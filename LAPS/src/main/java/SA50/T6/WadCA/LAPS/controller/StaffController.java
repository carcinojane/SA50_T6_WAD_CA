package SA50.T6.WadCA.LAPS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffController {

	@GetMapping("/login")
	public String Login(String username, String password) {
		return "staff_login";
	}
	
	@GetMapping("/home")
	public String home() {
		//return "staff_homepage";
		return "forward:/Manager/home";
	}
	
	@GetMapping("/apply")
	public String apply() {
		return "staff_applyLeave";
	}
	
	@GetMapping("/balance")
	public String balance() {
		return "staff_leaveBlance";
	}
	
	@GetMapping("/history")
	public String history() {
		return "staff_LeaveHistory";
	}
	
	@GetMapping("/history/details")
	public String leaveDetails() {
		return "staff_LeaveHistory_details";
	}
	
	@GetMapping("/history/details/edit")
	public String editLeaveDetails() {
		return "staff_LeaveHistory_details_edit";
	}
	
	
}
