package SA50.T6.WadCA.LAPS.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.LeaveRecord.LeaveStatus;

public interface LeaveRepository extends JpaRepository<LeaveRecord, Integer> {
	@Query("Select r from LeaveRecord r where r.staffId = :staffId AND r.leaveStatus = :leaveStatus")
	public List<LeaveRecord> findByIdAndLeaveStatus(@Param("staffId")Integer id, @Param("leaveStatus")LeaveStatus leaveStatus);
	
	@Query("Select r from LeaveRecord r where r.staffId = :staffId AND r.leaveType = :leaveType")
	public List<LeaveRecord> findByIdAndLeaveType(@Param("staffId")Integer id, @Param("leaveType")LeaveType leaveType);
	
	@Query("Select r from LeaveRecord r where r.staffId = :staffId AND r.leaveType = :leaveType"
			+ " AND r.leaveStatus = :leaveStatus")
	public List<LeaveRecord> findByIdAndStatusAndType(@Param("staffId")Integer id,@Param("leaveType")LeaveType leaveType,@Param("leaveStatus")LeaveStatus leaveStatus);
}
