package SA50.T6.WadCA.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;

public interface LeaveRepository extends JpaRepository<LeaveRecord, Integer> {

}
