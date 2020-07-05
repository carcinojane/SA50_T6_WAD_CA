package SA50.T6.WadCA.LAPS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import SA50.T6.WadCA.LAPS.model.Admin;


public interface AdminRepository extends JpaRepository<Admin, Integer> {

	Admin findByUsername(String name);
}
