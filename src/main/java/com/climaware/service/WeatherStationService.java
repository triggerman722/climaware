package com.climaware.service;


import com.climaware.model.WeatherStation;
import com.climaware.persistence.SystemDataAccess;

import java.util.List;

public class WeatherStationService {

    public WeatherStation get(long id) {
        return (WeatherStation) SystemDataAccess.get(WeatherStation.class, id);
    }

    public List<WeatherStation> getAll() {
        return SystemDataAccess.getAll("select p from WeatherStation p ");
    }

    public List<WeatherStation> getByLatitudeLongitudeDistance(String latitude, String longitude, String distance) {
        Object[] tvoObject = new Object[4];

        tvoObject[0] = latitude;
        tvoObject[1] = latitude;
        tvoObject[2] = longitude;
        tvoObject[3] = distance;

        //The good news is this is a working query on Apache Derby for geo spatial searches. It is going thru JPA and it does map to the requested object.
        //The bad news is this is a native query with positional arguments. You can see that "latitude" is mapped twice due to this limitation.
        return SystemDataAccess.getNativeWithParams("SELECT * from WeatherStation s WHERE (acos(sin(radians(s.latitude)) * sin(radians(?1)) + cos(radians(s.latitude)) * cos(radians(?2)) * cos(radians(s.longitude-(?3)))) * 6371) < ?4", tvoObject, WeatherStation.class);

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

    private boolean doesExist(long id) {
        Object object = get(id);
        return object != null;
    }

}
