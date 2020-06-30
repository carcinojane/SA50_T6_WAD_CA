package SA50.T6.WadCA.LAPS.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveRecord.LeaveStatus;
import SA50.T6.WadCA.LAPS.service.LeaveService;
import SA50.T6.WadCA.LAPS.service.LeaveServiceImpl;

@Controller
@RequestMapping("/manager")
public class ManagerController{

	@Autowired
	protected LeaveService lservice;
	
	@Autowired
	public void setLeaveService(LeaveServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}
	
	@GetMapping("/home")
	public String home() {
		return "manager_homepage";
	}
	
	@GetMapping("/approval")
	public String approval(Model model, HttpSession session) {
		//model.addAttribute("lrecords", lservice.findPendingLeaveRecordByManagerId((int)session.getAttribute("managerId"))) ;
		return "manager_approval";
	}
	
	@GetMapping("/staffLeaveDetails/{id}")
	public String leaveDetails(Model model, @PathVariable("id") Integer id) {
		//model.addAttribute("leave", lservice.findById(id));
		return "manager_leaveDetails";
	}
	
	@GetMapping(value = "/approve")
	public String approveLeave(@ModelAttribute("LeaveRecord") LeaveRecord leaveRecord, HttpSession session) {
		leaveRecord.setLeaveStatus(LeaveStatus.APPROVED);
		return "manager_approval";
	}
	
	@GetMapping(value = "/reject")
	public String rejectLeave(@ModelAttribute("LeaveRecord") LeaveRecord leaveRecord, HttpSession session) {
		if (leaveRecord.getReasonForRejection()!=null) {
		leaveRecord.setLeaveStatus(LeaveStatus.REJECTED);
		return "forward:/manager/approval";}
		
		return "manager_leaveDetails";	
	}
	
}