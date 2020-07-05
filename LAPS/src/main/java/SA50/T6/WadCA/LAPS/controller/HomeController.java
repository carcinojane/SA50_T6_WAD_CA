package SA50.T6.WadCA.LAPS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/LAPS")
public class HomeController {

	@GetMapping
	public String home() {
		return "main_homepage";
	}

}
