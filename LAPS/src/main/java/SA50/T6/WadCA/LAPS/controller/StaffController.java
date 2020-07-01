
package SA50.T6.WadCA.LAPS.controller;


import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.LeaveType;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
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
	public String Login(@ModelAttribute("staff") Staff staff, String username, String password, HttpSession session) {
		session.setAttribute("staffId", staff.getStaffId());
		return "staff_login";
	}
	
	@GetMapping("/.home")
	public String menu() {
		return "staff_homepage";
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
		session.setAttribute("staff", staff);

		if (registeredStaff.getDesignation()==Designation.manager){
			return "redirect:/manager/home";
		}

		return "redirect:/staff/.home";
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

	@GetMapping(value="/apply")
	public String apply(Model model, HttpSession session,
			@RequestParam(value="leaveStatus", required=false) LeaveStatus leaveStatus,
			@RequestParam(value="leaveType", required=false)LeaveType leaveType) {

		Staff staff = (Staff)session.getAttribute("staff");
		int id=staff.getStaffId();
		model.addAttribute("staff",staff);
		//model.addAttribute("leaveTypeList",ltservice.findAllLeaveTypeNames());
		//model.addAttribute("leaveStatuses",lservice.findAllLeaveStatus());
		
		ArrayList<LeaveRecord> lrecords = lservice.findLeaveRecordByStaffId(id);

		model.addAttribute("lrecords", lrecords);


		return ("staff_applyLeave");
	}

	//not working yet. 
	//@RequestMapping(value="/search", method = RequestMethod.POST)
	@RequestMapping(value="/search")
	public String search(Model model, HttpSession session, 
			@RequestParam(value="leaveStatus", required=false) LeaveStatus leaveStatus,
			@RequestParam(value="leaveType", required=false)LeaveType leaveType) {
		Staff staff = (Staff)session.getAttribute("staff");
		Integer id=staff.getStaffId();

		//search by leaveStatus and leaveType
		if (leaveStatus!=null && leaveType!=null) {
			//model.addAttribute("lrecords", lservice.findByIdAndStatusAndType(id, leaveStatus, leaveType));
		}
		//search by leaveType
		else if (leaveType!=null) {
			model.addAttribute("lrecords", lservice.findByIdAndLeaveType(id, leaveType));
		}
		//search by leaveStatus
		else if (leaveStatus!=null) {
			//model.addAttribute("lrecords", lservice.findByIdAndLeaveStatus(id, leaveStatus));
		}
		else {
			model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId(id));
		}


		//Staff staff = (Staff)session.getAttribute("staff");
		//		Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
		//		List<LeaveStatus> leaveStatus = lservice.findAllLeaveStatus();
		//		model.addAttribute("leaveTypeList", ltservice.findLeaveTypeNamesByDesignation(designation));
		//		model.addAttribute("leaveStatus", leaveStatus);
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
	public String save(@ModelAttribute("LeaveRecord") @Valid LeaveRecord leaveRecord,BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "staff_applyLeave_add";
		}
		if(leaveRecord.getLeaveType().equals("Annual Leave") || leaveRecord.getLeaveType().equals("Medical Leave")) {
			leaveRecord.setLeaveStartTime('N');
		}
		if(leaveRecord.getLeaveStatus() == LeaveStatus.APPLIED) {
			leaveRecord.setLeaveStatus(LeaveStatus.UPDATED);
		} else if(leaveRecord.getLeaveStatus()==LeaveStatus.APPROVED) {
			leaveRecord.setLeaveStatus(LeaveStatus.CANCELLED);
		}else {
			leaveRecord.setLeaveStatus(LeaveStatus.APPLIED);
		}
		leaveRecord.setManagerId((sservice.findStaffById((int)session.getAttribute("staffId")).getManager().getStaffId()));
		leaveRecord.setStaffId((int)session.getAttribute("staffId"));
		//		if(leaveRecord.getLeaveStartDate().getDayOfWeek() == DayOfWeek.SATURDAY || leaveRecord.getLeaveStartDate().getDayOfWeek() == DayOfWeek.SUNDAY || leaveRecord.getLeaveEndDate().getDayOfWeek() == DayOfWeek.SATURDAY || leaveRecord.getLeaveEndDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
		//			return "staff_applyLeave_add";
		//		} else 

		lservice.saveLeaveRecord(leaveRecord);
		return "forward:/staff/apply";
	}

	@GetMapping("/apply/delete")
	public String delete(@ModelAttribute("LeaveRecord")LeaveRecord leaveRecord, HttpSession session) {
		lservice.deleteLeaveRecord(leaveRecord);
		return"forward:/staff/apply";
	}
	@GetMapping("/balance")
	public String balance(Model model, HttpSession session) {
		//int staffId = (int) session.getAttribute("staffId");
		model.addAttribute("staff", sservice.findStaffById((int)session.getAttribute("staffId")));
		return "staff_leaveBlance";
	}

	@GetMapping("/history")
	public String history(Model model, HttpSession session) {
		model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId((int)session.getAttribute("staffId"))) ;
		return "staff_LeaveHistory";
	}

	@GetMapping("/history/details/{id}")
	public String leaveDetails(@PathVariable("id") Integer id, Model model) {
		//check LeaveStatus
		model.addAttribute("leave", lservice.findById(id));
		LeaveRecord record = lservice.findById(id);
		if(record.getLeaveStatus() == LeaveStatus.APPLIED || record.getLeaveStatus() == LeaveStatus.UPDATED) {
			return "staff_leaveHistory_datails_edit";
		} else {
			return "staff_leaveHistory_details";
		}
	}

	/*
	 * @GetMapping("/history/details") public String leaveDetails(int id, Model
	 * model) { //check LeaveStatus model.addAttribute("leave",
	 * lservice.findById(id)); return "staff_leaveHistory_details";
	 * 
	 * }
	 */

	@GetMapping("/history/details/edit")
	public String editLeaveDetails(Model model, int id) {
		model.addAttribute("leave",lservice.findById(id));
		return "staff_leaveHistory_details_edit";
	}

}

