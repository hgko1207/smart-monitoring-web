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
import net.woori.start.domain.EnumType.WeatherType;
import net.woori.start.domain.chart.ChartData;
import net.woori.start.domain.chart.ChartInfo;
import net.woori.start.domain.chart.LineChartSeries;
import net.woori.start.domain.measurement.MeasurementInfo;
import net.woori.start.domain.measurement.TableInfo;
import net.woori.start.domain.param.SearchParam;
import net.woori.start.service.MeasurementLogService;
import net.woori.start.service.PointInfoService;
import net.woori.start.service.WeatherService;

/**
 * 계측 데이터 조회 서비스 
 * 
 * @author hgko
 *
 */
@Service
public class MeasurementInfoService {
	
	private final SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Autowired
	private MeasurementLogService measurementService;
	
	@Autowired
	private PointInfoService pointInfoService;
	
	@Autowired
	private WeatherService weatherService;

	/**
	 * 토양수분, 토양온도 선택 시
	 * @param param
	 * @return
	 */
	public MeasurementInfo createMeasurementLog(SearchParam param) {
		MeasurementInfo measurementInfo = new MeasurementInfo();
		
		ChartInfo chartInfo = new ChartInfo(param.getSensor());
		Map<String, TableInfo> tableInfoMap = new HashMap<>();
		
		pointInfoService.getList(param.getSensorPoint()).forEach(pointInfo -> {
			LineChartSeries chartSeries = new LineChartSeries(pointInfo.getPointNm());
			
			List<ChartData> measurements = measurementService.getHourlyList(pointInfo.getPointSq(), param.getStartDate(), param.getEndDate());
			measurements.forEach(data -> {
				String date = hourFormat.format(data.getDate());
				TableInfo info = tableInfoMap.get(date);
				if (info == null) {
					tableInfoMap.put(date, new TableInfo(date));
				}
			});
			
			measurements.forEach(data -> {
				String date = hourFormat.format(data.getDate());
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
			
//			List<MeasurementLog> measurementLogs = measurementService.getList(pointInfo.getPointSq(), param.getStartDate(), param.getEndDate());
//			measurementLogs.forEach(data -> {
//				String date = hourFormat.format(data.getMeasDt());
//				TableInfo info = tableInfoMap.get(date);
//				if (info == null) {
//					tableInfoMap.put(date, new TableInfo(date));
//				}
//			});
//			
//			measurementLogs.forEach(data -> {
//				String date = hourFormat.format(data.getMeasDt());
//				TableInfo info = tableInfoMap.get(date);
//				if (info != null) {
//					if (param.getSensor() == SensorType.토양수분) {
//						if (param.getLocation() == LocationType.상층) {
//							chartSeries.addDataItem(date, round(data.getVwcCh1()));
//						} else if (param.getLocation() == LocationType.중층) {
//							chartSeries.addDataItem(date, round(data.getVwcCh2()));
//						} else if (param.getLocation() == LocationType.하층) {
//							chartSeries.addDataItem(date, round(data.getVwcCh3()));
//						}
//					} else if (param.getSensor() == SensorType.토양온도) {
//						if (param.getLocation() == LocationType.상층) {
//							chartSeries.addDataItem(date, round(data.getTempCh1()));
//						} else if (param.getLocation() == LocationType.중층) {
//							chartSeries.addDataItem(date, round(data.getTempCh2()));
//						} else if (param.getLocation() == LocationType.하층) {
//							chartSeries.addDataItem(date, round(data.getTempCh3()));
//						}
//					}
//					
//					if (pointInfo.getPointNm().contains("A")) {
//						if (param.getSensor() == SensorType.토양수분) {
//							info.setValueA1(round(data.getVwcCh1()));
//							info.setValueA2(round(data.getVwcCh2()));
//							info.setValueA3(round(data.getVwcCh3()));
//						} else if (param.getSensor() == SensorType.토양온도) {
//							info.setValueA1(round(data.getTempCh1()));
//							info.setValueA2(round(data.getTempCh2()));
//							info.setValueA3(round(data.getTempCh3()));
//						}
//					} else if (pointInfo.getPointNm().contains("B")) {
//						if (param.getSensor() == SensorType.토양수분) {
//							info.setValueB1(round(data.getVwcCh1()));
//							info.setValueB2(round(data.getVwcCh2()));
//							info.setValueB3(round(data.getVwcCh3()));
//						} else if (param.getSensor() == SensorType.토양온도) {
//							info.setValueB1(round(data.getTempCh1()));
//							info.setValueB2(round(data.getTempCh2()));
//							info.setValueB3(round(data.getTempCh3()));
//						}
//					} else if (pointInfo.getPointNm().contains("C")) {
//						if (param.getSensor() == SensorType.토양수분) {
//							info.setValueC1(round(data.getVwcCh1()));
//							info.setValueC2(round(data.getVwcCh2()));
//							info.setValueC3(round(data.getVwcCh3()));
//						} else if (param.getSensor() == SensorType.토양온도) {
//							info.setValueC1(round(data.getTempCh1()));
//							info.setValueC2(round(data.getTempCh2()));
//							info.setValueC3(round(data.getTempCh3()));
//						}
//					} else if (pointInfo.getPointNm().contains("D")) {
//						if (param.getSensor() == SensorType.토양수분) {
//							info.setValueD1(round(data.getVwcCh1()));
//							info.setValueD2(round(data.getVwcCh2()));
//							info.setValueD3(round(data.getVwcCh3()));
//						} else if (param.getSensor() == SensorType.토양온도) {
//							info.setValueD1(round(data.getTempCh1()));
//							info.setValueD2(round(data.getTempCh2()));
//							info.setValueD3(round(data.getTempCh3()));
//						}
//					} else if (pointInfo.getPointNm().contains("E")) {
//						if (param.getSensor() == SensorType.토양수분) {
//							info.setValueE1(round(data.getVwcCh1()));
//							info.setValueE2(round(data.getVwcCh2()));
//							info.setValueE3(round(data.getVwcCh3()));
//						} else if (param.getSensor() == SensorType.토양온도) {
//							info.setValueE1(round(data.getTempCh1()));
//							info.setValueE2(round(data.getTempCh2()));
//							info.setValueE3(round(data.getTempCh3()));
//						}
//					}
//					
//					tableInfoMap.put(date, info);
//				}
//			});
			
			chartInfo.addListChartSeries(chartSeries);
		});
		
		List<TableInfo> tableInfos = new ArrayList<>(); 
		
		tableInfoMap.values().stream().sorted(Comparator.comparing(TableInfo::getDate)).forEach(data -> {
			chartInfo.addCategory(data.getDate());
			tableInfos.add(data);
		});
		
		measurementInfo.setSensor(param.getSensor().getName());
		measurementInfo.setUnit(param.getSensor().getUnit());
		measurementInfo.setChartInfo(chartInfo);
		measurementInfo.setTableInfos(tableInfos);
		
		return measurementInfo;
	}
	
	public MeasurementInfo createWeatherLog(SearchParam param) {
		WeatherType weatherType = param.getWeatherType();
		
		ChartInfo chartInfo = new ChartInfo(param.getWeatherType());
		LineChartSeries chartSeries = new LineChartSeries(param.getWeatherType().name());
		
		List<TableInfo> tableInfos = new ArrayList<>();
		
		weatherService.getList(param.getStartDate(), param.getEndDate()).forEach(data -> {
			String date = hourFormat.format(data.getMeasDt());
			TableInfo info = new TableInfo(date);
			info.setTemp(round(data.getTemp_150()));
			info.setHum(round(data.getHd_150()));
			info.setArvlty(round(data.getArvlty_300()));
			info.setAfp(round(data.getAfp()));
			info.setSoilMitr(round(data.getSoilMitr_10()));
			info.setSolradQy(round(data.getSolradQy()));
			tableInfos.add(info);
			
			if (weatherType == WeatherType.기온) {
				if (data.getTemp_150() == 0)
					return;
				chartSeries.addDataItem(date, round(data.getTemp_150()));
			} else if (weatherType == WeatherType.습도) {
				if (data.getHd_150() == 0)
					return;
				chartSeries.addDataItem(date, round(data.getHd_150()));
			} else if (weatherType == WeatherType.풍속) {
				chartSeries.addDataItem(date, round(data.getArvlty_300()));
			} else if (weatherType == WeatherType.강수량) {
				chartSeries.addDataItem(date, round(data.getAfp()));
			} else if (weatherType == WeatherType.일조량) {
				chartSeries.addDataItem(date, round(data.getSolradQy()));
			} else if (weatherType == WeatherType.토양수분) {
				chartSeries.addDataItem(date, round(data.getSoilMitr_10()));
			}
			
			chartInfo.addCategory(date);
		});
		
		chartInfo.addListChartSeries(chartSeries);
		
		MeasurementInfo measurementInfo = new MeasurementInfo();
		measurementInfo.setChartInfo(chartInfo);
		measurementInfo.setTableInfos(tableInfos);
		
		return measurementInfo;
	}
	
	private float round(float value) {
		return (float) (Math.round(value * 100) / 100.0);
	}
}
