package SA50.T6.WadCA.LAPS.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
	@Query("select lt from LeaveType lt WHERE lt.leaveType=:leaveType AND lt.designation=:designation")
	LeaveType findLeaveTypeToEdit(@Param("leaveType")LType leaveType, @Param("designation")Designation designation);
	
	@Query("select lt.leaveType from LeaveType lt where lt.designation= :designation")
	Collection<LType> findByDesignation(@Param("designation")Designation designation);
	
	@Query("select lt from LeaveType lt where lt.designation= :designation")
	List<LeaveType> findLeaveTypeByDesignation(@Param("designation")Designation designation);
	
	@Query("Select lt from LeaveType lt where lt.id = :id")
	LeaveType findLeaveTypeById(@Param("id")Integer id);
}
