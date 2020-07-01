package net.woori.start.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.woori.start.domain.MapInfo;
import net.woori.start.domain.db.Measurement;
import net.woori.start.domain.db.MeasurementLog;
import net.woori.start.domain.db.PointInfo;
import net.woori.start.domain.param.SearchParam;
import net.woori.start.service.MeasurementLogService;
import net.woori.start.service.MeasurementService;
import net.woori.start.service.PointInfoService;

/**
 * 지도 데이터 조회 서비스
 * 
 * @author hgko
 *
 */
@Service
public class MapService {
	
	@Autowired
	private PointInfoService pointInfoService;
	
	@Autowired
	private MeasurementService measurementService;
	
	@Autowired
	private MeasurementLogService measurementLogService;

	public List<MapInfo> createMapInfo() {
		List<MapInfo> mapInfos = new ArrayList<>();
		
		pointInfoService.getList().forEach(data -> {
			MapInfo mapInfo = new MapInfo();
			mapInfo.setPoint(data.getPointNm());
			mapInfo.setLatitude(data.getLocLat());
			mapInfo.setLongitude(data.getLocLng());
			
			Measurement measurement = measurementService.get(data.getPointSq());
			if (measurement != null) {
				mapInfo.setTemp1(measurement.getTempCh1());
				mapInfo.setTemp2(measurement.getTempCh2());
				mapInfo.setTemp3(measurement.getTempCh3());
				mapInfo.setWater1(measurement.getVwcCh1());
				mapInfo.setWater2(measurement.getVwcCh2());
				mapInfo.setWater3(measurement.getVwcCh3());
			}
			
			mapInfos.add(mapInfo);
		});
		
		return mapInfos;
	}
	
	public MapInfo createMapInfo(SearchParam param) {
		PointInfo pointInfo = pointInfoService.get("A " + param.getSensorPoint().name());
		
		MapInfo mapInfo = new MapInfo();
		mapInfo.setPoint(pointInfo.getPointNm());
		mapInfo.setLatitude(pointInfo.getLocLat());
		mapInfo.setLongitude(pointInfo.getLocLng());
		
		MeasurementLog log = measurementLogService.getCurrentData(pointInfo.getPointSq(), param.getStartDate(), param.getCurrentDate());
		if (log != null) {
			mapInfo.setTemp1(log.getTempCh1());
			mapInfo.setTemp2(log.getTempCh2());
			mapInfo.setTemp3(log.getTempCh3());
			mapInfo.setWater1(log.getVwcCh1());
			mapInfo.setWater2(log.getVwcCh2());
			mapInfo.setWater3(log.getVwcCh3());
		}
		
		return mapInfo;
	}
}
