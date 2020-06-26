package net.woori.start.service.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class WeatherService {

	/** 기상관측시간자료목록조회 */
	private final String BASE_URL = "http://apis.data.go.kr/1360000/AsosHourlyInfoService/getWthrDataList";
	
	private final String serviceKey = "hs2LSXM7HYbhChv6V%2BaT%2BCpU8ckmAd2BRHbiA7Khzc813PYIVX7aGo7mctF%2F1uLb%2FSH%2BVVkPeCzaJbQouAWwDw%3D%3D";
	
	@PostConstruct
	public void getWthrDataList() {
		StringBuilder urlBuilder = new StringBuilder(BASE_URL); /*URL*/
		try {
	        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
//	        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)*/
	        urlBuilder.append("&" + URLEncoder.encode("dataCd","UTF-8") + "=" + URLEncoder.encode("ASOS", "UTF-8")); /*자료 분류 코드*/
	        urlBuilder.append("&" + URLEncoder.encode("dateCd","UTF-8") + "=" + URLEncoder.encode("HR", "UTF-8")); /*날짜 분류 코드*/
	        urlBuilder.append("&" + URLEncoder.encode("startDt","UTF-8") + "=" + URLEncoder.encode("20100101", "UTF-8")); /*조회 기간 시작일*/
	        urlBuilder.append("&" + URLEncoder.encode("startHh","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*조회 기간 시작시*/
	        urlBuilder.append("&" + URLEncoder.encode("endDt","UTF-8") + "=" + URLEncoder.encode("20100601", "UTF-8")); /*조회 기간 종료일*/
	        urlBuilder.append("&" + URLEncoder.encode("endHh","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*조회 기간 종료시*/
	        urlBuilder.append("&" + URLEncoder.encode("stnIds","UTF-8") + "=" + URLEncoder.encode("603", "UTF-8")); /*종관기상관측 지점 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("schListCnt","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	        
	        System.err.println(urlBuilder.toString());
	        
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			
			System.out.println("Response code: " + conn.getResponseCode());
			
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			StringBuilder sb = new StringBuilder();
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			rd.lines().forEach(line -> {
				sb.append(line);
			});
			
			System.err.println(sb.toString());
			
			rd.close();
			conn.disconnect();

//			StringBuilder sb = new StringBuilder();
//			String line;
//			while ((line = rd.readLine()) != null) {
//				sb.append(line);
//			}
//			System.err.println(sb.toString());
//
//			rd.close();
//			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
