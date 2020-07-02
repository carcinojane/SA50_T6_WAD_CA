
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
import SA50.T6.WadCA.LAPS.model.Overtime;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
import SA50.T6.WadCA.LAPS.service.LeaveService;
import SA50.T6.WadCA.LAPS.service.LeaveServiceImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeService;
import SA50.T6.WadCA.LAPS.service.OvertimeService;
import SA50.T6.WadCA.LAPS.service.OvertimeServiceImpl;
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
	
	@Autowired
	protected OvertimeService oservice;
	
	@Autowired
	public void setOvertimeService(OvertimeServiceImpl oserviceImpl) {
		this.oservice = oserviceImpl;
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
		model.addAttribute("staff", registeredStaff);
		session.setAttribute("display", registeredStaff.getUsername());
		session.setAttribute("staff", sservice.findStaffObjByUsername(registeredStaff.getUsername()));
		session.setAttribute("staffId", registeredStaff.getStaffId());

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


	@GetMapping(value="/apply")
	public String apply(Model model, HttpSession session) {
		Staff staff = (Staff)session.getAttribute("staff");
		model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId(staff.getStaffId()));
		return ("staff_applyLeave");
	}

	

	@GetMapping("/apply/add")
	public String applyLeave(@ModelAttribute("LeaveRecord") LeaveRecord leaveRecord, Model model, HttpSession session) {
		leaveRecord = new LeaveRecord();
		Staff staff = (Staff)session.getAttribute("staff");
		Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
		//model.addAttribute("leaveTypeList", ltservice.findAllLeaveTypeNames());
//		model.addAttribute("leaveTypeList", ltservice.findLeaveTypeNamesByDesignation(designation));
		model.addAttribute("leaveTypeList", ltservice.findByDesignation(designation));
		
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
		return "staff_leaveBalance";
	}

	@GetMapping("/history")
	public String history(Model model, HttpSession session) {
		int staffId =(int)session.getAttribute("staffId");
		Staff staff = sservice.findStaffById(staffId);
		model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId(staff.getStaffId())) ;
		return "staff_LeaveHistory";
	}

	@GetMapping("/history/details/{id}")
	public String leaveDetails(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("leave", lservice.findById(id));
		return "staff_leaveHistory_details";
	}

	@GetMapping("/history/details/edit/{id}")
	public String editLeaveDetails(Model model, @PathVariable("id") Integer id) {
		LeaveRecord leaveRecord=lservice.findById(id);
		if(!lservice.checkStatus(leaveRecord)) {
			return "redirect:/staff/history/details/"+id;
		}
		model.addAttribute("leave",leaveRecord);
			return "staff_leaveHistory_details_edit";
	}
	
	@GetMapping("/history/details/delete/{id}")
	public String deleteLeaveDetails(Model model, @PathVariable("id") Integer id) {
		lservice.deleteLeaveRecord(lservice.findById(id));
			return "redirect:/staff/history";
	}
	
	@GetMapping("/overtime")
	public String overtime(Model model, HttpSession session) {
		int staffId =(int)session.getAttribute("staffId");
		Staff staff = sservice.findStaffById(staffId);
		model.addAttribute("compLeave", staff.getTotalCompensationLeave());
		model.addAttribute("overtime", new Overtime());
		return "staff_overtime";
	}
	
	@RequestMapping(value="/overtime/save")
	public String OTsave(@ModelAttribute("overtime") @Valid Overtime overtime, BindingResult result,HttpSession session) {
		if (result.hasErrors()) {
			return "staff_overtime";
		}
		Staff staff = sservice.findStaffById((int)session.getAttribute("staffId"));
		//set staffId;
		overtime.setStaff(staff);
		oservice.SaveOvertime(overtime);
		//calculate total compensation leave 
		float currCompLeave = oservice.FindCompensationLeaveAwarded(overtime.getHours());
		float totalCompLeave = staff.getTotalCompensationLeave() + currCompLeave;
		staff.setTotalCompensationLeave(totalCompLeave);
		sservice.saveStaff(staff);
		
		return "staff_homepage";
	}


}

