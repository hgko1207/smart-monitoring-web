package net.woori.start.domain.weather;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/**
 * 기상청 API로 기상정보 조회할 때 쓰이는 도메인
 * 
 * @author hgko
 *
 */
@Data
@XmlRootElement(name = "Root")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Response {

	@XmlElement(name = "Info")
	private Info info;

	@Data
	public static class Info {

		/** 지역 코드 */
		private String stncode;
		
		/** 지역 이름 */
		private String stnname;
		
		/** 일시 */
		private String date;
		
		/** 온도(150CM) */
		private String temp_150;
		
		/** 습도(150CM) */
		private String hd_150;
		
		/** 풍향(300CM) */
		private String wd_300;
		
		/** 풍속(300CM) */
		private float arvlty_300;
		
		/** 강수량 */
		private String afp;
		
		/** 증발량 */
		private String afv;
		
		/** 일사량 */
		private String solradQy;
		
		/** 일조시간 */
		private String sunshnTime;
		
		/** 토양수분(10CM) */
		private float soilMitr_10;
	}
}
