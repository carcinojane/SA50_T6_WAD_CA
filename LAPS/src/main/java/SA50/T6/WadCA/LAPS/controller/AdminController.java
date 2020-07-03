package SA50.T6.WadCA.LAPS.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

//import java.text.SimpleDateFormat;
//import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import SA50.T6.WadCA.LAPS.model.Admin;
import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
import SA50.T6.WadCA.LAPS.model.Staff.Status;
import SA50.T6.WadCA.LAPS.service.AdminService;
import SA50.T6.WadCA.LAPS.service.AdminServiceImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeService;
import SA50.T6.WadCA.LAPS.service.StaffService;
import SA50.T6.WadCA.LAPS.service.StaffServiceImpl;

@Controller
@SessionAttributes("display")
@RequestMapping("/admin")

public class AdminController {

	@Autowired
	protected AdminService aservice;
	@Autowired
	private StaffService sservice;
	@Autowired
	private LeaveTypeService ltservice;

	@Autowired
	public void setAdminService(AdminServiceImpl aserviceImpl) {
		this.aservice = aserviceImpl;
	}

	@Autowired
	public void setStaffService(StaffServiceImpl sserviceImpl) {
		this.sservice = sserviceImpl;
	}

	@Autowired
	public void setLeaveTypeService(LeaveTypeImpl ltserviceImpl) {
		this.ltservice = ltserviceImpl;
	}

	@GetMapping("/login")
	public String login(@ModelAttribute("admin") Admin admin) {
		admin = new Admin();
		return "admin_login";
	}
	
	@GetMapping("/.home")
	public String menu() {
		return "admin_homepage";
	}
	
	@PostMapping("/home")
	public String home(@ModelAttribute("admin") @Valid Admin admin, BindingResult bindingResult, Model model,
			HttpSession session) {
		if (bindingResult.hasErrors() || admin == null) {
			return "admin_login";
		}
		Admin registeredAdmin = aservice.findAdminByName(admin.getUsername());
		if (!registeredAdmin.getPassword().equals(admin.getPassword())) {
			return "admin_login";
		}
		model.addAttribute("admin", admin);
		session.setAttribute("display", admin.getUsername());
		return "redirect:/admin/.home";
	}

	@GetMapping("/logout")
	public String logout(@ModelAttribute("admin") Admin admin, Model model, SessionStatus status) {
		status.setComplete();
		return "forward:/admin/login";
	}

	@RequestMapping(value = "/homepage")
	public String homepage(Model model) {
		return "admin_homepage";
	}

	@RequestMapping(value = "/manageStaff")
	public String manageStaff(Model model) {
		model.addAttribute("staffs", sservice.findAllActiveStaff());
		return "admin_manageStaff";
	}
	@RequestMapping(value = "/manageStaff/inactive")
	public String ViewInactiveStaff(Model model) {
		model.addAttribute("staffs", sservice.findAllInActiveStaff());
		return "admin_manageStaff_ViewInActiveStaff";
	}

	@GetMapping("/manageStaff/details/{id}")
	public String viewStaffDetaills(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("staff", sservice.findStaffById(id));
		ArrayList<Staff> subordinates=sservice.findSubordinates(sservice.findStaffById(id));
		model.addAttribute("subordinates", subordinates);

		return "admin_manageStaff_details";
	}

	@RequestMapping(value = "/manageStaff/search")
	public String showStaff(@RequestParam(value = "username") String username, Model model) {
		model.addAttribute("staffs", sservice.findSearchStaff(username));
		System.out.println("UserName= " + username + " Staff list" + sservice.findSearchStaff(username));
		return "admin_manageStaff";
	}

	@GetMapping("/manageStaff/add")
	public String addStaff(Model model) {
		model.addAttribute("staff", new Staff());
		ArrayList<String> mnames = sservice.findAllManagerNames();
		model.addAttribute("managerNames", mnames);
		return "admin_manageStaff_add";
	}

	@RequestMapping(value = "manageStaff/save")
	public String saveStaff(@ModelAttribute("staff") @Valid Staff staff, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			ArrayList<String> mnames = sservice.findAllManagerNames();
			model.addAttribute("managerNames", mnames);
			return "admin_manageStaff_add";
		}
		Designation designation = staff.getDesignation();
		if (designation == Staff.Designation.employee) {
			LeaveType annual = ltservice.findLeaveTypeToEdit(LType.AnnualLeave, Staff.Designation.employee);
			LeaveType medical = ltservice.findLeaveTypeToEdit(LType.MedicalLeave, Staff.Designation.employee);
			LeaveType compensation = ltservice.findLeaveTypeToEdit(LType.Compensation, Staff.Designation.employee);
			staff.setTotalAnnualLeave(annual.getEntitlement());
			staff.setTotalMedicalLeave(medical.getEntitlement());
			staff.setTotalCompensationLeave(compensation.getEntitlement());
		}
		else {
			LeaveType annual = ltservice.findLeaveTypeToEdit(LType.AnnualLeave, Staff.Designation.manager);
			LeaveType medical = ltservice.findLeaveTypeToEdit(LType.MedicalLeave, Staff.Designation.manager);
			LeaveType compensation = ltservice.findLeaveTypeToEdit(LType.Compensation, Staff.Designation.manager);
			staff.setTotalAnnualLeave(annual.getEntitlement());
			staff.setTotalMedicalLeave(medical.getEntitlement());
			staff.setTotalCompensationLeave(compensation.getEntitlement());
		}
		Staff manager = sservice.findManagerByUsername(staff.getManager().getUsername());
		manager = sservice.findStaffById(manager.getStaffId());
		staff.setStatus(Status.active);
		staff.setManager(manager);
		sservice.saveStaff(staff);
		return "forward:/admin/manageStaff";
	}

	@GetMapping("/manageStaff/edit/{id}")
	public String editStaffDetails(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("staff", sservice.findStaffById(id));
		ArrayList<String> mnames = sservice.findAllManagerNames();
		model.addAttribute("managerNames", mnames);
		return "admin_manageStaff_edit";
	}

	@RequestMapping(value = "manageStaff/edit/save")
	public String editsaveStaff(@ModelAttribute("staff") @Valid Staff staff, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			ArrayList<String> mnames = sservice.findAllManagerNames();
			model.addAttribute("managerNames", mnames);
			return "admin_manageStaff_edit";
		}
		Staff manager = sservice.findManagerByUsername(staff.getManager().getUsername());
		manager = sservice.findStaffById(manager.getStaffId());
		staff.setStatus(Status.active);
		staff.setManager(manager);
		sservice.saveStaff(staff);
		return "forward:/admin/manageStaff";
	}
	
	//@GetMapping("/manageStaff/delete/{id}")
	@RequestMapping(value="/manageStaff/delete/{id}",method= {RequestMethod.DELETE,RequestMethod.GET})
	public String deleteStaff(@PathVariable("id") Integer id, Model model) {
		Staff staff=sservice.findStaffById(id);
		if(staff.getDesignation()==Designation.manager) {
			ArrayList<Staff> subordinates=sservice.findSubordinates(staff);
			System.out.println("Sub: "+ subordinates);
			for (Staff staff2 : subordinates) {
				staff2.setManager(null);
				System.out.println("Manager null??: "+staff2.getManager());
				sservice.saveStaff(staff2);
			}
		}
		staff.setStatus(Status.inactive);
		sservice.saveStaff(staff);		
		return "forward:/admin/manageStaff";
	}

	@RequestMapping(value = "/manageLeaveType")
	public String manageLeaveType(Model model) {
		model.addAttribute("leavetypes", ltservice.findAllLeaveType());
		return "admin_manageLeaveType";
	}

	@GetMapping("/manageLeaveType/edit/{leaveType}/{designation}")
	public String editLeaveTypeEntitlement(@PathVariable("leaveType") LType leaveType,
			@PathVariable("designation") Designation designation, Model model) {
		model.addAttribute("leavetype", ltservice.findLeaveTypeToEdit(leaveType, designation));
		return "admin_manageLeaveType_edit";
	}

	@RequestMapping(value = "/manageLeaveType/save")
	public String saveLeaveType(@ModelAttribute("leaveType") @Valid LeaveType leavetype, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "admin_manageLeaveType_edit";
		}

		ltservice.save(leavetype);
		return "forward:/admin/manageLeaveType";
	}

}
