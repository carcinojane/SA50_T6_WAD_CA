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

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import SA50.T6.WadCA.LAPS.model.Admin;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.service.AdminService;
import SA50.T6.WadCA.LAPS.service.AdminServiceImpl;

import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.service.StaffService;

@Controller
@SessionAttributes("display")
@RequestMapping("/admin")

public class AdminController {
	
	@Autowired
	protected AdminService aservice;
	private StaffService sservice;
	
  @Autowired
	public void setAdminService(AdminServiceImpl aserviceImpl) {
		this.aservice=aserviceImpl;
	}
  	
	@GetMapping("/login")
	public String login(@ModelAttribute("admin") Admin admin) {
		admin = new Admin();
        return "admin_login"; 
    }
	
	@PostMapping("/home")
	public String home(@ModelAttribute("admin") @Valid Admin admin, BindingResult bindingResult,
			Model model, HttpSession session) {
		if(bindingResult.hasErrors()||admin==null) {
			return "admin_login";
		} 
		Admin registeredAdmin = aservice.findAdminByName(admin.getUsername());
		if(!registeredAdmin.getPassword().equals(admin.getPassword())) {
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
	
	@GetMapping("/manageStaff")
	public String manageStaff() {
		return null;
		//returns staff list
	}
		
	@RequestMapping(value = "/manageStaff")
	public String manageStaff(Model model) {
		model.addAttribute("staffs", sservice.findAllStaff());

        return "admin_manageStaff"; 
    }
	
	@GetMapping("/manageLeaveType")
	public String manageLeaveType() {
		
        return "admin_manageLeaveType"; 
    }
	
	@GetMapping("/manageStaff/details/{id}")
	public String viewStaffDetaills(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("staff", sservice.findStaffById(id));
        return "admin_manageStaff_details"; 
    }
	
	@GetMapping("/manageStaff/add")
	public String addStaff(Model model) {
		model.addAttribute("staff", new Staff());
        return "admin_manageStaff_add"; 
    }
	
	@GetMapping("/manageStaff/edit/{id}")
	public String editStaffDetails(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("staff", sservice.findStaffById(id));
        return "admin_manageStaff_edit"; 
    }
	
	@RequestMapping(value = "manageStaff/save")
	public String saveFacility(@ModelAttribute("staff") @Valid Staff staff, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "admin_manageStaff_edit";
		}
		
		sservice.saveStaff(staff);
		return "forward:/admin/manageStaff";
	}

}
