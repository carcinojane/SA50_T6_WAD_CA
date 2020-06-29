package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;

public interface LeaveService {
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.staffId = :staffId")
	ArrayList<LeaveRecord> findLeaveRecordByStaffId(@Param("staffId")int staffId);
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.managerId = :managerId AND lr.leaveStatus = :APPLIED")
	ArrayList<LeaveRecord> findPendingLeaveRecordByManagerId(@Param("managerId")int managerId);
	
	public ArrayList<LeaveRecord> findAll();
	public LeaveRecord findById(int id);
	public boolean saveLeaveRecord(LeaveRecord leaveRecord);
	
}
