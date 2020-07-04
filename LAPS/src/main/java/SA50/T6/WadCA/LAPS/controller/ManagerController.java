package SA50.T6.WadCA.LAPS.controller;

import java.util.ArrayList;
import java.util.List;

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
import SA50.T6.WadCA.LAPS.model.ManagerReport;
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

	@ModelAttribute("managerReport")
	public ManagerReport managerReport() {
		return new ManagerReport();
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
		Staff manager = (Staff)session.getAttribute("staff");
		ArrayList<LeaveRecord> lrecords = (ArrayList<LeaveRecord>)lservice.findByMangerId(manager.getStaffId());
		session.setAttribute("mlrecords", lrecords);
		model.addAttribute("lrecords",lrecords);
		model.addAttribute("months",lservice.LeaveMonths(lrecords));
		return "manager_subordinateLeave";

	}

	@RequestMapping(value="/reportForm")
	public String managerReport(@ModelAttribute("managerReport") ManagerReport managerReport,BindingResult result,Model model,HttpSession session) {
		Staff staff = (Staff)session.getAttribute("staff");
		List<LeaveRecord> records = lservice.findByMangerId(staff.getStaffId());
		List<LeaveRecord> filterRecords = new ArrayList<>();

		if(managerReport.getStaffId()!=null) {
			records = lservice.findLeaveRecordByStaffId(managerReport.getStaffId());
		}

		Integer month = managerReport.getMonth();
		LeaveStatus lstatus = managerReport.getLeaveStatus();
		LType ltype = managerReport.getLeaveType();

		if(month==0 && lstatus==null && ltype==null) {
			filterRecords= records;
		}
		
		if(month!=0) {
			List<LeaveRecord> mFilter = lservice.findByMonth(records, month);
			if(lstatus!=null && ltype!=null) {
				List<LeaveRecord> sFilter =lservice.findByStatus(mFilter,lstatus);
				filterRecords = lservice.findByLtype(sFilter,ltype);
			}
			else if(lstatus!=null) {
				filterRecords =lservice.findByStatus(mFilter,lstatus);
			}
			else if(ltype!=null) {
				filterRecords = lservice.findByLtype(mFilter,ltype);
			}
			else {
				filterRecords = mFilter;
			}
		}
		
		else if(lstatus!=null && ltype!=null) {
				List<LeaveRecord> sFilter =lservice.findByStatus(records,lstatus);
				filterRecords = lservice.findByLtype(sFilter,ltype);
			}
		else if(lstatus!=null) {
			filterRecords = lservice.findByStatus(records, lstatus);
		}
		
		else if(ltype!=null) {
			filterRecords = lservice.findByLtype(records, ltype);
		}	

	lservice.writeToCSV(filterRecords);

	return "forward:/manager/subordinateLeave";
}


@RequestMapping("/staffLeaveDetails/{id}")
public String leaveDetails(Model model, @PathVariable("id") Integer id) {
	model.addAttribute("leave", lservice.findById(id));
	return "manager_leaveDetails";
}

@GetMapping(value = "/approve/{id}")
public String approveLeave(@PathVariable("id") Integer id, HttpSession session, Model model) {
	boolean status = lservice.approveLeave(id);

	if(status == false) {
		System.out.println(status + "tot");
		model.addAttribute("msg","Unable to approve leave (Insufficient leave balance)!");
		return leaveDetails(model,id);
	}
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
	model.addAttribute("staff", sservice.findStaffById(id));
	model.addAttribute("lrecords", lservice.findLeaveRecordByStaffId(id)) ;
	return "manager_PastLeaveRecords";
}


}