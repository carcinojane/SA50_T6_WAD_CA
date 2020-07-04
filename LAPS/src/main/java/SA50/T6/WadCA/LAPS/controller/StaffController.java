
package SA50.T6.WadCA.LAPS.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import SA50.T6.WadCA.LAPS.model.LType;
import SA50.T6.WadCA.LAPS.model.LeaveRecord;
import SA50.T6.WadCA.LAPS.model.LeaveStatus;
import SA50.T6.WadCA.LAPS.model.Overtime;
import SA50.T6.WadCA.LAPS.model.Staff;
import SA50.T6.WadCA.LAPS.model.Staff.Designation;
import SA50.T6.WadCA.LAPS.model.Staff.Status;
import SA50.T6.WadCA.LAPS.service.LeaveService;
import SA50.T6.WadCA.LAPS.service.LeaveServiceImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeImpl;
import SA50.T6.WadCA.LAPS.service.LeaveTypeService;
import SA50.T6.WadCA.LAPS.service.NotificationService;
import SA50.T6.WadCA.LAPS.service.NotificationServiceImpl;
import SA50.T6.WadCA.LAPS.service.OvertimeService;
import SA50.T6.WadCA.LAPS.service.OvertimeServiceImpl;
import SA50.T6.WadCA.LAPS.service.StaffService;
import SA50.T6.WadCA.LAPS.service.StaffServiceImpl;
import SA50.T6.WadCA.LAPS.utils.PageBean;
import SA50.T6.WadCA.LAPS.utils.PageUtil;

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

	@Autowired
	protected OvertimeValidator overtimeValidator;

	@Autowired
	protected ApplyLeaveValidator applyLeaveValidator;

	protected NotificationService nservice;

	@Autowired
	public void setNotificationService(NotificationServiceImpl nserviceImpl) {
		this.nservice = nserviceImpl;
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
	public String home(@ModelAttribute("staff") @Valid Staff staff, BindingResult bindingResult, Model model,
			HttpSession session) {
		if (bindingResult.hasErrors() || staff == null) {
			return "staff_login";
		}
		Staff registeredStaff = sservice.findStaffByName(staff.getUsername());
		if (registeredStaff.getStatus() == Status.inactive) {
			model.addAttribute("error", "You do not have permission!!!!!");
			return "staff_login";
		}
		if (!registeredStaff.getPassword().equals(staff.getPassword())) {
			return "staff_login";
		}
		model.addAttribute("staff", registeredStaff);
		session.setAttribute("display", registeredStaff.getUsername());
		session.setAttribute("staff", sservice.findStaffObjByUsername(registeredStaff.getUsername()));
		session.setAttribute("staffId", registeredStaff.getStaffId());

		if (registeredStaff.getDesignation() == Designation.manager) {
			return "redirect:/manager/home";
		}

		return "redirect:/staff/.home";
	}

	@GetMapping("/logout")
	public String logout(@ModelAttribute("staff") Staff staff, Model model, SessionStatus status) {
		status.setComplete();
		return "forward:/staff/login";
	}

	@GetMapping(value = "/apply")
	public String apply(Model model, HttpSession session) {
		Staff staff = (Staff) session.getAttribute("staff");
		model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId(staff.getStaffId()));
		return ("staff_applyLeave");
	}

	@GetMapping("/apply/add")
	public String applyLeave(@ModelAttribute("LeaveRecord") LeaveRecord leaveRecord, Model model, HttpSession session) {
		leaveRecord = new LeaveRecord();
		Staff staff = (Staff) session.getAttribute("staff");
		Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
		model.addAttribute("leaveTypeList", ltservice.findLeaveTypeByDesignation(designation));
		return "staff_applyLeave_add";
	}

	@RequestMapping(value = "/apply/save")
	public String save(@ModelAttribute("LeaveRecord") @Valid LeaveRecord leaveRecord, BindingResult result, Model model,
			HttpSession session) {

		if (result.hasErrors()) {
			return "redirect:/staff/apply/add";
		}
		int staffId = (int) session.getAttribute("staffId");
		Staff staff = sservice.findStaffById(staffId);
		String staffName = staff.getUsername();
		leaveRecord.setStaffId(staffId);

		LocalDate from = leaveRecord.getLeaveStartDate();
		LocalDate to = leaveRecord.getLeaveEndDate();
		float numOfDay = lservice.numOfLeaveApplied(leaveRecord);

		if (leaveRecord.getLeaveType() == LType.Compensation) {
			if (leaveRecord.getLeaveStartTime().contentEquals("NA")
					|| leaveRecord.getLeaveEndTime().contentEquals("NA")) {
				Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
				model.addAttribute("time", "Please speficy the From Time and To Time");
				model.addAttribute("leaveTypeList", ltservice.findLeaveTypeByDesignation(designation));
				return "staff_applyLeave_add";

			}
		}

		if (from.isAfter(to)) {
			Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
			model.addAttribute("msg", "To date should be later than From date");
			model.addAttribute("leaveTypeList", ltservice.findLeaveTypeByDesignation(designation));
			return "staff_applyLeave_add";
			// return "redirect:/staff/apply/add";

		}
		if (leaveRecord.getLeaveType() != LType.MedicalLeave) {
			if (from.isBefore(LocalDate.now())) {
				Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
				model.addAttribute("msg", "From date should be later than today");
				model.addAttribute("leaveTypeList", ltservice.findLeaveTypeByDesignation(designation));
				return "staff_applyLeave_add";
				// return "redirect:/staff/apply/add";
			}
		}

		if (from.getDayOfWeek() == DayOfWeek.SATURDAY || from.getDayOfWeek() == DayOfWeek.SUNDAY
				|| to.getDayOfWeek() == DayOfWeek.SATURDAY || to.getDayOfWeek() == DayOfWeek.SUNDAY) {
			Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
			model.addAttribute("msg", "From or To date falls on PH");
			model.addAttribute("leaveTypeList", ltservice.findLeaveTypeByDesignation(designation));
			return "staff_applyLeave_add";
			// return "redirect:/staff/apply/add";
		}

		if (leaveRecord.getLeaveType() == LType.AnnualLeave) {
			if (numOfDay > staff.getTotalAnnualLeave()) {
				System.out.println(numOfDay);
				System.out.println(staff.getTotalCompensationLeave());
				System.out.println(staff.getStaffId());
				Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
				model.addAttribute("insufficient", "Leave entitlement is not sufficient");
				model.addAttribute("leaveTypeList", ltservice.findLeaveTypeByDesignation(designation));
				return "staff_applyLeave_add";
				// return "redirect:/staff/apply/add";
			}
		}

		if (leaveRecord.getLeaveType() == LType.MedicalLeave) {
			if (numOfDay > staff.getTotalMedicalLeave()) {
				Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
				model.addAttribute("insufficient", "Leave entitlement is not sufficient");
				model.addAttribute("leaveTypeList", ltservice.findLeaveTypeByDesignation(designation));
				return "staff_applyLeave_add";
				// return "redirect:/staff/apply/add";
			}
		}

		if (leaveRecord.getLeaveType() == LType.Compensation) {
			if (numOfDay > staff.getTotalCompensationLeave()) {
				System.out.println(numOfDay);
				System.out.println(staff.getTotalCompensationLeave());
				System.out.println(staff.getStaffId());
				Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
				model.addAttribute("insufficient", "Leave entitlement is not sufficient");
				model.addAttribute("leaveTypeList", ltservice.findLeaveTypeByDesignation(designation));
				return "staff_applyLeave_add";
				// return "redirect:/staff/apply/add";
			}
		}

		int managerId = sservice.findStaffById(staffId).getManager().getStaffId();
		leaveRecord.setManagerId(managerId);

		LeaveStatus leaveStatus = leaveRecord.getLeaveStatus();
		if (leaveStatus == LeaveStatus.APPLIED) {
			leaveRecord.setLeaveStatus(LeaveStatus.UPDATED);
		} else if (leaveStatus == LeaveStatus.APPROVED) {
			leaveRecord.setLeaveStatus(LeaveStatus.CANCELLED);
		} else {
			leaveRecord.setLeaveStatus(LeaveStatus.APPLIED);
		}

		LType type = leaveRecord.getLeaveType();
		if (type == (LType.AnnualLeave) || type == (LType.MedicalLeave)) {
			leaveRecord.setLeaveStartTime("NA");
			leaveRecord.setLeaveEndTime("NA");

		}

//		LocalDate leaveStartDate = leaveRecord.getLeaveStartDate();
//		LocalDate leaveEndDate = leaveRecord.getLeaveEndDate();
//		if(leaveStartDate.getDayOfWeek() == DayOfWeek.SATURDAY 
//				|| leaveStartDate.getDayOfWeek() == DayOfWeek.SUNDAY 
//				|| leaveEndDate.getDayOfWeek() == DayOfWeek.SATURDAY 
//				|| leaveEndDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
//			return "redirect:/staff/apply/add";
//		} else 
		lservice.saveLeaveRecord(leaveRecord);
		Staff reportingManager = sservice.findStaffById(managerId);
		try {
			nservice.sendNotification(reportingManager, staffId, staffName);
		} catch (MailException e) {
			// catch error
		}
		return "redirect:/staff/apply";
	}

	@GetMapping("/apply/cancel/{id}")
	public String cancel(@PathVariable("id") Integer id, HttpSession session, Model model) {
		LeaveRecord leaveRecord = lservice.findById(id);
		
		boolean cancellable =leaveRecord.getLeaveStartDate().isBefore(LocalDate.now()); 
		System.out.println(LocalDate.now());
		
		if(cancellable == true){
			model.addAttribute("cancel","Cancellation not allowed.");
			System.out.println(cancellable);
			return "forward:/staff/history/details/"+id;
			
		}
		
		Staff staff = (Staff) session.getAttribute("staff");
		float numOfLeave = lservice.numOfLeaveApplied(leaveRecord);
		float balance = 0;
		if (leaveRecord.getLeaveType() == LType.AnnualLeave) {
			balance = staff.getTotalAnnualLeave() + numOfLeave;
			staff.setTotalAnnualLeave(balance);
			sservice.saveStaff(staff);
		} else if (leaveRecord.getLeaveType() == LType.MedicalLeave) {
			balance = staff.getTotalMedicalLeave() + numOfLeave;
			staff.setTotalMedicalLeave(balance);
			sservice.saveStaff(staff);
		} else {
			balance = staff.getTotalCompensationLeave() + numOfLeave;
			staff.setTotalCompensationLeave(balance);
			sservice.saveStaff(staff);
		}
		leaveRecord.setLeaveStatus(LeaveStatus.CANCELLED);
		lservice.saveLeaveRecord(leaveRecord);

		return "forward:/staff/apply";
	}

	@GetMapping("/apply/delete/{id}")
	public String delete(@PathVariable("id") Integer id, HttpSession session) {
		LeaveRecord leaveRecord = lservice.findById(id);
		lservice.deleteLeaveRecord(leaveRecord);

		return "forward:/staff/apply";
	}

	@GetMapping("/balance")
	public String balance(Model model, HttpSession session) {
		// int staffId = (int) session.getAttribute("staffId");
		model.addAttribute("staff", sservice.findStaffById((int) session.getAttribute("staffId")));
		return "staff_leaveBalance";
	}

	@GetMapping("/history")
	public String history(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page) {

		Staff staff = (Staff) session.getAttribute("staff");
		String status_param = request.getParameter("status");
		if (status_param == null || status_param == "" || status_param.equals("-1")) {
			status_param = "-1";
		}
		Integer status = Integer.parseInt(status_param);
		int totalNum = lservice.countSize(staff.getStaffId()).size();
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), 5);
		List<LeaveRecord> findLeaveRecordByStaffId = lservice.findLeaveRecordByStaffIdPage(staff.getStaffId(), status,
				pageBean.getStart(), pageBean.getPageSize());
		String genPagination = PageUtil.genPagination("/staff/history", totalNum, Integer.parseInt(page), 5, null);
		
		
		model.addAttribute("pageCode", genPagination);
		model.addAttribute("lrecords", findLeaveRecordByStaffId);
		return "staff_LeaveHistory";
	}

	@GetMapping("/history/details/{id}")
	public String leaveDetails(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("leave", lservice.findById(id));
		return "staff_leaveHistory_details";
	}

	@GetMapping("/history/details/edit/{id}")
	public String editLeaveDetails(Model model, @PathVariable("id") Integer id, HttpSession session) {

		LeaveRecord leaveRecord = lservice.findById(id);
		if (!lservice.checkStatus(leaveRecord)) {
			return "redirect:/staff/history/details/" + id;
		}
		model.addAttribute("leave", leaveRecord);
		Staff staff = (Staff) session.getAttribute("staff");
		Designation designation = sservice.findStaffById(staff.getStaffId()).getDesignation();
		model.addAttribute("leaveTypeList", ltservice.findLeaveTypeByDesignation(designation));
		return "staff_leaveHistory_details_edit";
	}

	@RequestMapping(value = "/history/details/edit/save")
	public String saveUpdatedLeave(@ModelAttribute("LeaveRecord") @Valid LeaveRecord leaveRecord, BindingResult result,
			Model model, HttpSession session, RedirectAttributes redirect) {
		Staff staff = (Staff) session.getAttribute("staff");
		int staffId = staff.getStaffId();
		float numOfDay = lservice.numOfLeaveApplied(leaveRecord);
		redirect.addAttribute("id", leaveRecord.getLeaveId());

		applyLeaveValidator.validate(leaveRecord, result);
		if (result.hasErrors()) {
			return "redirect:/staff/history/details/edit/{id}";
		}

		if (leaveRecord.getLeaveType() == LType.Compensation) {
			if (leaveRecord.getLeaveStartTime() == "NA" || leaveRecord.getLeaveEndTime() == "NA") {
				model.addAttribute("time","Please specify From Time and To Time");
				return "redirect:/staff/history/details/edit/{id}";
			}
		}

		if (leaveRecord.getLeaveType() == LType.AnnualLeave) {
			if (numOfDay > staff.getTotalAnnualLeave()) {
				model.addAttribute("insufficient","Leave entitlement not sufficient");
				return "redirect:/staff/history/details/edit/{id}";
			}
		}

		if (leaveRecord.getLeaveType() == LType.MedicalLeave) {
			if (numOfDay > staff.getTotalMedicalLeave()) {
				model.addAttribute("insufficient","Leave entitlement not sufficient");
				return "redirect:/staff/history/details/edit/{id}";
			}
		}

		if (leaveRecord.getLeaveType() == LType.Compensation) {
			if (numOfDay > staff.getTotalCompensationLeave()) {
				model.addAttribute("insufficient","Leave entitlement not sufficient");
				return "redirect:/staff/history/details/edit/{id}";
			}
		}

		leaveRecord.setStaffId(staffId);

		int managerId = sservice.findStaffById(staffId).getManager().getStaffId();
		leaveRecord.setManagerId(managerId);

		leaveRecord.setLeaveStatus(LeaveStatus.UPDATED);

		LType type = leaveRecord.getLeaveType();
		if (type == (LType.AnnualLeave) || type == (LType.MedicalLeave)) {
			leaveRecord.setLeaveStartTime("NA");
			leaveRecord.setLeaveEndTime("NA");

		}

		lservice.saveLeaveRecord(leaveRecord);
		return "redirect:/staff/apply";
	}

//	@GetMapping("/history/details/delete/{id}")
//	public String deleteLeaveDetails(Model model, @PathVariable("id") Integer id) {
//		lservice.deleteLeaveRecord(lservice.findById(id));
//		return "redirect:/staff/history";
//	}

	@GetMapping("/overtime")
	public String overtime(Model model, HttpSession session) {
		int staffId = (int) session.getAttribute("staffId");
		Staff staff = sservice.findStaffById(staffId);
		model.addAttribute("compLeave", staff.getTotalCompensationLeave());
		model.addAttribute("overtime", new Overtime());
		return "staff_overtime";
	}

	@GetMapping("/overtime/save")
	public String OTsave(@ModelAttribute("overtime") @Valid Overtime overtime, BindingResult result,
			HttpSession session) {

		overtimeValidator.validate(overtime, result);
		if (result.hasErrors()) {
			return "staff_overtime";
		}

		Staff staff = sservice.findStaffById((int) session.getAttribute("staffId"));
		// set staffId;
		overtime.setStaff(staff);
		oservice.SaveOvertime(overtime);
		// calculate total compensation leave
		float currCompLeave = oservice.FindCompensationLeaveAwarded(overtime.getHours());
		float totalCompLeave = staff.getTotalCompensationLeave() + currCompLeave;
		staff.setTotalCompensationLeave(totalCompLeave);
		sservice.saveStaff(staff);

		return "forward:/staff/overtime";
	}

}
