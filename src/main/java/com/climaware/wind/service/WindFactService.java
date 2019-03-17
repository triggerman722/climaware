package com.climaware.wind.service;

import com.climaware.persistence.SystemDataAccess;
import com.climaware.postalcode.service.PostalCodeLocationService;
import com.climaware.weatherstation.service.WeatherStationService;
import com.climaware.wind.model.WindFact;
import com.climaware.wind.model.WindScore;

import java.util.ArrayList;
import java.util.List;

public class WindFactService {

    private PostalCodeLocationService postalCodeLocationService;
    private WeatherStationService weatherStationService;

    public WindFactService() {
        postalCodeLocationService = new PostalCodeLocationService();
        weatherStationService = new WeatherStationService();
    }

    public WindFact get(long id) {
        return (WindFact) SystemDataAccess.get(WindFact.class, id);
    }

    public List<WindFact> getAll() {
        return SystemDataAccess.getAll("select p from WindFact p ");
    }

    public WindFact getByPostalCode(String postalcode) {
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "postalcode";
        tvoObject[0][1] = postalcode;
        List<WindFact> windFacts = SystemDataAccess.getWithParams("select " +
                "new WindFact(" +
                "COALESCE(MAX(p.windspeed), 0)," +
                "COALESCE(MIN(p.windspeed), 0)," +
                "COALESCE(AVG(p.windspeed), 0)," +
                "COUNT(p.windspeed)" +
                ") " +
                "from WindRecord  p where p.postalcode = :postalcode ", tvoObject);

        if (windFacts.size() > 0) {
            return windFacts.get(0);
        }
        return null;
        /*
        List<Object[]> objects = SystemDataAccess.getWithParams("select " +
                "max(p.windspeed) as maximum, " +
                "min(p.windspeed) as minimum, " +
                "avg(p.windspeed) as average, " +
                "count(p.windspeed) as countevents " +
                "from WindRecord  p where p.postalcode = :postalcode ", tvoObject);

        WindFact windFact = new WindFact(objects.get(0));
        return windFact;
         */
    }

    public WindFact getByStationId(String stationid) {
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "stationid";
        tvoObject[0][1] = stationid;
        List<WindFact> windFacts = SystemDataAccess.getWithParams("select " +
                "new WindFact(" +
                "COALESCE(MAX(p.windspeed), 0)," +
                "COALESCE(MIN(p.windspeed), 0)," +
                "COALESCE(AVG(p.windspeed), 0)," +
                "COUNT(p.windspeed)" +
                ") " +
                "from WindRecord  p where p.stationid = :stationid ", tvoObject);

        if (windFacts.size() > 0) {
            return windFacts.get(0);
        }
        return null;
    }
    public List<WindFact> getByLocation(String latitude, String longitude, String distance) {
        Object[] tvoObject = new Object[4];

        tvoObject[0] = latitude;
        tvoObject[1] = latitude;
        tvoObject[2] = longitude;
        tvoObject[3] = distance;

        List<Object[]> objects = SystemDataAccess.getNativeWithParams("SELECT " +
                        "max(t.windspeed) as maximum, " +
                        "min(t.windspeed) as minimum, " +
                        "avg(t.windspeed) as average, " +
                        "count(t.windspeed) as countevents " +
                        "from (SELECT windspeed from WindRecord " +
                        "WHERE (acos(sin(radians(latitude)) * sin(radians(?1)) + " +
                        "cos(radians(latitude)) * cos(radians(?2)) * " +
                        "cos(radians(longitude-(?3)))) * 6371) < ?4 " +
                        " ORDER BY " +
                        "(acos(sin(radians(latitude)) * sin(radians(?1)) + " +
                        "cos(radians(latitude)) * cos(radians(?2)) * " +
                        "cos(radians(longitude-(?3)))) * 6371) ASC) t"
                , tvoObject, Object[].class);

        List<WindFact> windFacts = new ArrayList<>();

        //awful and embarrassing.
        for (Object[] rawEntry : objects) {
            windFacts.add(new WindFact(rawEntry));
        }

        return windFacts;

    }

    public WindScore score(String stationid) {

        WindScore windScore = new WindScore();

        /*
        PostalCodeLocation postalCodeLocation = postalCodeLocationService.getByPostalCode(postalcode);


        List<WindFact> windFacts = getByLocation(
                String.valueOf(postalCodeLocation.getLatitude()),
                String.valueOf(postalCodeLocation.getLongitude()),
                distance
        );
        WindFact windFact = windFacts.get(0);


        WindFact windFact = getByPostalCode(postalCodeLocation.getPostalcode());
        */
        WindFact windFact = getByStationId(stationid);

        windScore.setWindFact(windFact);

        int maxwindspeed = 0;

        maxwindspeed = windFact.getMaximum();

        if (maxwindspeed > 85) {
            windScore.setValue(90);
            windScore.setScore(WindScore.Score.HIGH);
        } else if (maxwindspeed > 50) {
            windScore.setValue(75);
            windScore.setScore(WindScore.Score.MEDIUM);
        } else if (maxwindspeed <= 50) {
            windScore.setValue(25);
            windScore.setScore(WindScore.Score.LOW);
        }

        return windScore;

    }
}
