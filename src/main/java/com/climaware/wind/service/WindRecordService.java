package com.climaware.wind.service;

import com.climaware.persistence.SystemDataAccess;
import com.climaware.postalcode.model.PostalCodeLocation;
import com.climaware.postalcode.service.PostalCodeLocationService;
import com.climaware.wind.model.WindRecord;
import com.climaware.wind.model.WindScore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class WindRecordService {

    private PostalCodeLocationService postalCodeLocationService;

    public WindRecordService() {
        postalCodeLocationService = new PostalCodeLocationService();
    }

    public WindRecord get(long id) {
        return (WindRecord) SystemDataAccess.get(WindRecord.class, id);
    }

    public List<WindRecord> getAll() {
        return SystemDataAccess.getAll("select p from WindRecord p ");
    }

    public void add(WindRecord windRecord) {
        windRecord.setId(null);

        //TODO: Break out the postal code region values, if they are blank

        SystemDataAccess.add(windRecord);
    }

    public void delete(long id) {
        SystemDataAccess.delete(WindRecord.class, id);
    }

    public int deleteAll() {
        return SystemDataAccess.deleteAll("delete from WindRecord");
    }

    public WindRecord set(long id, WindRecord windRecord) {
        if (!doesExist(id))
            return null;

        return (WindRecord) SystemDataAccess.set(WindRecord.class, id, windRecord);
    }

    private boolean doesExist(long id) {
        Object object = get(id);
        return object != null;
    }

    public List<WindRecord> getByLocationTime(String latitude, String longitude, String distance, String year, String month, String day) {

        Object[] tvoObject = new Object[4];

        tvoObject[0] = latitude;
        tvoObject[1] = latitude;
        tvoObject[2] = longitude;
        tvoObject[3] = distance;
        tvoObject[4] = year;
        tvoObject[5] = month;
        tvoObject[6] = day;

        return SystemDataAccess.getNativeWithParams("SELECT * from WindRecord s " +
                "WHERE (acos(sin(radians(s.latitude)) * sin(radians(?1)) + " +
                "cos(radians(s.latitude)) * cos(radians(?2)) * " +
                "cos(radians(s.longitude-(?3)))) * 6371) < ?4 " +
                "AND s.year = ?5 and s.month = ?6 and s.day = ?7 ", tvoObject, WindRecord.class);
    }

    public WindScore score(String year, String month, String day, String postalcode) {

        WindScore windScore = new WindScore();

        PostalCodeLocation postalCodeLocation = postalCodeLocationService.getByPostalCode(postalcode);

        String defaultdistance = "50"; //kilometres

        List<WindRecord> windRecords = getByLocationTime(
                String.valueOf(postalCodeLocation.getLatitude()),
                String.valueOf(postalCodeLocation.getLongitude()),
                defaultdistance,
                year,
                month,
                day
        );

        int maxwindspeed = 0;

        for (WindRecord windRecord : windRecords) {
            int windspeed = windRecord.getWindspeed();
            if (windspeed > maxwindspeed)
                maxwindspeed = windspeed;
        }

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

    public void downloadData(String latitude, String longitude, String stationid, int year, int month, int day) throws IOException {

        URL url = new URL("http://climate.weather.gc.ca/climate_data/bulk_data_e.html?format=csv&stationID=" +
                stationid +
                "&Year=" + year +
                "&Month=" + month +
                "&Day=" + day +
                "&timeframe=1&submit=Download+Data");

        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\"", "");
            String parts[] = line.split(",");
            if (parts.length > 2 && !parts[0].equalsIgnoreCase("name")) {
                try {
                    WindRecord windRecord = new WindRecord();
                    windRecord.setYear(Integer.parseInt(parts[1]));
                    windRecord.setMonth(Integer.parseInt(parts[2]));
                    windRecord.setDay(Integer.parseInt(parts[3]));
                    windRecord.setTime(parts[4]);
                    windRecord.setWindspeed(Integer.parseInt(parts[13]));
                    windRecord.setLatitude(Float.parseFloat(latitude));
                    windRecord.setLongitude(Float.parseFloat(longitude));

                    add(windRecord);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
