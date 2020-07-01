package net.woori.start.domain.weather;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

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
		
		private String temp_150;
		
		private String tmprt_150Top;
		
		private String tmprt_150Lwet;
	}
}
