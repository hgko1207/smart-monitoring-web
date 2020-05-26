package net.woori.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("measurement")
public class MeasurementController {

	@GetMapping("")
	public void main(Model model) {
		
	}
}
