package SA50.T6.WadCA.LAPS.controller;

import java.time.LocalDate;

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
import org.springframework.web.bind.annotation.RequestMapping;

import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.service.StaffService;

@Controller
@RequestMapping("/admin")

public class AdminController {
	
	@Autowired
	private StaffService sservice;
	
	@GetMapping("/login")
	public String login() {
		
        return "admin_login"; 
    }
	
	@GetMapping("/home")
	public String home() {
		
        return "admin_homepage"; 
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
	public String viewStaffDetails(@PathVariable("id") Integer id, Model model) {
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
	public String saveStaff(@ModelAttribute("staff") @Valid Staff staff, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "admin_manageStaff_add";
		}
		
		sservice.saveStaff(staff);
		return "forward:/admin/manageStaff";
	}

}
