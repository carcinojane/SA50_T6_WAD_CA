package SA50.T6.WadCA.LAPS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import SA50.T6.WadCA.LAPS.repo.LeaveRepository;
import SA50.T6.WadCA.LAPS.repo.StaffRepository;

@Controller
@RequestMapping("/manager")
public class ManagerController{
	
	@Autowired
	LeaveRepository leaveRepo;
	StaffRepository staffRepo;
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder) {
		
	}
	
	@GetMapping("/home")
	public String login(String username, String password) {
		return "manager_homepage";
	}

	@GetMapping("/approval")
	public String listAll(Model model) { 
		model.addAttribute("leaves", leaveRepo.findAll());
		return "manager_approval";
	}
	
	@GetMapping("/details/{id}")
	public String showEditForm(Model model, @PathVariable("id") Integer id) {	
		model.addAttribute("leave", leaveRepo.findById(id).get());
		return "manager_leaveDetails";
	}

}
