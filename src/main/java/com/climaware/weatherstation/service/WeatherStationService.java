package com.climaware.weatherstation.service;


import com.climaware.persistence.SystemDataAccess;
import com.climaware.weatherstation.model.WeatherStation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        return SystemDataAccess.getNativeWithParams("" +
                        "SELECT * " +
                        "from WeatherStation s WHERE " +
                        "(acos(sin(radians(s.latitude)) * sin(radians(?1)) + cos(radians(s.latitude)) * cos(radians(?2)) * cos(radians(s.longitude-(?3)))) * 6371) < ?4 " +
                        " AND s.lastyear > 2018 " +
                        "ORDER BY " +
                        "(acos(sin(radians(s.latitude)) * sin(radians(?1)) + cos(radians(s.latitude)) * cos(radians(?2)) * cos(radians(s.longitude-(?3)))) * 6371) ASC",
                tvoObject, WeatherStation.class);

    }

    public void add(WeatherStation weatherStation) {
        weatherStation.setId(null);

        SystemDataAccess.add(weatherStation);
    }

    public void delete(long id) {
        SystemDataAccess.delete(WeatherStation.class, id);
    }

    public int deleteAll() {
        return SystemDataAccess.deleteAll("delete from WeatherStation");
    }

    public WeatherStation set(long id, WeatherStation weatherStation) {
        if (!doesExist(id))
            return null;

        WeatherStation weatherStationinternal = get(id);
        //Is this user allowed to set this?

        return (WeatherStation) SystemDataAccess.set(WeatherStation.class, id, weatherStation);
    }

    public void addAll() throws IOException {

        deleteAll();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("weatherstations/stations.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\"", "");

            String parts[] = line.split(",");
            if (parts.length > 2 && !parts[0].equalsIgnoreCase("name")) {
                try {
                    WeatherStation weatherStation = new WeatherStation();
                    weatherStation.setName(parts[0]);
                    weatherStation.setProvince(parts[1]);
                    weatherStation.setStationid(parts[3]);
                    weatherStation.setLatitude(Float.valueOf(parts[6]));
                    weatherStation.setLongitude(Float.valueOf(parts[7]));

                    weatherStation.setFirstyear(Integer.valueOf(parts[11]));
                    weatherStation.setLastyear(Integer.valueOf(parts[12]));
                    add(weatherStation);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        reader.close();


    }

    private boolean doesExist(long id) {
        Object object = get(id);
        return object != null;
    }

}