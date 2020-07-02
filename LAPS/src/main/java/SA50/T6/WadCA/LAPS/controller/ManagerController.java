package SA50.T6.WadCA.LAPS.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;

import SA50.T6.WadCA.LAPS.model.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.Staff;
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
		Staff staff = (Staff)session.getAttribute("staff");	
		model.addAttribute("lrecords", lservice.findPendingLeaveRecordByManagerId(staff.getStaffId()));
		return "manager_approval";
	}

	@RequestMapping("/staffLeaveDetails/{id}")
	public String leaveDetails(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("leave", lservice.findById(id));
		return "manager_leaveDetails";
	}

	@GetMapping(value = "/approve/{id}")
	public String approveLeave(@PathVariable("id") Integer id, HttpSession session) {
		lservice.approveLeave(id);
		//return "redirect:/manager/approve/"+id;
		return "redirect:/manager/staffLeaveDetails/"+id;
	}

//	@GetMapping(value = "/reject/{id}")
//	public String rejectLeave(@PathVariable("id") Integer id, HttpSession session) {
//		LeaveRecord record = lservice.findById(id);
//		lservice.rejectLeave(record);
//
//		return "redirect:/manager/staffLeaveDetails/"+id;
//	}
	
	@GetMapping(value = "/reject/{id}")
	public String rejectLeave(@PathVariable("id") Integer id, HttpSession session) {
		lservice.rejectLeave(id);

		return "redirect:/manager/staffLeaveDetails/"+id;
	}

	@GetMapping(value = "/history/{id}")
	public String staffRecord(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId(id)) ;
		return "manager_PastLeaveRecords";
	}

}