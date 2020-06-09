package net.woori.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import net.woori.start.domain.EnumType.SensorType;
import net.woori.start.service.common.ChartService;

@Controller
public class DashboardController {
	
	@Autowired
	private ChartService chartService;

	@GetMapping("home")
    public void home(Model model) {
		model.addAttribute("sensorTypes", SensorType.values());
    }
	
	@PostMapping("dashboard/search")
    public ResponseEntity<?> search(Model model) {
		return new ResponseEntity<>(chartService.createChartInfo(), HttpStatus.OK);
    }
}
