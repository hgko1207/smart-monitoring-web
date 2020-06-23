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

/**
 * 대시보드 화면 컨트롤러
 * 
 * @author hgko
 *
 */
@Controller
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardService;

	/**
	 * 대시보드 화면
	 * @param model
	 */
	@GetMapping("home")
    public void home(Model model) {
		model.addAttribute("sensorTypes", SensorType.values());
    }
	
	/**
	 * 조회
	 * @param param
	 * @return
	 */
	@PostMapping("dashboard/search")
    public ResponseEntity<?> search(@RequestBody SearchParam param) {
		return new ResponseEntity<>(dashboardService.createBarChartInfo(param), HttpStatus.OK);
    }
	
	/**
	 * 조회
	 * @param param
	 * @return
	 */
	@PostMapping("dashboard/search/line")
    public ResponseEntity<?> searchLine(@RequestBody SearchParam param) {
		return new ResponseEntity<>(dashboardService.createLineChartInfo(param), HttpStatus.OK);
    }
}
