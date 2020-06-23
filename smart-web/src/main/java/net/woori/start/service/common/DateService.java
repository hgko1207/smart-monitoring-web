package net.woori.start.service.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

@Service
public class DateService {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH:00");
	
	public String parseDate(String value) {
		try {
			return hourFormat.format(dateFormat.parse(value));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return value;
	}
}