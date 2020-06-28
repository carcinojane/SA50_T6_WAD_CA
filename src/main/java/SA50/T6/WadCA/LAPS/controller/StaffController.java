package SA50.T6.WadCA.LAPS.controller;


import java.util.ArrayList;

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
	protected StaffService sservice;
	
	@Autowired
	protected StaffServiceImpl smplservice;
	
	@Autowired
	public void setLeaveService(LeaveServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}
	
	@Autowired
	protected LeaveTypeService ltservice;
	
	@Autowired
	protected LeaveTypeImpl lmplservice;
	
	@Autowired
	public void setLeaveTypeService(LeaveTypeImpl ltserviceImpl) {
		this.ltservice = ltserviceImpl;
	}
	
	@Autowired
	public void setStaffService(StaffServiceImpl sserviceImpl) {
		this.sservice = sserviceImpl;
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
	public String history(Model model, HttpSession session) {
		//change to session.getAttribute(staffId);
		Staff userstaff = (Staff) session.getAttribute("staffId");
		LeaveRecord record = new LeaveRecord();
		Staff staff=new Staff();
		staff = smplservice.getStaffbyID(userstaff.getStaffId());

		ArrayList<LeaveRecord> completeLeaveList = new ArrayList<LeaveRecord>();
		completeLeaveList = lmplservice.getCompleteLeavesSheet(record);

		System.out.println(staff.getTotalAnnualLeave());
		model.addAttribute("staff", staff);
		model.addAttribute("completeLeaves",completeLeaveList);
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
