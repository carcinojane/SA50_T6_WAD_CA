package SA50.T6.WadCA.LAPS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
import SA50.T6.WadCA.LAPS.repo.LeaveTypeRepository;
import SA50.T6.WadCA.LAPS.repo.StaffRepository;
import SA50.T6.WadCA.LAPS.service.LeaveTypeImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeService;
import SA50.T6.WadCA.LAPS.model.Admin;
import SA50.T6.WadCA.LAPS.service.AdminService;


@SpringBootApplication
public class LapsApplication {
	
	@Autowired
	StaffRepository staffRepo;
	
	@Autowired
	protected LeaveTypeService ltservice;
	
	@Autowired
	public void setLeaveTypeService(LeaveTypeImpl ltserviceImpl) {
		this.ltservice = ltserviceImpl;
	}

	public static void main(String[] args) {
		SpringApplication.run(LapsApplication.class, args);
	}

	//test mapping


//	@Bean
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//			//test 1 with username and password

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
		
			
//			LeaveType annual_emp = new LeaveType(Designation.employee, "Annual Leave", 14f, 1f);
//			LeaveType medical_emp = new LeaveType(Designation.employee, "Medical Leave", 60f,1f);
//			LeaveType compensation_emp = new LeaveType(Designation.employee, "Compensation Leave", 2f, 0.5f);
//			LeaveType annual_mng = new LeaveType(Designation.manager, "Annual Leave", 18f, 1f);
//			LeaveType medical_mng = new LeaveType(Designation.manager, "Medical Leave", 60f, 1f);
//			LeaveType compensation_mng = new LeaveType(Designation.manager, "Compensation Leave", 2f, 0.5f);
//			
//			ltservice.save(annual_emp);
//			ltservice.save(medical_emp);
//			ltservice.save(compensation_emp);
//			ltservice.save(annual_mng);
//			ltservice.save(medical_mng);
//			ltservice.save(compensation_mng);
			

		};
	}

}
