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

import net.woori.start.domain.param.SearchParam;
import net.woori.start.service.common.MeasurementService;

@Controller
@RequestMapping("measurement")
public class MeasurementController {
	
	@Autowired
	private MeasurementService measurementService;

	@GetMapping("")
	public void main(Model model) {
	}
	
	@PostMapping("search/chart")
    public ResponseEntity<?> searchChart(@RequestBody SearchParam param) {
		return new ResponseEntity<>(measurementService.createChartInfo(param), HttpStatus.OK);
    }
	
	@PostMapping("search/table")
    public ResponseEntity<?> searchTable(@RequestBody SearchParam param) {
		return new ResponseEntity<>(HttpStatus.OK);
    }
}
