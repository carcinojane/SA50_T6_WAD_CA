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

	@RequestMapping("/staffLeaveDetails/{id}")
	public String leaveDetails(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("leave", lservice.findById(id));
		return "manager_leaveDetails";
	}
	
// 	@GetMapping(value = "/approve")
// 	public String approveLeave(@ModelAttribute("LeaveRecord") LeaveRecord leaveRecord, HttpSession session) {
// 		Staff staff =(Staff)session.getAttribute("staff");
// 		float numOfLeave = lservice.numOfLeaveApplied(leaveRecord);
// 		float balance = 0;
		
// 		if(leaveRecord.getLeaveType() == LType.AnnualLeave) {
// 			if(numOfLeave > staff.getTotalAnnualLeave()) {
// 				return "redirect:/manager/approve";
// 			}
			
// 		}
		
// 		if(leaveRecord.getLeaveType() == LType.MedicalLeave) {
// 			if(numOfLeave > staff.getTotalMedicalLeave()) {
// 				return "redirect:/manager/approve";
// 			}
// 		}
		
// 		if(leaveRecord.getLeaveType() == LType.Compensation) {
// 			if(numOfLeave > staff.getTotalCompensationLeave()) {
// 				return "redirect:/staff/apply/add";
// 			}
// 		}
		
// 		if(leaveRecord.getLeaveType()== LType.AnnualLeave) {
// 			balance = staff.getTotalAnnualLeave() + numOfLeave;
// 			staff.setTotalAnnualLeave(balance);
// 			sservice.saveStaff(staff);
// 		} else if(leaveRecord.getLeaveType()== LType.MedicalLeave) {
// 			balance = staff.getTotalMedicalLeave() + numOfLeave;
// 			staff.setTotalMedicalLeave(balance);
// 			sservice.saveStaff(staff);
// 		} else {
// 			balance = staff.getTotalCompensationLeave() + numOfLeave;
// 			staff.setTotalCompensationLeave(balance);
// 			sservice.saveStaff(staff);
// 		}
// 		leaveRecord.setLeaveStatus(LeaveStatus.APPROVED);
// 		return "manager_approval";

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