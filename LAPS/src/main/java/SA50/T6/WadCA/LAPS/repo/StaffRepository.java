package SA50.T6.WadCA.LAPS.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
	
	Staff findByUsername(String name);
	
	@Query("Select s.username from Staff s")
	ArrayList<String> findAllNames();
	
	@Query("Select s from Staff s Where s.designation=2")
	ArrayList<Staff> findAllManagers();
	
	@Query("Select s from Staff s Where s.username Like CONCAT('%',:username,'%')")
	ArrayList<Staff> findStaffLikeSearchInput(@Param("username")String username);
}
