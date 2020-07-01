package SA50.T6.WadCA.LAPS.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import SA50.T6.WadCA.LAPS.model.Admin;
import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.LeaveTypeId;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
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
		return "admin_homepage";
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
		model.addAttribute("staffs", sservice.findAllStaff());
		return "admin_manageStaff";
	}

	@GetMapping("/manageStaff/details/{id}")
	public String viewStaffDetaills(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("staff", sservice.findStaffById(id));
		
		return "admin_manageStaff_details";
	}

	@RequestMapping(value = "/manageStaff/search")
	public String showStaff(@RequestParam(value = "username") String username, Model model) {
		model.addAttribute("staffs", sservice.findSearchStaff(username));
		System.out.println("UserName= "+username+" Staff list"+sservice.findSearchStaff(username));
		return "admin_manageStaff";
	}

	@GetMapping("/manageStaff/add")
	public String addStaff(Model model) {
		model.addAttribute("staff", new Staff());
		model.addAttribute("managers", sservice.findAllManager());
		return "admin_manageStaff_add";
	}

	@GetMapping("/manageStaff/edit/{id}")
	public String editStaffDetails(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("staff", sservice.findStaffById(id));
		return "admin_manageStaff_edit";
	}

	@RequestMapping(value = "manageStaff/save")
	public String saveStaff(@ModelAttribute("staff") @Valid Staff staff,
			BindingResult bindingResult, Model model) {
		System.out.println("Manager: " + staff.getManager());
		if (bindingResult.hasErrors()) {
			System.out.println("!!!Has Error Binding Result!!!!");
			return "admin_manageStaff_add";
		}
		//staff.setManager(manager);
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
		System.out.println(leaveType+ " ," +designation);
		model.addAttribute("leavetype", ltservice.findLeaveTypeToEdit(leaveType,designation));
		return "admin_manageLeaveType_edit";
	}

	@RequestMapping(value = "/manageLeaveType/save")
	public String saveLeaveType(@ModelAttribute("leaveType") @Valid LeaveType leavetype, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "admin_manageLeaveType_edit";
		}

		ltservice.save(leavetype);
		System.out.println("Designation: " + leavetype.getDesignation()  + " Leave Type: "
				+ leavetype.getLeaveType());
		return "forward:/admin/manageLeaveType";
	}


}
