package SA50.T6.WadCA.LAPS.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;

public interface LeaveRepository extends JpaRepository<LeaveRecord, Integer> {
//	@Query("SELECT * FROM LeaveRecord lr WHERE lr.leaveStartDate <= :leaveStartDate AND lr.leaveEndDate >= :leaveEndDate")
//	List<LeaveRecord> findByFromAndToDate(@Param("leaveStartDate") LocalDate leaveStartDate, @Param("leaveEndDate") LocalDate leaveEndDate);
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.managerId = :managerId")
	ArrayList<LeaveRecord> findLeaveRecordByManagerId(@Param("managerId")Integer managerId);
	
	@Query("SELECT lr FROM LeaveRecord lr WHERE lr.leaveStatus = :leaveStatus")
	ArrayList<LeaveRecord> findLeaveRecordByLeaveStatus(@Param("leaveStatus")String leaveStatus);
}
