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
			Staff manager2 = new Staff("manager2","password2","manager2@gmail.com",Status.inactive,2,60,18);
			manager2.setDesignation(Staff.Designation.manager);
			staffRepo.save(manager2);
			Staff head = new Staff("Head Manager","password3","head@gmail.com",Status.active,2,60,18);
			head.setDesignation(Staff.Designation.manager);
			staffRepo.save(head);
			Staff manager3 = new Staff("manager3","password3","manager3@gmail.com",Status.active,2,60,18);
			manager3.setDesignation(Staff.Designation.manager);
			staffRepo.save(manager3);
			
			//create staff
			Staff staff1 = new Staff("staff1","password","staff1@gmail.com",Status.active,2,60,18);
			Staff staff2 = new Staff("staff2","password","staff2@gmail.com",Status.active,0,60,18);
			Staff staff3 = new Staff("staff3","password3","staff3@gmail.com",Status.active,4,60,18);
			Staff staff4 = new Staff("staff4","password4","staff4@gmail.com",Status.active,2,60,18);
			Staff staff5 = new Staff("staff5","password5","staff5@gmail.com",Status.active,2,60,18);
			staff1.setManager(manager1);
			staff1.setDesignation(Staff.Designation.employee);
			staff2.setManager(manager1);
			staff2.setDesignation(Staff.Designation.employee);
			staff3.setManager(manager3);
			staff3.setDesignation(Staff.Designation.employee);
			staff4.setManager(manager3);
			staff4.setDesignation(Staff.Designation.employee);
			staff5.setManager(manager3);
			staff5.setDesignation(Staff.Designation.employee);
			staffRepo.save(staff1);
			staffRepo.save(staff2);
			staffRepo.save(staff3);
			staffRepo.save(staff4);
			staffRepo.save(staff5);
			
			//create admin
			Admin admin1=new Admin("May Thu Kyaw","admin1", "password");
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
			LeaveRecord l1 = new LeaveRecord(1,LeaveStatus.APPLIED,"Attend National Day parade",
					LocalDate.of(2020, 8, 9),'N',LocalDate.of(2020, 8, 9),'N',5,"",97856210);
			l1.setLeaveType(LType.AnnualLeave);
			lRepo.save(l1);
			LeaveRecord l2 = new LeaveRecord(1,LeaveStatus.CANCELLED,"Family matters",
					LocalDate.of(2020, 10, 9),'A',LocalDate.of(2020, 10, 11),'A',5,"",8662395);
			l2.setLeaveType(LType.MedicalLeave);
			lRepo.save(l2);
			LeaveRecord l3 = new LeaveRecord(1,LeaveStatus.APPROVED,"It's my birthday!",
					LocalDate.of(2020, 11, 9),'A',LocalDate.of(2020, 11, 9),'A',5,"",8662395);
			l3.setLeaveType(LType.AnnualLeave);
			lRepo.save(l3);
			LeaveRecord l4 = new LeaveRecord(
					LeaveStatus.APPROVED,
					"There's a post-covid promotion that I cannot miss",
					LocalDate.of(2020, 8, 9),'A',
					LocalDate.of(2020, 8, 9),'A',
					6,
					LType.AnnualLeave,
					"N.A.",
					8662395);
			lRepo.save(l4);
			
			System.out.println(l1.getLeaveType().getDisplayValue());
			System.out.println(l4.getWorkDissemination());
			System.out.println(annual_emp.getLeaveType().getDisplayValue());

			



	};

}
}
