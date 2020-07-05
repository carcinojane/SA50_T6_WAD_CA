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

	// test mapping

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			// create manager
			//testdata

			Staff manager1 = new Staff("manager1", "password", "waiyankm93@gmail.com", Status.active, 2, 60, 18);
			manager1.setDesignation(Staff.Designation.manager);
			manager1.setStartDate(LocalDate.of(2010, 01, 01));

			Staff manager2 = new Staff("manager2", "password", "waiyankm93@gmail.com", Status.active, 2, 60, 18);
			manager2.setDesignation(Staff.Designation.manager);
			manager2.setStartDate(LocalDate.of(2018, 01, 01));

			Staff head = new Staff("Head", "password", "head@gmail.com", Status.active, 2, 60, 18);
			head.setDesignation(Staff.Designation.manager);
			head.setStartDate(LocalDate.of(2000, 01, 01));
			staffRepo.save(head);

			Staff manager3 = new Staff("manager3", "password", "waiyankm93@gmail.com", Status.active, 2, 60, 18);
			manager3.setDesignation(Staff.Designation.manager);
			manager3.setStartDate(LocalDate.of(2017, 01, 01));

			manager1.setManager(head);
			manager2.setManager(head);
			manager3.setManager(head);
			staffRepo.save(manager1);
			staffRepo.save(manager2);
			staffRepo.save(manager3);

			// create staff
			Staff staff1 = new Staff("staff1", "password", "staff1@gmail.com", Status.active, 0f, 60f, 14f);
			Staff staff2 = new Staff("staff2", "password", "staff2@gmail.com", Status.active, 1f, 60f, 14f);
			Staff staff3 = new Staff("staff3", "password", "staff3@gmail.com", Status.active, 1.5f, 60f, 14f);
			// staff with 0 Annual leave
			Staff staff4 = new Staff("staff4", "password", "staff4@gmail.com", Status.active, 2f, 60f, 0f);
			// staff with 0 Compensation leave
			Staff staff5 = new Staff("staff5", "password", "staff5@gmail.com", Status.active, 0f, 60f, 14f);
			// staff with 0 Medical leave
			Staff staff6 = new Staff("staff6", "password", "staff6@gmail.com", Status.active, 0f, 0f, 14f);
			// Inactive staff
			Staff staff7 = new Staff("staff7", "password", "staff7@gmail.com", Status.inactive, 0f, 60f, 14f);

			staff1.setManager(manager1);
			staff1.setDesignation(Staff.Designation.employee);
			staff1.setStartDate(LocalDate.of(2020, 01, 01));
			staff2.setManager(manager1);
			staff2.setDesignation(Staff.Designation.employee);
			staff2.setStartDate(LocalDate.of(2020, 01, 01));
			staff3.setManager(manager3);
			staff3.setDesignation(Staff.Designation.employee);
			staff3.setStartDate(LocalDate.of(2020, 01, 01));
			staff4.setManager(manager3);
			staff4.setDesignation(Staff.Designation.employee);
			staff4.setStartDate(LocalDate.of(2020, 01, 01));
			staff5.setManager(manager2);
			staff5.setDesignation(Staff.Designation.employee);
			staff5.setStartDate(LocalDate.of(2020, 01, 01));
			staff6.setManager(manager2);
			staff6.setDesignation(Staff.Designation.employee);
			staff6.setStartDate(LocalDate.of(2020, 01, 01));
			staff7.setManager(manager1);
			staff7.setDesignation(Staff.Designation.employee);
			staff7.setStartDate(LocalDate.of(2020, 01, 01));

			staffRepo.save(staff1);
			staffRepo.save(staff2);
			staffRepo.save(staff3);
			staffRepo.save(staff4);
			staffRepo.save(staff5);
			staffRepo.save(staff6);
			staffRepo.save(staff7);

			// create admin
			Admin admin1 = new Admin("Human Resources", "admin1", "password");
			adminRepo.save(admin1);

			LeaveType annual_emp = new LeaveType(Designation.employee, LType.AnnualLeave, 14f, 1f);
			LeaveType medical_emp = new LeaveType(Designation.employee, LType.MedicalLeave, 60f, 1f);
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
			//Staff 1
			LeaveRecord lr1 = new LeaveRecord(2, LeaveStatus.APPLIED,"Japan holiday", 
					LocalDate.of(2020,7,13),"NA", LocalDate.of(2020,7,17),"NA",5,"staff2 will take over",97856210);
			lr1.setLeaveType(LType.AnnualLeave);
			
			LeaveRecord lr2 = new LeaveRecord(2, LeaveStatus.APPLIED,"Apply for HDB", 
					LocalDate.of(2020,8,3),"NA", LocalDate.of(2020,8,3),"NA",5,"staff2 will take over",97856210);
			lr2.setLeaveType(LType.AnnualLeave);
			
			LeaveRecord lr3 = new LeaveRecord(2, LeaveStatus.APPLIED,"Broke arm on holiday", 
					LocalDate.of(2020,7,20),"NA", LocalDate.of(2020,7,31),"NA",5,"staff2 will take over",97856210);
			lr3.setLeaveType(LType.MedicalLeave);			

			LeaveRecord lr4 = new LeaveRecord(2, LeaveStatus.APPROVED,"It's my birthday!",
					LocalDate.of(2020, 11, 9),"NA",LocalDate.of(2020, 11, 9),"NA",5,"Employee Perks",97856210);
			lr4.setLeaveType(LType.AnnualLeave);
			
			LeaveRecord lr5 = new LeaveRecord(2, LeaveStatus.APPROVED,"December holiday", 
					LocalDate.of(2020,12,21),"NA", LocalDate.of(2020,12,24),"NA",5,"staff2 will take over",97856210);
			lr5.setLeaveType(LType.AnnualLeave);
			//Staff 2
			LeaveRecord lr6 = new LeaveRecord(2, LeaveStatus.APPROVED,"Clearing leave", 
					LocalDate.of(2020,7,13),"NA", LocalDate.of(2020,7,17),"NA",6,"Manager 1 in charge",96223444);
			lr6.setLeaveType(LType.AnnualLeave);	
			
			LeaveRecord lr7 = new LeaveRecord(2, LeaveStatus.APPLIED,"Tangs Sale",
					LocalDate.of(2020, 7, 21),"A",LocalDate.of(2020, 7, 21),"N",6,"Manager 1 in charge",9622344);
			lr7.setLeaveType(LType.Compensation);
			
			LeaveRecord lr8 = new LeaveRecord(2, LeaveStatus.APPLIED,"Eye operation",
					LocalDate.of(2020, 10, 12),"NA",LocalDate.of(2020, 10, 23),"NA",6,"staff1",9622344);
			lr8.setLeaveType(LType.MedicalLeave);
			
			LeaveRecord lr9 = new LeaveRecord(2, LeaveStatus.APPROVED,"Staycation",
					LocalDate.of(2020, 11, 2),"NA",LocalDate.of(2020, 11, 3),"NA",6,"staff1",9622344);
			lr9.setLeaveType(LType.AnnualLeave);
			
			LeaveRecord lr10 = new LeaveRecord(2, LeaveStatus.APPLIED,"PSLE results release",
					LocalDate.of(2020, 11, 25),"NA",LocalDate.of(2020, 11, 25),"NA",6,"staff1",9622344);
			lr10.setLeaveType(LType.AnnualLeave);
			//Staff 3
			LeaveRecord lr11 = new LeaveRecord(4, LeaveStatus.APPLIED,"Off-in-lieu (halfday)",
					LocalDate.of(2020, 7, 10),"N",LocalDate.of(2020, 7, 10),"N",7,"staff 4 will take over",93445222);
			lr11.setLeaveType(LType.Compensation);
			
			LeaveRecord lr12 = new LeaveRecord(4, LeaveStatus.APPLIED,"Celebrate Phase 3",
					LocalDate.of(2020, 7, 27),"NA",LocalDate.of(2020, 7, 27),"NA",7,"staff 4 will take over",93445222);
			lr12.setLeaveType(LType.AnnualLeave);
			
			LeaveRecord lr13 = new LeaveRecord(4, LeaveStatus.APPROVED,"Facelift operation",
					LocalDate.of(2020, 8, 3),"NA",LocalDate.of(2020, 8, 14),"NA",7,"staff 4 will take over",93445222);
			lr13.setLeaveType(LType.MedicalLeave);
			
			LeaveRecord lr14 = new LeaveRecord(4, LeaveStatus.APPROVED,"Off-in-lieu (half day)",
					LocalDate.of(2020, 7, 28),"A",LocalDate.of(2020, 7, 28),"A",7,"staff 4 will take over",93445222);
			lr14.setLeaveType(LType.Compensation);
			
			LeaveRecord lr15 = new LeaveRecord(4, LeaveStatus.APPROVED,"Malaysia holiday",
					LocalDate.of(2020, 10, 21),"NA",LocalDate.of(2020, 10, 23),"NA",7,"staff 4 will take over",93445222);
			lr15.setLeaveType(LType.AnnualLeave);
			
			LeaveRecord lr16 = new LeaveRecord(4, LeaveStatus.APPROVED,"Sep school holiday",
					LocalDate.of(2020, 9, 20),"NA",LocalDate.of(2020, 9, 23),"NA",7,"staff 4 will take over",93445222);
			lr16.setLeaveType(LType.AnnualLeave);
			
			LeaveRecord lr17 = new LeaveRecord(4, LeaveStatus.APPROVED,"Nov exam period",
					LocalDate.of(2020, 11, 10),"NA",LocalDate.of(2020, 11, 13),"NA",7,"staff 4 will take over",93445222);
			lr17.setLeaveType(LType.AnnualLeave);
			

			lRepo.save(lr1);
			lRepo.save(lr2);
			lRepo.save(lr3);
			lRepo.save(lr4);
			lRepo.save(lr5);
			lRepo.save(lr6);
			lRepo.save(lr7);
			lRepo.save(lr8);
			lRepo.save(lr9);
			lRepo.save(lr10);
			lRepo.save(lr11);
			lRepo.save(lr12);
			lRepo.save(lr13);
			lRepo.save(lr14);
			lRepo.save(lr15);
			lRepo.save(lr16);
			lRepo.save(lr17);

		};

	}
}