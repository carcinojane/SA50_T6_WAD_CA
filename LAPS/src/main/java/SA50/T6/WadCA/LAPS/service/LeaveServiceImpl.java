package SA50.T6.WadCA.LAPS.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.repo.LeaveRepository;
import SA50.T6.WadCA.LAPS.repo.StaffRepository;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	LeaveRepository lrepo;

	@Autowired
	StaffRepository srepo;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public ArrayList<LeaveRecord> findAll() {
		return (ArrayList<LeaveRecord>) lrepo.findAll();
	}

	@Transactional
	public LeaveRecord findById(int id) {
		return lrepo.findById(id).get();

	}

	@Transactional
	public boolean saveLeaveRecord(LeaveRecord leaveRecord) {
		if(lrepo.save(leaveRecord)!= null) return true; else return false;
	}

	@Transactional
	public List<LeaveStatus> findAllLeaveStatus() {
		List<LeaveStatus>leaveStatus = Arrays.asList(LeaveStatus.values());
		return leaveStatus;
	}

	@Transactional
	public void deleteLeaveRecord(LeaveRecord leaveRecord) {

		lrepo.delete(leaveRecord);
	}

	@Transactional
	public float numOfLeaveApplied(LeaveRecord leave) {
		LocalDate from = leave.getLeaveStartDate();
		LocalDate to = leave.getLeaveEndDate();
		float numOfDay = 0;
		LocalDate curr = from;

		do {
			if(curr.compareTo(from)==0 && leave.getLeaveStartTime() == "PM")
				numOfDay += 0.5;
			else if(curr.compareTo(to)==0 && leave.getLeaveEndTime() == "AM")
				numOfDay += 0.5;
			else if(curr.getDayOfWeek() != DayOfWeek.SATURDAY && curr.getDayOfWeek() != DayOfWeek.SUNDAY)
				numOfDay ++;
			curr = curr.plusDays(1);
		}while(curr.isBefore(to));


		return numOfDay;
	}


	@Transactional
	public List<LeaveRecord> findByIdAndLeaveStatus(Integer id, LeaveStatus leaveStatus) {

		// TODO Auto-generated method stub
		return lrepo.findByIdAndLeaveStatus(id, leaveStatus);
	}

	@Transactional
	public List<LeaveRecord> findByIdAndLeaveType(Integer id, LType leaveType) {
		// TODO Auto-generated method stub
		return lrepo.findByIdAndLeaveType(id, leaveType);
	}

	@Transactional
	public List<LeaveRecord> findByIdAndStatusAndType(Integer id, LeaveStatus leaveStatus, LType leaveType) {
		// TODO Auto-generated method stub
		return lrepo.findByIdAndStatusAndType(id, leaveType, leaveStatus);
	}

	@Transactional
	public ArrayList<LeaveRecord> findLeaveRecordByManagerId(Integer managerId) {
		ArrayList<LeaveRecord> lrecords = new ArrayList<LeaveRecord>();
		lrecords = lrepo.findLeaveRecordByManagerId(managerId);
		return lrecords; 
	}

	@Transactional
	public ArrayList<LeaveRecord> findLeaveRecordByLeaveStatus(String leaveStatus) {
		ArrayList<LeaveRecord> lrecords = new ArrayList<LeaveRecord>();
		lrecords = lrepo.findLeaveRecordByLeaveStatus(leaveStatus);
		return lrecords; 
	}
	@Transactional
	public ArrayList<LeaveRecord> findPendingLeaveRecordByManagerId(Integer managerId) {
		ArrayList<LeaveRecord> lrecords = new ArrayList<LeaveRecord>();
		lrecords = lrepo.findPendingLeaveRecordByManagerId(managerId);
		return lrecords; 
	}

	@Transactional
	public ArrayList<LeaveRecord> findLeaveRecordByStaffId(Integer staffId) {
		ArrayList<LeaveRecord> lrecords = new ArrayList<LeaveRecord>();
		lrecords = lrepo.findLeaveRecordByStaffId(staffId);
		return lrecords; 
	}

	@Transactional
	public LeaveRecord findLeaveRecordById(Integer leaveId) {
		LeaveRecord lrecord = new LeaveRecord();
		lrecord = lrepo.findById(leaveId).get();
		return lrecord;
	}

	@Transactional
	public Boolean checkStatus(LeaveRecord leaveRecord) {
		if(leaveRecord.getLeaveStatus()==LeaveStatus.APPLIED||
				leaveRecord.getLeaveStatus()==LeaveStatus.UPDATED) {
			return true;
		}
		return false;
	}

	@Transactional
	public boolean approveLeave(Integer id) {
		boolean status = false;
		LeaveRecord leaveRecord = lrepo.findById(id).get();
		if(checkStatus(leaveRecord)) {
			Staff staff = leaveRecord.getStaff();
			float days = numOfLeaveApplied(leaveRecord);
			LType leaveType = leaveRecord.getLeaveType();

			float totalAnnualLeave = staff.getTotalAnnualLeave();
			float totalMedicalLeave = staff.getTotalMedicalLeave();
			float totalCompensationLeave = staff.getTotalCompensationLeave();

			if(leaveType==LType.AnnualLeave) {
				if(totalAnnualLeave>= days) {
					leaveRecord.setLeaveStatus(LeaveStatus.APPROVED);
					float balance = totalAnnualLeave-days;
					staff.setTotalAnnualLeave(balance);
					srepo.save(staff);
					status = true;
				}

			}

			else if(leaveType==LType.MedicalLeave) {
				if(totalMedicalLeave>= days) {
					leaveRecord.setLeaveStatus(LeaveStatus.APPROVED);
					float balance = totalMedicalLeave-days;
					staff.setTotalMedicalLeave(balance);
					srepo.save(staff);
					status = true;
				}

			}

			else if(leaveType==LType.Compensation) {
				if(totalCompensationLeave>= days) {
					leaveRecord.setLeaveStatus(LeaveStatus.APPROVED);
					float balance = totalCompensationLeave-days;
					staff.setTotalCompensationLeave(balance);
					srepo.save(staff);
					status = true;
				}
			}
		
		}
		if(status) {
			leaveRecord.setReasonForRejection("N.A.");
			lrepo.save(leaveRecord);
		}
		return status;
	}

	@Transactional
	public void rejectLeave(Integer id) {
		LeaveRecord record = lrepo.findById(id).get();
		if(checkStatus(record)) {
		//if(checkStatus(record)&&record.getReason()!=null) {
			record.setLeaveStatus(LeaveStatus.REJECTED);
		}
		lrepo.save(record);

	}
	
	@Transactional
    public void writeToCSV(List<LeaveRecord> records)
    {
    	final String CSV_SEPARATOR = ",";
    	String home = System.getProperty("user.home");
    	File file = new File(home+"/Downloads/" + "LeaveReport.csv"); 
        try
        {
        	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "UTF-8"));
            //BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("LeaveReport.csv"), "UTF-8"));
        	bw.write("LeaveId,StaffId,Name,Category,Start_date,End_date,Status");
        	bw.newLine();
        	for (LeaveRecord record : records)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(record.getLeaveId());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(record.getStaffId());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(record.getStaff().getUsername());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(record.getLeaveType().getDisplayValue());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(record.getLeaveStartDate());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(record.getLeaveEndDate());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(record.getLeaveStatus().getDisplayValue());
                oneLine.append(CSV_SEPARATOR);
                
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }

	@Override
	public List<LeaveRecord> findByMangerId(Integer id) {
		return lrepo.findLeaveRecordByManagerId(id);
	}

	@Transactional
	public List<LeaveRecord> findByMonth(List<LeaveRecord> records, Integer month) {
		List<LeaveRecord> leaveRecords= new ArrayList<>();
		for (Iterator<LeaveRecord> iterator=records.iterator();
				iterator.hasNext();) {
			LeaveRecord record = (LeaveRecord)iterator.next();
			int startMonth = record.getLeaveStartDate().getMonth().getValue();
			int endMonth = record.getLeaveEndDate().getMonth().getValue();

			if(startMonth==endMonth
					&& startMonth==month) {
				leaveRecords.add(record);
			}
			
			if(startMonth<month && endMonth==month) {
				leaveRecords.add(record);
			}
			if(endMonth>month && 
					startMonth==month ||startMonth<month) {
				leaveRecords.add(record);
			}
		}
		
				return leaveRecords;
	}
	
	@Transactional
	public List<Month> LeaveMonths(List<LeaveRecord> records) {
		List<Month> months= new ArrayList<>();
		for (Iterator<LeaveRecord> iterator=records.iterator();
				iterator.hasNext();) {
			LeaveRecord record = (LeaveRecord)iterator.next();
			Month startMonth = record.getLeaveStartDate().getMonth();
			Month endMonth= record.getLeaveEndDate().getMonth();
			
			if(startMonth.equals(endMonth)) {
				if(!months.contains(startMonth)) {
					months.add(startMonth);
				}
			}
			else {
				Month month = startMonth;
				while(!month.equals(endMonth)) {
					month.plus(1);
					if(!months.contains(month)) {
						months.add(month);
					}
				}
				
			}
			
		}
		return months;
	}

	@Transactional
	public List<LeaveRecord> findLeaveRecordByStaffIdPage(int staffId,int status,int start,int size) {
		String sqlStr="";
		if (status==-1) {
			 sqlStr = "select * from leave_record t where t.staff_id = " + staffId+" limit "+start+","+size;
		}else {
			 sqlStr = "select * from leave_record t where t.staff_id = " + staffId+" and t.leave_status="+status+" limit "+start+","+size;
		}
		return this.jdbcTemplate.query(sqlStr, new BeanPropertyRowMapper<LeaveRecord>(LeaveRecord.class));
	}

	@Transactional
	public List<LeaveRecord> countSize(int staffId) {
		String sqlStr="select * from leave_record t where t.staff_id = " + staffId;
		return this.jdbcTemplate.query(sqlStr, new BeanPropertyRowMapper<LeaveRecord>(LeaveRecord.class));
	}

	@Override
	public List<LeaveRecord> findByStaffId(List<LeaveRecord> records, Integer staffId) {
		ArrayList<LeaveRecord> leaveRecords= new ArrayList<>();
		for (Iterator<LeaveRecord> iterator=records.iterator();
				iterator.hasNext();) {
			LeaveRecord record = (LeaveRecord)iterator.next();
			if(record.getStaffId()== staffId) {
				leaveRecords.add(record);
				}
			}
		return leaveRecords;
	}

	@Override
	public List<LeaveRecord> findByLtype(List<LeaveRecord> records, LType leaveType) {
		ArrayList<LeaveRecord> leaveRecords= new ArrayList<>();
		for (Iterator<LeaveRecord> iterator=records.iterator();
				iterator.hasNext();) {
			LeaveRecord record = (LeaveRecord)iterator.next();
			if(record.getLeaveType()== leaveType) {
				leaveRecords.add(record);
				}
			}
		return leaveRecords;
	}

	@Override
	public ArrayList<LeaveRecord> findByStatus(List<LeaveRecord> records, LeaveStatus leaveStatus) {
		ArrayList<LeaveRecord> leaveRecords= new ArrayList<>();
		for (Iterator<LeaveRecord> iterator=records.iterator();
				iterator.hasNext();) {
			LeaveRecord record = (LeaveRecord)iterator.next();
			if(record.getLeaveStatus()== leaveStatus) {
				leaveRecords.add(record);
				}
			}
		return leaveRecords;
	}

	@Override
	public List<LeaveRecord> findByMonthLtypeLstatus(List<LeaveRecord> records, Integer month, LType leaveType,
			LeaveStatus leaveStatus) {
		List<LeaveRecord> fliterMonth= findByMonth(records,month);
		List<LeaveRecord> filterLType= findByLtype(fliterMonth,leaveType);
		List<LeaveRecord> filterLStatus= findByStatus(filterLType,leaveStatus);
		
		return filterLStatus;
	}





}

