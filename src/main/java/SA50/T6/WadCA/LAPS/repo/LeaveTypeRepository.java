package SA50.T6.WadCA.LAPS.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.LeaveTypeId;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, LeaveTypeId> {

	ArrayList<LeaveRecord> findAll(LeaveRecord lRecord);

}
