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

/**
 * 계측 정보 화면 컨트롤러
 * 
 * @author hgko
 *
 */
@Controller
@RequestMapping("measurement")
public class MeasurementController {
	
	@Autowired
	private MeasurementInfoService measurementInfoService;
	
	@Autowired
	private PointInfoService pointInfoService;
	
	/**
	 * 계측정보 화면
	 * @param model
	 */
	@GetMapping("")
	public void main(Model model) {
		model.addAttribute("points", pointInfoService.getList());
		model.addAttribute("locationTypes", LocationType.values());
		model.addAttribute("sensorTypes", SensorType.values());
		model.addAttribute("chartTypes", ChartType.values());
	}
	
	
	/**
	 * 차트 조회
	 * @param param
	 * @return
	 */
	@PostMapping("search/chart")
    public ResponseEntity<?> searchChart(@RequestBody SearchParam param) {
		return new ResponseEntity<>(measurementInfoService.createChartInfo(param), HttpStatus.OK);
    }
	
	/**
	 * 테이블 조회
	 * @param param
	 * @return
	 */
	@PostMapping("search/table")
    public ResponseEntity<?> searchTable(@RequestBody SearchParam param) {
		return new ResponseEntity<>(HttpStatus.OK);
    }
}
