package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveRecord.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.LeaveType;

public interface LeaveService {
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.staffId = :staffId")
	ArrayList<LeaveRecord> findLeaveRecordByStaffId(@Param("staffId")int staffId);
	
	@Query(
		value = "SELECT lr FROM LeaveRecord lr WHERE lr.managerId = :managerId AND lr.leaveStatus = 'APPLIED'", 
		nativeQuery = true)
	public ArrayList<LeaveRecord> findPendingLeaveRecordByManagerId(@Param("managerId")int managerId);
	
	public ArrayList<LeaveRecord> findAll();
	public LeaveRecord findById(int id);
	public boolean saveLeaveRecord(LeaveRecord leaveRecord);
	public List<LeaveStatus> findAllLeaveStatus();
//	public List<LeaveRecord> findByFromAndToDate(LocalDate leaveStartDate, LocalDate leaveEndDate);
	public void deleteLeaveRecord(LeaveRecord leaveRecord);
	public float numOfLeaveApplied(LeaveRecord leaveRecord);
	public List<LeaveRecord> findByIdAndLeaveStatus(Integer id, LeaveStatus leaveStatus);
	public List<LeaveRecord> findByIdAndLeaveType(Integer id, LeaveType leaveType);
	public List<LeaveRecord> findByIdAndStatusAndType(Integer id,LeaveStatus leaveStatus, LeaveType leaveType);

	
}
