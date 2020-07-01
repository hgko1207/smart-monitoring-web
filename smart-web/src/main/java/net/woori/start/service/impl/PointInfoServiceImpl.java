package net.woori.start.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.start.domain.EnumType.SensorPointType;
import net.woori.start.domain.db.PointInfo;
import net.woori.start.repository.PointInfoRepository;
import net.woori.start.service.PointInfoService;

@Transactional
@Service
public class PointInfoServiceImpl implements PointInfoService {
	
	@Autowired
	private PointInfoRepository pointInfoRepository;

	@Override
	public PointInfo get(Integer id) {
		return pointInfoRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	@Override
	public List<PointInfo> getList() {
		return pointInfoRepository.findAll().stream()
				.sorted(Comparator.comparing(PointInfo::getPointNm)).collect(Collectors.toList());
	}

	@Override
	public boolean regist(PointInfo domain) {
		if (isNew(domain)) {
			return pointInfoRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(PointInfo domain) {
		if (!isNew(domain)) {
			return pointInfoRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(Integer id) {
		pointInfoRepository.deleteById(id);
		return true;
	}

	private boolean isNew(PointInfo domain) {
		return !pointInfoRepository.existsById(domain.getPointSq());
	}

	@Override
	public PointInfo get(String pointNm) {
		return pointInfoRepository.findByPointNmContaining(pointNm);
	}

	@Override
	public List<PointInfo> getList(SensorPointType sensorPoint) {
		return pointInfoRepository.getList("%" + sensorPoint.name() + "%").stream()
				.sorted(Comparator.comparing(PointInfo::getPointNm)).collect(Collectors.toList());
	}
}
