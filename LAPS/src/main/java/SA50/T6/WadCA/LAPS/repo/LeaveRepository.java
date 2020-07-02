package SA50.T6.WadCA.LAPS.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.LType;

public interface LeaveRepository extends JpaRepository<LeaveRecord, Integer> {

	@Query("Select r from LeaveRecord r where r.staffId = :staffId AND r.leaveStatus = :leaveStatus")
	public List<LeaveRecord> findByIdAndLeaveStatus(@Param("staffId")Integer id, @Param("leaveStatus")LeaveStatus leaveStatus);
	
	@Query("Select r from LeaveRecord r where r.staffId = :staffId AND r.leaveType = :leaveType")
	public List<LeaveRecord> findByIdAndLeaveType(@Param("staffId")Integer id, @Param("leaveType")LType leaveType);
	
	@Query("Select r from LeaveRecord r where r.staffId = :staffId AND r.leaveType = :leaveType"
			+ " AND r.leaveStatus = :leaveStatus")
	public List<LeaveRecord> findByIdAndStatusAndType(@Param("staffId")Integer id,@Param("leaveType")LType leaveType,@Param("leaveStatus")LeaveStatus leaveStatus);

//	@Query("SELECT * FROM LeaveRecord lr WHERE lr.leaveStartDate <= :leaveStartDate AND lr.leaveEndDate >= :leaveEndDate")
//	List<LeaveRecord> findByFromAndToDate(@Param("leaveStartDate") LocalDate leaveStartDate, @Param("leaveEndDate") LocalDate leaveEndDate);
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.managerId = :managerId")
	ArrayList<LeaveRecord> findLeaveRecordByManagerId(@Param("managerId")Integer managerId);
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.leaveStatus = :leaveStatus")
	ArrayList<LeaveRecord> findLeaveRecordByLeaveStatus(@Param("leaveStatus")String leaveStatus);
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.managerId = :managerId AND (lr.leaveStatus = 0 OR lr.leaveStatus = 4)")
	ArrayList<LeaveRecord> findPendingLeaveRecordByManagerId(@Param("managerId")Integer managerId);
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.staffId = :staffId")
	ArrayList<LeaveRecord> findLeaveRecordByStaffId(@Param("staffId")Integer staffId);

	

}
