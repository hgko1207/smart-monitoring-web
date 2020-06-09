package net.woori.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.woori.start.domain.EnumType.SensorType;
import net.woori.start.domain.param.SearchParam;
import net.woori.start.service.common.DashboardService;

@Controller
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardService;

	@GetMapping("home")
    public void home(Model model) {
		model.addAttribute("sensorTypes", SensorType.values());
    }
	
	@PostMapping("dashboard/search")
    public ResponseEntity<?> search(@RequestBody SearchParam param) {
		return new ResponseEntity<>(dashboardService.createChartInfo(param), HttpStatus.OK);
    }
}
