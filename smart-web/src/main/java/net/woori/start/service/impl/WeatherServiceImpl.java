package net.woori.start.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.woori.start.domain.chart.WeatherChartData;
import net.woori.start.domain.db.Weather;
import net.woori.start.repository.WeatherRepository;
import net.woori.start.service.WeatherService;

@Transactional
@Service
public class WeatherServiceImpl implements WeatherService {
	
	@Autowired
	private WeatherRepository weatherRepository;

	@Override
	public Weather get(Integer id) {
		return weatherRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Weather> getList() {
		return weatherRepository.findAll();
	}

	@Override
	public boolean regist(Weather domain) {
		if (isNew(domain)) {
			return weatherRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean update(Weather domain) {
		if (!isNew(domain)) {
			return weatherRepository.save(domain) != null;
		} else {
			return false;
		}	
	}

	@Override
	public boolean delete(Integer id) {
		weatherRepository.deleteById(id);
		return true;
	}

	private boolean isNew(Weather domain) {
		return !weatherRepository.existsById(domain.getId());
	}

	@Override
	public Weather getRecentData() {
		return weatherRepository.getRecentData();
	}

	@Override
	public List<WeatherChartData> getChartList(String startDate, String endDate) {
		return weatherRepository.getChartList(startDate, endDate);
	}

	@Override
	public List<Weather> getList(String startDate, String endDate) {
		return weatherRepository.getList(startDate, endDate);
	}
}
