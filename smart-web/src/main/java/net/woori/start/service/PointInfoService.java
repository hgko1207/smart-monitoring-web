package net.woori.start.service;

import java.util.List;

import net.woori.start.domain.EnumType.SensorPointType;
import net.woori.start.domain.db.PointInfo;

public interface PointInfoService extends CRUDService<PointInfo, Integer> {

	PointInfo get(String pointNm);

	List<PointInfo> getList(SensorPointType sensorPoint);
}
