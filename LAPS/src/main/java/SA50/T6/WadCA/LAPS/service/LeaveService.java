package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;

public interface LeaveService {
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.staffId = :staffId")
	ArrayList<LeaveRecord> findLeaveRecordByStaffId(@Param("staffId")int staffId);
	
	@Query(
		value = "SELECT * FROM LeaveRecord lr WHERE lr.managerId = :managerId AND lr.leaveStatus = APPLIED", 
		nativeQuery = true)
	ArrayList<LeaveRecord> findPendingLeaveRecordByManagerId(@Param("managerId")int managerId);
	
	public ArrayList<LeaveRecord> findAll();
	public LeaveRecord findById(int id);
	public boolean saveLeaveRecord(LeaveRecord leaveRecord);
	
}
