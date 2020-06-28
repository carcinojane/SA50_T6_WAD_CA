package SA50.T6.WadCA.LAPS.service;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;

public interface LeaveService {
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.staffId = :staffId")
	ArrayList<LeaveRecord> findLeaveRecordByStaffId(int staffId);
	
	public ArrayList<LeaveRecord> findAll();
	public LeaveRecord findById(int id);
	public boolean saveLeaveRecord(LeaveRecord leaveRecord);
}
