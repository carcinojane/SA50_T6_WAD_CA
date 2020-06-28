package SA50.T6.WadCA.LAPS.controller;

//import java.text.SimpleDateFormat;
//import java.util.Calendar;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")

public class AdminController {
	
	@GetMapping("/login")
	public String login() {
		
        return "admin_login"; 
    }
	
	@GetMapping("/home")
	public String home() {
		
        return "admin_homepage"; 
    }
	
	@GetMapping("/manageStaff")
	public String manageStaff() {
		//returns staff list
        return "admin_manageStaff"; 
    }
	
	@GetMapping("/manageLeaveType")
	public String manageLeaveType() {
		
        return "admin_manageLeaveType"; 
    }
	
	@GetMapping("/manageStaff/details")
	public String viewStaffDetaills() {
		
        return "admin_manageStaff_details"; 
    }
	
	@GetMapping("/manageStaff/add")
	public String addStaff() {
		
        return "admin_manageStaff_add"; 
    }
	
	@GetMapping("/manageStaff/edit")
	public String editStaffDetails() {
		
        return "admin_manageStaff_edit"; 
    }
	

}
