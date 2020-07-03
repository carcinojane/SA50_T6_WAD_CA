
package SA50.T6.WadCA.LAPS.service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveStatus;

public interface LeaveService {
	public ArrayList<LeaveRecord> findLeaveRecordByManagerId(Integer managerId);
	
	public ArrayList<LeaveRecord> findLeaveRecordByLeaveStatus(String leaveStatus);
	
	public ArrayList<LeaveRecord> findPendingLeaveRecordByManagerId (Integer managerId);
	
	public LeaveRecord findLeaveRecordById(Integer leaveId);
		
	public ArrayList<LeaveRecord> findAll();
	public LeaveRecord findById(int id);
	public boolean saveLeaveRecord(LeaveRecord leaveRecord);
	public List<LeaveStatus> findAllLeaveStatus();
//	public List<LeaveRecord> findByFromAndToDate(LocalDate leaveStartDate, LocalDate leaveEndDate);
	public void deleteLeaveRecord(LeaveRecord leaveRecord);
	public float numOfLeaveApplied(LeaveRecord leaveRecord);
	public List<LeaveRecord> findByIdAndLeaveStatus(Integer id, LeaveStatus leaveStatus);
	public List<LeaveRecord> findByIdAndLeaveType(Integer id, LType leaveType);
	public List<LeaveRecord> findByIdAndStatusAndType(Integer id,LeaveStatus leaveStatus, LType leaveType);
	public ArrayList<LeaveRecord> findLeaveRecordByStaffId(Integer staffId);
	public Boolean checkStatus(LeaveRecord leaveRecord);
	public boolean approveLeave(Integer id);
	public void rejectLeave(Integer id);
	public void writeToCSV(ArrayList<LeaveRecord> records);
	public List<LeaveRecord> findByMangerId(Integer id);
	public List<LeaveRecord>findByMonth(ArrayList<LeaveRecord> records,Month month);
	public List<Month>LeaveMonths(ArrayList<LeaveRecord> records);

	
	List<LeaveRecord> countSize(@Param("staffId")int staffId);
	List<LeaveRecord> findLeaveRecordByStaffId(@Param("staffId")int staffId,@Param("status")int status,int start,int size);
}
