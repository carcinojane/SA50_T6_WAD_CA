package SA50.T6.WadCA.LAPS.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.repo.LeaveRepository;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	LeaveRepository lrepo;

//	@Override
//	public ArrayList<LeaveRecord> findLeaveRecordByStaffId(int staffId) {
//		ArrayList<LeaveRecord> records = new ArrayList<LeaveRecord>();
//		List<LeaveRecord> leaverecord = lrepo.findAll();
//		for (Iterator<LeaveRecord> iterator = leaverecord.iterator(); iterator.hasNext();) {
//			LeaveRecord leaveRecord2 = (LeaveRecord) iterator.next();
//			records.add(leaveRecord2);
//			
//		}
//		return records; 
//	}

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

//	@Override
//	public List<LeaveRecord> findByFromAndToDate(LocalDate leaveStartDate, LocalDate leaveEndDate) {
//		List<LeaveRecord> leaveRecords = lrepo.findByFromAndToDate(leaveStartDate, leaveEndDate);
//		return leaveRecords;
//	}

	@Transactional
	public void deleteLeaveRecord(LeaveRecord leaveRecord) {
		leaveRecord.setLeaveStatus(LeaveStatus.CANCELLED);
		lrepo.save(leaveRecord);
		
	}

	@Transactional
	public float numOfLeaveApplied(LeaveRecord leave) {
		LocalDate from = leave.getLeaveStartDate();
		LocalDate to = leave.getLeaveEndDate();
		float numOfDay = 0;
		LocalDate curr = from;
		
		do {
			if(curr.compareTo(from)==0 && leave.getLeaveStartTime() == 'P')
				numOfDay += 0.5;
			else if(curr.compareTo(to)==0 && leave.getLeaveEndTime() == 'A')
				numOfDay += 0.5;
			else if(curr.getDayOfWeek() != DayOfWeek.SATURDAY && curr.getDayOfWeek() != DayOfWeek.SUNDAY)
				numOfDay ++;
			
			curr = curr.plusDays(1);
		}while(curr.compareTo(to)!=0);
		
		
		return numOfDay;
	}


	@Transactional
	public List<LeaveRecord> findByIdAndLeaveStatus(Integer id, LeaveStatus leaveStatus) {
		
		// TODO Auto-generated method stub
		return lrepo.findByIdAndLeaveStatus(id, leaveStatus);
	}

	@Transactional
	public List<LeaveRecord> findByIdAndLeaveType(Integer id, LeaveType leaveType) {
		// TODO Auto-generated method stub
		return lrepo.findByIdAndLeaveType(id, leaveType);
	}

	@Transactional
	public List<LeaveRecord> findByIdAndStatusAndType(Integer id, LeaveStatus leaveStatus, LeaveType leaveType) {
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
				leaveRecord.getLeaveStatus()==LeaveStatus.UPDATED||
				leaveRecord.getLeaveStatus()==LeaveStatus.CANCELLED) {
			return true;
		}
		return false;
	}

}
