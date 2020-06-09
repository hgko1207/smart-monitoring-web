package net.woori.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import net.woori.start.domain.EnumType.ChartType;
import net.woori.start.domain.EnumType.LocationType;
import net.woori.start.domain.EnumType.SensorType;
import net.woori.start.domain.param.SearchParam;
import net.woori.start.service.PointInfoService;
import net.woori.start.service.common.MeasurementInfoService;

@Controller
@RequestMapping("measurement")
public class MeasurementController {
	
	@Autowired
	private MeasurementInfoService measurementInfoService;
	
	@Autowired
	private PointInfoService pointInfoService;
	
	@GetMapping("")
	public void main(Model model) {
		model.addAttribute("points", pointInfoService.getList());
		model.addAttribute("locationTypes", LocationType.values());
		model.addAttribute("sensorTypes", SensorType.values());
		model.addAttribute("chartTypes", ChartType.values());
	}
	
	@PostMapping("search/chart")
    public ResponseEntity<?> searchChart(@RequestBody SearchParam param) {
		return new ResponseEntity<>(measurementInfoService.createChartInfo(param), HttpStatus.OK);
    }
	
	@PostMapping("search/table")
    public ResponseEntity<?> searchTable(@RequestBody SearchParam param) {
		return new ResponseEntity<>(HttpStatus.OK);
    }
}
