package net.woori.start.service.common;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.EnumType.LocationType;
import net.woori.start.domain.EnumType.SensorType;
import net.woori.start.domain.chart.ChartInfo;
import net.woori.start.domain.chart.LineChartSeries;
import net.woori.start.domain.db.PointInfo;
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
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private MeasurementService measurementService;
	
	@Autowired
	private PointInfoService pointInfoService;

	public ChartInfo createChartInfo(SearchParam param) {
		ChartInfo chartInfo = new ChartInfo(param.getSensor());
		
		if (param.getPoint().equals("전체")) {
			pointInfoService.getList().forEach(pointInfo -> {
				if (!pointInfo.getPointNm().contains("사람")) {
					LineChartSeries chartSeries = new LineChartSeries(pointInfo.getPointNm());
					
					measurementService.getList(pointInfo.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
						String date = dateFormat.format(data.getMeasDt());
						chartInfo.addCategory(date);
						if (param.getSensor() == SensorType.토양수분) {
							if (param.getLocation() == LocationType.상층) {
								chartSeries.addDataItem(date, data.getVwcCh1());
							} else if (param.getLocation() == LocationType.중층) {
								chartSeries.addDataItem(date, data.getVwcCh2());
							} else if (param.getLocation() == LocationType.하층) {
								chartSeries.addDataItem(date, data.getVwcCh3());
							}
						} else if (param.getSensor() == SensorType.토양온도) {
							if (param.getLocation() == LocationType.상층) {
								chartSeries.addDataItem(date, data.getTempCh1());
							} else if (param.getLocation() == LocationType.중층) {
								chartSeries.addDataItem(date, data.getTempCh2());
							} else if (param.getLocation() == LocationType.하층) {
								chartSeries.addDataItem(date, data.getTempCh3());
							}
						}
					});
					
					chartInfo.addListChartSeries(chartSeries);
				}
			});
		} else {
			PointInfo pointInfo = pointInfoService.get(Integer.parseInt(param.getPoint()));
			LineChartSeries chartSeries = new LineChartSeries(pointInfo.getPointNm());
			
			measurementService.getList(pointInfo.getPointSq(), param.getStartDate(), param.getEndDate()).forEach(data -> {
				chartInfo.addCategory(dateFormat.format(data.getMeasDt()));
				if (param.getSensor() == SensorType.토양수분) {
					if (param.getLocation() == LocationType.상층) {
						chartSeries.addDataItem(data.getVwcCh1());
					} else if (param.getLocation() == LocationType.중층) {
						chartSeries.addDataItem(data.getVwcCh2());
					} else if (param.getLocation() == LocationType.하층) {
						chartSeries.addDataItem(data.getVwcCh3());
					}
				} else if (param.getSensor() == SensorType.토양온도) {
					if (param.getLocation() == LocationType.상층) {
						chartSeries.addDataItem(data.getTempCh1());
					} else if (param.getLocation() == LocationType.중층) {
						chartSeries.addDataItem(data.getTempCh2());
					} else if (param.getLocation() == LocationType.하층) {
						chartSeries.addDataItem(data.getTempCh3());
					}
				}
			});
			
			chartInfo.addListChartSeries(chartSeries);
		}
		
		return chartInfo;
	}
}
