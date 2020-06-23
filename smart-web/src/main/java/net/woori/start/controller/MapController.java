package net.woori.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.woori.start.service.common.MapService;

/**
 * 지도 정보 화면 컨트롤러
 * 
 * @author hgko
 *
 */
@Controller
@RequestMapping("map")
public class MapController {
	
	@Autowired
	private MapService mapService;

	@GetMapping("")
	public void main(Model model) {
	}
	
	@GetMapping("search")
	public ResponseEntity<?> search() {
		return new ResponseEntity<>(mapService.createMapInfo(), HttpStatus.OK);
	}
}
