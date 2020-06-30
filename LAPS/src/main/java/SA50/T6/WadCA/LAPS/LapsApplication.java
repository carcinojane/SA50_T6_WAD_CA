package SA50.T6.WadCA.LAPS;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import SA50.T6.WadCA.LAPS.model.*;
import SA50.T6.WadCA.LAPS.model.LeaveRecord.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
import SA50.T6.WadCA.LAPS.repo.*;
import SA50.T6.WadCA.LAPS.service.*;


@SpringBootApplication
public class LapsApplication {

	@Autowired
	StaffRepository staffRepo;
	@Autowired
	AdminRepository adminRepo;
	@Autowired
	LeaveRepository lRepo;
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


	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			//create staff

			Staff manager1 = new Staff("manager1","password");
			manager1.setDesignation(Staff.Designation.manager);
			staffRepo.save(manager1);

			Staff staff1 = new Staff("staff1","password");
			Staff staff2 = new Staff("staff2","password");
			
			

			staff1.setManager(manager1);
			staff2.setManager(manager1);

			staffRepo.save(staff1);
			staffRepo.save(staff2);

			Admin admin1=new Admin("admin1", "password");
			adminRepo.save(admin1);

			//create Leave Records
			LeaveRecord l1 = new LeaveRecord(1,LeaveStatus.APPLIED,"Attend National Day parade",
					LocalDate.of(2020, 8, 9),'N',LocalDate.of(2020, 8, 9),'N',2,"",97856210);
			lRepo.save(l1);
			LeaveRecord l2 = new LeaveRecord(1,LeaveStatus.CANCELLED,"Family matters",
					LocalDate.of(2020, 10, 9),'A',LocalDate.of(2020, 10, 11),'A',2,"",8662395);
			lRepo.save(l2);
			LeaveRecord l3 = new LeaveRecord(1,LeaveStatus.APPROVED,"It's my birthday!",
					LocalDate.of(2020, 11, 9),'A',LocalDate.of(2020, 11, 9),'A',2,"",8662395);
			lRepo.save(l3);


		//};			
					LeaveType annual_emp = new LeaveType(Designation.employee, "Annual Leave", 14f, 1f);
					LeaveType medical_emp = new LeaveType(Designation.employee, "Medical Leave", 60f,1f);
					LeaveType compensation_emp = new LeaveType(Designation.employee, "Compensation Leave", 2f, 0.5f);
					LeaveType annual_mng = new LeaveType(Designation.manager, "Annual Leave", 18f, 1f);
					LeaveType medical_mng = new LeaveType(Designation.manager, "Medical Leave", 60f, 1f);
					LeaveType compensation_mng = new LeaveType(Designation.manager, "Compensation Leave", 2f, 0.5f);
					
					ltservice.save(annual_emp);
					ltservice.save(medical_emp);
					ltservice.save(compensation_emp);
					ltservice.save(annual_mng);
					ltservice.save(medical_mng);
					ltservice.save(compensation_mng);
					
		
				};



	}
}
