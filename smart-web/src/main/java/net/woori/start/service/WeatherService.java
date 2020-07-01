package net.woori.start.service;

import net.woori.start.domain.db.Weather;

public interface WeatherService extends CRUDService<Weather, Integer> {

	Weather getData();
}
