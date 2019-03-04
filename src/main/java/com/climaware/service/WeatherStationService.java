package com.climaware.service;


import com.climaware.model.Award;
import com.climaware.model.WeatherStation;
import com.climaware.persistence.SystemDataAccess;

import java.util.Date;
import java.util.List;

public class WeatherStationService {

	public WeatherStation get(long id) {
		return (WeatherStation) SystemDataAccess.get(WeatherStation.class, id);
	}
    public List<WeatherStation> getAll() {
        return SystemDataAccess.getAll("select p from WeatherStation p ");
    }

	public void add(WeatherStation weatherStation) {
		weatherStation.setId(null);

		SystemDataAccess.add(weatherStation);
	}

	public void delete(long id) {
		SystemDataAccess.delete(WeatherStation.class, id);
	}

	public WeatherStation set(long id, WeatherStation weatherStation) {
		if (!doesExist(id))
			return null;

		WeatherStation weatherStationinternal = get(id);
		//Is this user allowed to set this?

		return (WeatherStation) SystemDataAccess.set(WeatherStation.class, id, weatherStation);
	}

	private boolean doesExist(long id){
		Object object = get(id);
		if (object!=null)
			return true;
		return false;
	}

}
