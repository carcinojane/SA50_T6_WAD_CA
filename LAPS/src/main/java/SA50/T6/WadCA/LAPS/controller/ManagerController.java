package SA50.T6.WadCA.LAPS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerController{
	
	@GetMapping("/home")
	public String login(String username, String password) {
		return "manager_homepage";
	}

}
