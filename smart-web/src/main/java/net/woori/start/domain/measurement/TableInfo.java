package net.woori.start.domain.measurement;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 계측정보 테이블 도메인
 * 
 * @author hgko
 *
 */
@Data
@NoArgsConstructor
public class TableInfo {

	public TableInfo(String date) {
		this.date = date;
	}

	private String date;
	
	/** A 상층 값 */
	private float valueA1;
	
	/** A 중층 값 */
	private float valueA2;
	
	/** A 하층 값 */
	private float valueA3;
	
	
	/** B 상층 값 */
	private float valueB1;
	
	/** B 중층 값 */
	private float valueB2;
	
	/** B 하층 값 */
	private float valueB3;
	

	/** C 상층 값 */
	private float valueC1;
	
	/** C 중층 값 */
	private float valueC2;
	
	/** C 하층 값 */
	private float valueC3;
	
	
	/** D 상층 값 */
	private float valueD1;
	
	/** D 중층 값 */
	private float valueD2;
	
	/** D 하층 값 */
	private float valueD3;
	
	
	/** E 상층 값 */
	private float valueE1;
	
	/** E 중층 값 */
	private float valueE2;
	
	/** E 하층 값 */
	private float valueE3;
	
	
	/** 기상 데이터 */
	/** 기온 */
	private float temp;
	
	/** 습도 */
	private float hum;
	
	/** 풍속 */
	private float arvlty;
	
	/** 강수량 */
	private float afp;
	
	/** 토양수분 */
	private float soilMitr;
	
	/** 일조량 */
	private float solradQy;
}
