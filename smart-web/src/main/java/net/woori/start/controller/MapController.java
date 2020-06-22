package net.woori.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 지도 정보 화면 컨트롤러
 * 
 * @author hgko
 *
 */
@Controller
@RequestMapping("map")
public class MapController {

	@GetMapping("")
	public void main(Model model) {
		
	}
}
