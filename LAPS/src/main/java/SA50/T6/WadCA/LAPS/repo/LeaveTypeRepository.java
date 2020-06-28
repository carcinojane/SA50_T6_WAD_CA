package SA50.T6.WadCA.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.LeaveTypeId;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, LeaveTypeId> {

}
