package SA50.T6.WadCA.LAPS.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SA50.T6.WadCA.LAPS.model.Staff;


public interface StaffRepository extends JpaRepository<Staff, Integer> {
	
	Staff findByUsername(String name);
	
	@Query("Select s.username from Staff s")
	ArrayList<String> findAllNames();
	
	@Query("Select s from Staff s Where s.designation=2 OR s.designation=0")
	ArrayList<Staff> findAllManagers();
	
	@Query("Select s from Staff s Where s.username Like CONCAT('%',:username,'%')")
	ArrayList<Staff> findStaffLikeSearchInput(@Param("username")String username);
	
	@Query("Select s.staffId from Staff s Where s.username = :username")
	Integer findStaffIdByUsername(@Param("username")String username);
	
	@Query("Select s from Staff s where s.username = :username")
	 Staff findManagerByUsername(@Param("username") String username);
}
