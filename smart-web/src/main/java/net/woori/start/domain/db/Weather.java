package net.woori.start.domain.db;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.woori.start.domain.Domain;
import net.woori.start.domain.weather.Response;

/**
 * 사용자 관리 도메인
 * 
 * @author hgko
 *
 */
@Entity
@Table(name = "tb_weather")
@Data
@NoArgsConstructor
public class Weather implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	/** 온도(150CM) */
	private float temp_150;
	
	/** 습도(150CM) */
	private float hd_150;
	
	/** 풍향(300CM) */
	private String wd_300;
	
	/** 풍속(300CM) */
	private float arvlty_300;
	
	/** 강수량 */
	private float afp;
	
	/** 증발량 */
	@Column
	private String afv;
	
	/** 일사량 */
	private float solradQy;
	
	/** 일조시간 */
	@Column
	private String sunshnTime;
	
	/** 토양수분(10CM) */
	private float soilMitr_10;
	
	public Weather(Response response) {
		
	}
}
