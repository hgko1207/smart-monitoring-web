package net.woori.start.service.common;

import java.net.URLEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.woori.start.domain.weather.Response;

@Service
public class WeatherService {

	private final String BASE_URL = "http://weather.rda.go.kr/openapi/realtime_openapi_xml.jsp";
	
	private final String userId = "sondar20";
	private final String serviceKey = "fhkkcbtdunucdfwhnnextzxlemumvq";
	
//	@PostConstruct
	public void getWthrDataList() {
		StringBuilder urlBuilder = new StringBuilder(BASE_URL);
		try {
			urlBuilder.append("?" + URLEncoder.encode("mberid", "UTF-8") + "=" + userId);
			urlBuilder.append("&" + URLEncoder.encode("regist_ky", "UTF-8") + "=" + serviceKey);
	        
	        RestTemplate restTemplate = new RestTemplate();
	        Response weather = restTemplate.getForObject(urlBuilder.toString(), Response.class);
	        System.out.println(weather);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
