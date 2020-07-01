package net.woori.start.domain.measurement;

import java.util.List;

import lombok.Data;
import net.woori.start.domain.chart.ChartInfo;

@Data
public class MeasurementInfo {
	
	private String sensor;

	private ChartInfo chartInfo;
	
	private List<TableInfo> tableInfos;
	
}
