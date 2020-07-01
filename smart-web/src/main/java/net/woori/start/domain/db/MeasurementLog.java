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
import net.woori.start.domain.Domain;

/**
 * 스마트팜 센서 RAW 데이터 도메인
 * 
 * @author hgko
 *
 */
@Entity
@Table(name = "tb_sf_meas_raw")
@Data
public class MeasurementLog implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int measSq;
	
	/** 장비 시퀀스 */
	private int pointSq;
	
	/** 측정날짜 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date measDt;
	
	/** 수분 데이터_CH1(상층) 데이터 */
	private float vwcCh1;
	
	/** 수분 데이터_CH2(중층) 데이터 */
	private float vwcCh2;
	
	/** 수분 데이터_CH3(하층) 데이터 */
	private float vwcCh3;
	
	/** 온도_CH1(상층) 데이터 */
	private float tempCh1;
	
	/** 온도_CH2(중층) 데이터 */
	private float tempCh2;
	
	/** 온도_CH3(하층) 데이터 */
	private float tempCh3;
	
	/** DB 입력시간 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date insDt;
	
	/** DB Update시간 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date updDt;
}
