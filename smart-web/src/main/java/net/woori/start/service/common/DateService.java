package net.woori.start.service.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

/**
 * 날짜 관련 유틸 서비스
 * 
 * @author hgko
 *
 */
@Service
public class DateService {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH:00");
	
	public String stringToDate(String value) {
		try {
			return hourFormat.format(dateFormat.parse(value));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public String stringToDay(String value) {
		try {
			return new SimpleDateFormat("yyyy년 MM월 dd일").format(dayFormat.parse(value));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return value;
	}
}
