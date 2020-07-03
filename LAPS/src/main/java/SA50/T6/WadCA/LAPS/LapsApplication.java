package SA50.T6.WadCA.LAPS;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import SA50.T6.WadCA.LAPS.model.Admin;
import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
import SA50.T6.WadCA.LAPS.model.Staff.Status;
import SA50.T6.WadCA.LAPS.repo.AdminRepository;
import SA50.T6.WadCA.LAPS.repo.LeaveRepository;
import SA50.T6.WadCA.LAPS.repo.StaffRepository;
import SA50.T6.WadCA.LAPS.service.LeaveTypeImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeService;



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
			//create manager

			Staff manager1 = new Staff("manager1","password","manager1@gmail.com",Status.active,2,60,18);
			manager1.setDesignation(Staff.Designation.manager);
			staffRepo.save(manager1);
			Staff manager2 = new Staff("manager2","password","manager2@gmail.com",Status.active, 2,60,18);
			manager2.setDesignation(Staff.Designation.manager);
			staffRepo.save(manager2);
			Staff head = new Staff("Head","password","head@gmail.com", Status.active, 2,60,18);
			head.setDesignation(Staff.Designation.admin);
			staffRepo.save(head);
			
			Staff manager3 = new Staff("manager3","password","manager3@gmail.com",Status.active,2,60,18);
			manager3.setDesignation(Staff.Designation.manager);
			staffRepo.save(manager3);
			
			//create staff
			Staff staff1 = new Staff("staff1","password","staff1@gmail.com",Status.active, 0f,60f,14f);
			Staff staff2 = new Staff("staff2","password","staff2@gmail.com", Status.active, 1f,60f,14f);
			Staff staff3 = new Staff("staff3","password","staff3@gmail.com", Status.active, 1.5f,60f,14f);
				//staff with 0 Annual leave	
				Staff staff4 = new Staff("staff4","password","staff4@gmail.com",Status.active, 2f,60f,0f);
				//staff with 0 Compensation leave	
				Staff staff5 = new Staff("staff5","password","staff5@gmail.com",Status.active, 0f,60f,14f);
				//staff with 0 Medical leave	
				Staff staff6 = new Staff("staff6","password","staff6@gmail.com",Status.active, 0f,0f,14f);

			staff1.setManager(manager1);
			staff1.setDesignation(Staff.Designation.employee);
			staff2.setManager(manager1);
			staff2.setDesignation(Staff.Designation.employee);
			staff3.setManager(manager3);
			staff3.setDesignation(Staff.Designation.employee);

			staff4.setManager(manager2);
			staff4.setDesignation(Staff.Designation.employee);
			staff5.setManager(manager2);
			staff5.setDesignation(Staff.Designation.employee);
			staff6.setManager(manager2);
			staff6.setDesignation(Staff.Designation.employee);

			staff4.setManager(manager3);
			staff4.setDesignation(Staff.Designation.employee);
			staff5.setManager(manager3);
			staff5.setDesignation(Staff.Designation.employee);

			staffRepo.save(staff1);
			staffRepo.save(staff2);
			staffRepo.save(staff3);
			staffRepo.save(staff4);
			staffRepo.save(staff5);
			staffRepo.save(staff6);
			
			//create admin
			Admin admin1=new Admin("Human Resources","admin1", "password");
			adminRepo.save(admin1);
			
			LeaveType annual_emp = new LeaveType(Designation.employee, LType.AnnualLeave, 14f, 1f);
			LeaveType medical_emp = new LeaveType(Designation.employee, LType.MedicalLeave, 60f,1f);
			LeaveType compensation_emp = new LeaveType(Designation.employee, LType.Compensation, 2f, 0.5f);
			LeaveType annual_mng = new LeaveType(Designation.manager, LType.AnnualLeave, 18f, 1f);
			LeaveType medical_mng = new LeaveType(Designation.manager, LType.MedicalLeave, 60f, 1f);
			LeaveType compensation_mng = new LeaveType(Designation.manager, LType.Compensation, 2f, 0.5f);

			ltservice.save(annual_emp);
			ltservice.save(medical_emp);
			ltservice.save(compensation_emp);
			ltservice.save(annual_mng);
			ltservice.save(medical_mng);
			ltservice.save(compensation_mng);
			
			//create Leave Records
			//Applied Annual leave & on working day
			LeaveRecord lr1 = new LeaveRecord(1, LeaveStatus.APPLIED,"Japan holiday", 
					LocalDate.of(2020,7,13),'A', LocalDate.of(2020,7,17),'N',5,"staff2 will take over",97856210);
			lr1.setLeaveType(LType.AnnualLeave);
			//Applied Compensation leave
			LeaveRecord lr2 = new LeaveRecord(1, LeaveStatus.APPLIED,"Off-in-lieu", 
					LocalDate.of(2020,8,3),'A', LocalDate.of(2020,8,3),'N',5,"staff2 will take over",97856210);
			lr2.setLeaveType(LType.Compensation);
			//Applied Medical leave
			LeaveRecord lr3 = new LeaveRecord(1, LeaveStatus.APPLIED,"Broke arm on holiday", 
					LocalDate.of(2020,7,20),'A', LocalDate.of(2020,7,31),'N',5,"staff2 will take over",97856210);
			lr3.setLeaveType(LType.MedicalLeave);
			//Approved Annual leave
			LeaveRecord lr4 = new LeaveRecord(1, LeaveStatus.APPROVED,"Clearing leave", 
					LocalDate.of(2020,7,13),'A', LocalDate.of(2020,7,17),'N',6,"Manager 1 in charge",96223444);
			lr4.setLeaveType(LType.AnnualLeave);
			LeaveRecord lr5 = new LeaveRecord(1, LeaveStatus.APPROVED,"It's my birthday!",
					LocalDate.of(2020, 11, 9),'A',LocalDate.of(2020, 11, 9),'N',5,"Employee Perks",97856210);
			lr5.setLeaveType(LType.AnnualLeave);
			
			lRepo.save(lr1);lRepo.save(lr2);lRepo.save(lr3);lRepo.save(lr4);lRepo.save(lr5);
			
			System.out.println(lr1.getLeaveType().getDisplayValue());
			System.out.println(lr4.getWorkDissemination());
			System.out.println(annual_emp.getLeaveType().getDisplayValue());		

	};

}
}
