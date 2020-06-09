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
 * 지점 정보 도메인
 * 
 * @author hgko
 *
 */
@Entity
@Table(name = "tb_sf_info_point")
@Data
public class PointInfo implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pointSq;
	
	/** 지점 이름 */
	@Column(length = 30)
	private String pointNm;
	
	/** 사용 유무 */
	private boolean useCd;
	
	/** 경도 좌표 */
	private double locLng;
	
	/** 위도 좌표 */
	private double locLat;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date portDt;
	
	/** DB 입력시간 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date insDt;
	
	/** DB Update시간 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date updDt;
}
