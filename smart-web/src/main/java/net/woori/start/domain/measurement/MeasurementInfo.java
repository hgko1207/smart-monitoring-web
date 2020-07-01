package net.woori.start.domain.measurement;

import java.util.List;

import lombok.Data;
import net.woori.start.domain.chart.ChartInfo;

/**
 * 계측 정보 데이터 정보
 * 
 * @author hgko
 *
 */
@Data
public class MeasurementInfo {
	
	private String sensor;

	private ChartInfo chartInfo;
	
	private List<TableInfo> tableInfos;
	
}
