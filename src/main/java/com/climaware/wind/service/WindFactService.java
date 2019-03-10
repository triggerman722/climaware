package com.climaware.wind.service;

import com.climaware.persistence.SystemDataAccess;
import com.climaware.postalcode.model.PostalCodeLocation;
import com.climaware.postalcode.service.PostalCodeLocationService;
import com.climaware.wind.model.WindFact;
import com.climaware.wind.model.WindScore;

import java.util.ArrayList;
import java.util.List;

public class WindFactService {

    private PostalCodeLocationService postalCodeLocationService;

    public WindFactService() {
        postalCodeLocationService = new PostalCodeLocationService();
    }

    public WindFact get(long id) {
        return (WindFact) SystemDataAccess.get(WindFact.class, id);
    }

    public List<WindFact> getAll() {
        return SystemDataAccess.getAll("select p from WindFact p ");
    }


    public List<WindFact> getByLocation(String latitude, String longitude, String distance) {
        Object[] tvoObject = new Object[4];

        tvoObject[0] = latitude;
        tvoObject[1] = latitude;
        tvoObject[2] = longitude;
        tvoObject[3] = distance;

        List<Object[]> objects = SystemDataAccess.getNativeWithParams("SELECT " +
                        "max(windspeed) as maximum, " +
                        "min(windspeed) as minimum, " +
                        "avg(windspeed) as average, " +
                        "count(windspeed) as countevents " +
                        "from WindRecord " +
                        "WHERE (acos(sin(radians(latitude)) * sin(radians(?1)) + " +
                        "cos(radians(latitude)) * cos(radians(?2)) * " +
                        "cos(radians(longitude-(?3)))) * 6371) < ?4"
                , tvoObject, Object[].class);

        List<WindFact> windFacts = new ArrayList<>();

        //awful and embarrassing.
        for (Object[] rawEntry : objects) {
            windFacts.add(new WindFact(rawEntry));
        }

        return windFacts;

    }

    public WindScore score(String postalcode) {

        WindScore windScore = new WindScore();

        PostalCodeLocation postalCodeLocation = postalCodeLocationService.getByPostalCode(postalcode);

        String defaultdistance = "50"; //kilometres

        List<WindFact> windFacts = getByLocation(
                String.valueOf(postalCodeLocation.getLatitude()),
                String.valueOf(postalCodeLocation.getLongitude()),
                defaultdistance
        );

        int avgwindspeed = 0;

        WindFact windFact = windFacts.get(0);

        avgwindspeed = windFact.getAverage();

        if (avgwindspeed > 85) {
            windScore.setValue(90);
            windScore.setScore(WindScore.Score.HIGH);
        } else if (avgwindspeed > 50) {
            windScore.setValue(75);
            windScore.setScore(WindScore.Score.MEDIUM);
        } else if (avgwindspeed <= 50) {
            windScore.setValue(25);
            windScore.setScore(WindScore.Score.LOW);
        }

        return windScore;

    }
}
