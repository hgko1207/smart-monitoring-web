package net.woori.start.controller.mobile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.woori.start.domain.EnumType.SensorPointType;
import net.woori.start.domain.EnumType.SensorType;

@Controller
@RequestMapping("m")
public class MobileController {
	
	@GetMapping("/")
    public String index(Model model) {
		 return "redirect:login";
    }
	
	@GetMapping("login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error,
    		@CookieValue(value = "saved_username", defaultValue = "") String username) {
		if (error != null) {
			model.addAttribute("error", "falied");
		}
		
		if (username != null) {
			model.addAttribute("username", username);
		}
        return "m/login";
    }
	
	@GetMapping("home")
    public void home(Model model) {
		model.addAttribute("sensorTypes", SensorType.values());
		model.addAttribute("sensorPointTypes", SensorPointType.values());
    }
}
