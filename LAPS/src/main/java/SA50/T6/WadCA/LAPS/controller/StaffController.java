package SA50.T6.WadCA.LAPS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.service.LeaveService;
import SA50.T6.WadCA.LAPS.service.LeaveServiceImpl;

@Controller
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
	protected LeaveService lservice;
	
	@Autowired
	public void setLeaveService(LeaveServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}

	@GetMapping("/login")
	public String Login(String username, String password) {
		return "staff_login";
	}
	
	@GetMapping("/home")
	public String home() {
		return "staff_homepage";
		//return "forward:/manager/home";
	}
	
//	@GetMapping("/list")
//	public String list(@ModelAttribute("LeaveRecord") LeaveRecord leaveRecord, int staffId) {
//		lservice.findLeaveRecordByStaffId(staffId);
//		return ("staff_applyLeave");
//	}
	
	@GetMapping("/apply")
	public String apply(Model model, int staffId) {
		model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId(staffId)) ;
		return ("staff_applyLeave");
	}
	
	@GetMapping("/balance")
	public String balance() {
		return "staff_leaveBlance";
	}
	
	@GetMapping("/history")
	public String history(Model model, int staffId) {
		model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId(staffId)) ;
		return "staff_LeaveHistory";
	}
	
	@GetMapping("/history/details")
	public String leaveDetails(Model model, int id) {
		model.addAttribute("leave", lservice.findById(id));
		return "staff_LeaveHistory_details";
	}
	
	@GetMapping("/history/details/edit")
	public String editLeaveDetails() {
		return "staff_LeaveHistory_details_edit";
	}
	
	
	
}
