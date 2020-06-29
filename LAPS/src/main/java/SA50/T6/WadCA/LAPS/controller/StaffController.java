package SA50.T6.WadCA.LAPS.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveRecord.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
import SA50.T6.WadCA.LAPS.service.LeaveService;
import SA50.T6.WadCA.LAPS.service.LeaveServiceImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeService;
import SA50.T6.WadCA.LAPS.service.StaffService;
import SA50.T6.WadCA.LAPS.service.StaffServiceImpl;

@Controller
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

	@GetMapping("/login")
	public String Login(@ModelAttribute("staff") Staff staff, String username, String password, HttpSession session) {
		session.setAttribute("staffId", staff.getStaffId());
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
	
	@GetMapping("/search")
	public String search(Model model, HttpSession session) {
		Designation designation = sservice.findStaffById((int)session.getAttribute("staffId")).getDesignation();
		List<LeaveStatus> leaveStatus = lservice.findAllLeaveStatus();
		model.addAttribute("leaveTypeList", ltservice.findLeaveTypeNamesByDesignation(designation));
		model.addAttribute("leaveStatus", leaveStatus);
		return ("staff_applyLeave");
	}
	
	@GetMapping("/apply/add")
	public String applyLeave(@ModelAttribute("LeaveRecord") LeaveRecord leaveRecord, Model model, HttpSession session) {
		leaveRecord = new LeaveRecord();
		Designation designation = sservice.findStaffById((int)session.getAttribute("staffId")).getDesignation();
		//model.addAttribute("leaveTypeList", ltservice.findAllLeaveTypeNames());
		model.addAttribute("leaveTypeList", ltservice.findLeaveTypeNamesByDesignation(designation));
		return "staff_applyLeave_add";
	}
	
	@GetMapping("/apply/save")
	public String save(@ModelAttribute("LeaveRecord") LeaveRecord leaveRecord, HttpSession session) {
		if(leaveRecord.getLeaveStatus()!= null) {
			leaveRecord.setLeaveStatus(LeaveStatus.UPDATED);
		} else {
			leaveRecord.setLeaveStatus(LeaveStatus.APPLIED);
		}
		leaveRecord.setManagerId((sservice.findStaffById((int)session.getAttribute("staffId")).getManager().getStaffId()));
		leaveRecord.setStaffId((int)session.getAttribute("staffId"));
		//set managerID, get based on staffId
		//set staffID, get based on staffId
		lservice.saveLeaveRecord(leaveRecord);
		return "forward:/staff/apply";
	}
	
	@GetMapping("/balance")
	public String balance(Model model, HttpSession session) {
		//int staffId = (int) session.getAttribute("staffId");
		model.addAttribute("staff", sservice.findStaffById((int)session.getAttribute("staffId")));
		return "staff_leaveBlance";
	}
	
	@GetMapping("/history")
	public String history(Model model, HttpSession session) {
		//change to session.getAttribute(staffId);
		model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId((int)session.getAttribute("staffId"))) ;
		return "staff_LeaveHistory";
	}
	
	@GetMapping("/history/details")
	public String leaveDetails(Model model, int id) {
		//change to session.getAttribute(staffId);
		model.addAttribute("leave", lservice.findById(id));
		return "staff_LeaveHistory_details";
	}
	
	@GetMapping("/history/details/edit")
	public String editLeaveDetails(Model model, int id) {
		//change to session.getAttribute(staffId);
		model.addAttribute("leave",lservice.findById(id));
		return "staff_LeaveHistory_details_edit";
	}
	
	
	
}
