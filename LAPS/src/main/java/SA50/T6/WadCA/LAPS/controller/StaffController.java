package SA50.T6.WadCA.LAPS.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveRecord.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.service.LeaveService;
import SA50.T6.WadCA.LAPS.service.LeaveServiceImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeService;
import SA50.T6.WadCA.LAPS.service.StaffService;
import SA50.T6.WadCA.LAPS.service.StaffServiceImpl;

@Controller
@SessionAttributes("display")
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
	protected LeaveService lservice;
	
	@Autowired
	public void setLeaveService(LeaveServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}
	
	@Autowired
	protected LeaveTypeService ltservice;
	
	@Autowired
	public void setLeaveTypeService(LeaveTypeImpl ltserviceImpl) {
		this.ltservice = ltserviceImpl;
	}
	
	@Autowired
	protected StaffService sservice;
	
	@Autowired
	public void setStaffService(StaffServiceImpl sserviceImpl) {
		this.sservice = sserviceImpl;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		
	}

	@GetMapping("/login")
	public String Login(@ModelAttribute("staff") Staff staff) {
		staff=new Staff();
		return "staff_login";
	}
	
	@PostMapping("/home")
	public String home(@ModelAttribute("staff") @Valid Staff staff, BindingResult bindingResult,
			Model model, HttpSession session) {
		if(bindingResult.hasErrors()||staff==null) {
			return "staff_login";
		} 
		Staff registeredStaff = sservice.findStaffByName(staff.getUsername());
		if(!registeredStaff.getPassword().equals(staff.getPassword())) {
			return "staff_login";
		}
		model.addAttribute("staff", staff);
		session.setAttribute("display", staff.getUsername());
		return "staff_homepage";
		//return "forward:/manager/home";
	}
	
	@GetMapping("/logout")
	public String logout(@ModelAttribute("staff") Staff staff, Model model, SessionStatus status) {
		status.setComplete();
		return "forward:/staff/login";
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
	
	@GetMapping("/apply/add")
	public String applyLeave(@ModelAttribute("LeaveRecord") LeaveRecord leaveRecord, Model model) {
		leaveRecord = new LeaveRecord();
		model.addAttribute("leaveTypeList", ltservice.findAllLeaveTypeNames());
		return "staff_applyLeave_add";
	}
	
	@GetMapping("/apply/save")
	public String save(@ModelAttribute("LeaveRecord") LeaveRecord leaveRecord) {
		leaveRecord.setLeaveStatus(LeaveStatus.APPLIED);
		//set managerID, get based on staffId
		//set staffID, get based on staffId
		lservice.saveLeaveRecord(leaveRecord);
		return "staff_applyLeave";
	}
	
	@GetMapping("/balance")
	public String balance(Model model, int staffId) {
		model.addAttribute("staff", sservice.findStaffById(staffId));
		return "staff_leaveBlance";
	}
	
	@GetMapping("/history")
	public String history(Model model, int staffId) {
		//change to session.getAttribute(staffId);
		model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId(staffId)) ;
		return "staff_LeaveHistory";
	}
	
	@GetMapping("/history/details")
	public String leaveDetails(Model model, int id) {
		//change to session.getAttribute(staffId);
		model.addAttribute("leave", lservice.findById(id));
		return "staff_LeaveHistory_details";
	}
	
	@GetMapping("/history/details/edit")
	public String editLeaveDetails() {
		//change to session.getAttribute(staffId);
		return "staff_LeaveHistory_details_edit";
	}
	
	
	
}
