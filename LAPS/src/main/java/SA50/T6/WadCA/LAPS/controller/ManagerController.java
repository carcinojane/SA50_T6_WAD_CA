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

import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveRecord;

import SA50.T6.WadCA.LAPS.model.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.service.LeaveService;
import SA50.T6.WadCA.LAPS.service.LeaveServiceImpl;
import SA50.T6.WadCA.LAPS.service.StaffService;
import SA50.T6.WadCA.LAPS.service.StaffServiceImpl;

@Controller
@RequestMapping("/manager")
public class ManagerController{

	@Autowired
	protected LeaveService lservice;

	@Autowired
	public void setLeaveService(LeaveServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}
	
	@Autowired
	protected StaffService sservice;

	@Autowired
	public void setStaffService(StaffServiceImpl sserviceImpl) {
		this.sservice = sserviceImpl;
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
	
	@GetMapping(value = "/subordinateLeave")
	public String approveLeave(Model model,HttpSession session) {
		//lservice.approveLeave(id);
		//return "redirect:/manager/approve/"+id;
		Staff manager = (Staff)session.getAttribute("staff");
		model.addAttribute("lrecords",lservice.findByMangerId(manager.getStaffId()));
		return "manager_subordinateLeave";

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
	
	@GetMapping(value= "/save")
	public String saveLeave(  @ModelAttribute("leave") @Valid LeaveRecord leave, 
			BindingResult bindingResult,  Model model) {
		if (bindingResult.hasErrors()) {
			return "manager_leaveDetails";
		}
		lservice.saveLeaveRecord(leave);
		return "manager_leaveDetails";
	}

	@GetMapping(value = "/history/{id}")
	public String staffRecord(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId(id)) ;
		return "manager_PastLeaveRecords";
	}
	

}