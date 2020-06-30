package SA50.T6.WadCA.LAPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import SA50.T6.WadCA.LAPS.model.Admin;
import SA50.T6.WadCA.LAPS.repo.StaffRepository;
import SA50.T6.WadCA.LAPS.service.AdminService;


@SpringBootApplication
public class LapsApplication {
	
	@Autowired
	StaffRepository staffRepo;

	public static void main(String[] args) {
		SpringApplication.run(LapsApplication.class, args);
	}

	//test mapping
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			
			//test 1 with username and password
//			Staff manager1 = new Staff("manager1","password");
//			staffRepo.save(manager1);
//			
//			Staff staff1 = new Staff("staff1","password");
//			Staff staff2 = new Staff("staff2","password");
//			
//			staff1.setManager(manager1);
//			staff2.setManager(manager1);
//			
//			staffRepo.save(staff1);
//			staffRepo.save(staff2);


		};
	}
}
