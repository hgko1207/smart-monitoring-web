package net.woori.start.service.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.EnumType.LocationType;
import net.woori.start.domain.EnumType.SensorType;
import net.woori.start.domain.chart.ChartData;
import net.woori.start.domain.chart.ChartInfo;
import net.woori.start.domain.chart.LineChartSeries;
import net.woori.start.domain.measurement.MeasurementInfo;
import net.woori.start.domain.measurement.TableInfo;
import net.woori.start.domain.param.SearchParam;
import net.woori.start.service.MeasurementService;
import net.woori.start.service.PointInfoService;

/**
 * 계측정보 서비스 
 * 
 * @author hgko
 *
 */
@Service
public class MeasurementInfoService {
	
//	private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH시");
//	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private MeasurementService measurementService;
	
	@Autowired
	private PointInfoService pointInfoService;

	/**
	 * 토양수분, 토양온도 선택 시
	 * @param param
	 * @return
	 */
	public MeasurementInfo createMeasurementInfo(SearchParam param) {
		MeasurementInfo measurementInfo = new MeasurementInfo();
		
		ChartInfo chartInfo = new ChartInfo(param.getSensor());
		Map<String, TableInfo> tableInfoMap = new HashMap<>();
		
		pointInfoService.getList(param.getSensorPoint()).forEach(pointInfo -> {
			LineChartSeries chartSeries = new LineChartSeries(pointInfo.getPointNm());
			
			List<ChartData> measurements = measurementService.getDailyList(pointInfo.getPointSq(), param.getStartDate(), param.getEndDate());
			measurements.forEach(data -> {
				String date = dayFormat.format(data.getDate());
				TableInfo info = tableInfoMap.get(date);
				if (info == null) {
					tableInfoMap.put(date, new TableInfo(date));
				}
			});
			
			measurements.forEach(data -> {
				String date = dayFormat.format(data.getDate());
				TableInfo info = tableInfoMap.get(date);
				if (info != null) {
					if (param.getSensor() == SensorType.토양수분) {
						if (param.getLocation() == LocationType.상층) {
							chartSeries.addDataItem(date, round(data.getWater1()));
						} else if (param.getLocation() == LocationType.중층) {
							chartSeries.addDataItem(date, round(data.getWater2()));
						} else if (param.getLocation() == LocationType.하층) {
							chartSeries.addDataItem(date, round(data.getWater3()));
						}
					} else if (param.getSensor() == SensorType.토양온도) {
						if (param.getLocation() == LocationType.상층) {
							chartSeries.addDataItem(date, round(data.getTemp1()));
						} else if (param.getLocation() == LocationType.중층) {
							chartSeries.addDataItem(date, round(data.getTemp2()));
						} else if (param.getLocation() == LocationType.하층) {
							chartSeries.addDataItem(date, round(data.getTemp3()));
						}
					}
					
					if (pointInfo.getPointNm().contains("A")) {
						if (param.getSensor() == SensorType.토양수분) {
							info.setValueA1(round(data.getWater1()));
							info.setValueA2(round(data.getWater2()));
							info.setValueA3(round(data.getWater3()));
						} else if (param.getSensor() == SensorType.토양온도) {
							info.setValueA1(round(data.getTemp1()));
							info.setValueA2(round(data.getTemp2()));
							info.setValueA3(round(data.getTemp3()));
						}
					} else if (pointInfo.getPointNm().contains("B")) {
						if (param.getSensor() == SensorType.토양수분) {
							info.setValueB1(round(data.getWater1()));
							info.setValueB2(round(data.getWater2()));
							info.setValueB3(round(data.getWater3()));
						} else if (param.getSensor() == SensorType.토양온도) {
							info.setValueB1(round(data.getTemp1()));
							info.setValueB2(round(data.getTemp2()));
							info.setValueB3(round(data.getTemp3()));
						}
					} else if (pointInfo.getPointNm().contains("C")) {
						if (param.getSensor() == SensorType.토양수분) {
							info.setValueC1(round(data.getWater1()));
							info.setValueC2(round(data.getWater2()));
							info.setValueC3(round(data.getWater3()));
						} else if (param.getSensor() == SensorType.토양온도) {
							info.setValueC1(round(data.getTemp1()));
							info.setValueC2(round(data.getTemp2()));
							info.setValueC3(round(data.getTemp3()));
						}
					} else if (pointInfo.getPointNm().contains("D")) {
						if (param.getSensor() == SensorType.토양수분) {
							info.setValueD1(round(data.getWater1()));
							info.setValueD2(round(data.getWater2()));
							info.setValueD3(round(data.getWater3()));
						} else if (param.getSensor() == SensorType.토양온도) {
							info.setValueD1(round(data.getTemp1()));
							info.setValueD2(round(data.getTemp2()));
							info.setValueD3(round(data.getTemp3()));
						}
					} else if (pointInfo.getPointNm().contains("E")) {
						if (param.getSensor() == SensorType.토양수분) {
							info.setValueE1(round(data.getWater1()));
							info.setValueE2(round(data.getWater2()));
							info.setValueE3(round(data.getWater3()));
						} else if (param.getSensor() == SensorType.토양온도) {
							info.setValueE1(round(data.getTemp1()));
							info.setValueE2(round(data.getTemp2()));
							info.setValueE3(round(data.getTemp3()));
						}
					}
					
					tableInfoMap.put(date, info);
				}
			});
			
			chartInfo.addListChartSeries(chartSeries);
		});
		
		List<TableInfo> tableInfos = new ArrayList<>(); 
		
		tableInfoMap.values().stream().sorted(Comparator.comparing(TableInfo::getDate)).forEach(data -> {
			chartInfo.addCategory(data.getDate());
			tableInfos.add(data);
		});
		
		measurementInfo.setSensor(param.getSensor().getName());
		measurementInfo.setChartInfo(chartInfo);
		measurementInfo.setTableInfos(tableInfos);
		
		return measurementInfo;
	}
	
	private float round(float value) {
		return (float) (Math.round(value * 100) / 100.0);
	}
}
