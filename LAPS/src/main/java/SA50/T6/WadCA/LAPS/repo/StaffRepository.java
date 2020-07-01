package SA50.T6.WadCA.LAPS.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SA50.T6.WadCA.LAPS.model.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
	
	Staff findByUsername(String name);
	
	@Query("Select s.username from Staff s")
	ArrayList<String> findAllNames();
	
	
}
