package com.climaware.wind.service;

import com.climaware.persistence.SystemDataAccess;
import com.climaware.postalcode.model.PostalCodeLocation;
import com.climaware.postalcode.service.PostalCodeLocationService;
import com.climaware.wind.model.WindRecord;
import com.climaware.wind.model.WindScore;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public List<WindRecord> getAllPaged(int offset, int pagesize) {
        return SystemDataAccess.getAllPaged("select p from WindRecord p ", offset, pagesize);
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

        Object[] tvoObject = new Object[7];

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
                "AND s.year0 = ?5 and s.month = ?6 and s.day = ?7 " +
                " ORDER BY " +
                "(acos(sin(radians(s.latitude)) * sin(radians(?1)) + " +
                "cos(radians(s.latitude)) * cos(radians(?2)) * " +
                "cos(radians(s.longitude-(?3)))) * 6371) ASC", tvoObject, WindRecord.class);
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

    public void downloadData1(int year, int month, int day) throws IOException {

        URL url = new URL("ftp://client_climate@ftp.tor.ec.gc.ca/Pub/Get_More_Data_Plus_de_donnees/Station Inventory EN.csv");

        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\"", "");
            appendToFile("stations2.csv", line);
        }
        reader.close();
    }

    public void downloadData() throws IOException {

        deleteAll();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("weatherstations/stations.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\"", "");

            String parts[] = line.split(",");
            if (parts.length > 2 && !parts[0].equalsIgnoreCase("name")) {
                try {

                    int lastyear = (parts[12] != null) ? (Integer.parseInt(parts[12])) : 0;
                    if (lastyear < 2015) {
                        continue;
                    }

                    String province = (parts[1] != null) ? (parts[1]) : "";
                    if (!province.equalsIgnoreCase("ontario")) {
                        continue;
                    }
                    System.out.println("Gathering records for: " + parts[0]);

                    String stationId = parts[3];
/*
                    List<PostalCodeLocation> postalCodeLocations = postalCodeLocationService.getByLatitudeLongitudeDistance(
                            parts[6],
                            parts[7],
                            "20"
                    );
*/
                    Calendar myDate = Calendar.getInstance();
                    myDate.add(Calendar.YEAR, -5);

                    for (int i = 0; i < 60; i++) {

                        System.out.println("About to sleep for a half second");
                        Thread.sleep(500);
                        System.out.println("And we're back...");

                        String datetime = new SimpleDateFormat("yyyy-MM-dd").format(myDate.getTime());
                        System.out.println("Getting data for: " + datetime);


                        URL windurl = new URL("http://climate.weather.gc.ca/climate_data/bulk_data_e.html?format=csv&stationID=" +
                                stationId +
                                "&Year=" + myDate.get(Calendar.YEAR) +
                                "&Month=" + myDate.get(Calendar.MONTH) +
                                "&Day=1" +
                                "&timeframe=1&submit=Download+Data");

                        URLConnection windconn = windurl.openConnection();
                        InputStream windinputStream = windconn.getInputStream();
                        BufferedReader windreader = new BufferedReader(new InputStreamReader(windinputStream));

                        String windline;
                        while ((windline = windreader.readLine()) != null) {
                            windline = windline.replaceAll("\"", "");
                            String windparts[] = windline.split(",");
                            if (windparts.length > 2 && !windparts[0].equalsIgnoreCase("Date/Time")) {
                                try {
                                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./data/" + stationId + ".csv", true)));
                                    out.println(windline);
                                    out.close();

                                    /*

                                    WindRecord windRecord = new WindRecord();
                                    windRecord.setYear(Integer.parseInt(windparts[1]));
                                    windRecord.setMonth(Integer.parseInt(windparts[2]));
                                    windRecord.setDay(Integer.parseInt(windparts[3]));
                                    windRecord.setTime(windparts[4]);
                                    windRecord.setWindspeed(Integer.parseInt(windparts[13]));
                                    windRecord.setLatitude(Float.parseFloat(parts[6]));
                                    windRecord.setLongitude(Float.parseFloat(parts[7]));
                                    windRecord.setStationid(stationId);

                                    add(windRecord);
                                    */


                                } catch (Exception e) {
                                }
                            }
                        }
                        windreader.close();

                        myDate.add(Calendar.MONTH, 1);
                    }

                } catch (Exception e) {

                }
            }
        }
        reader.close();
    }

    public void downloadData6(int year, int month, int day) throws IOException {

        deleteAll();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("weatherstations/stations.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\"", "");

            String parts[] = line.split(",");
            if (parts.length > 2 && !parts[0].equalsIgnoreCase("name")) {
                try {

                    int lastyear = (parts[12] != null) ? (Integer.parseInt(parts[12])) : 0;
                    if (lastyear < 2015) {
                        continue;
                    }

                    String province = (parts[1] != null) ? (parts[1]) : "";
                    if (!province.equalsIgnoreCase("ontario")) {
                        continue;
                    }
                    System.out.println("Gathering records for: " + parts[0]);


                    URL windurl = new URL("http://climate.weather.gc.ca/climate_data/bulk_data_e.html?format=csv&stationID=" +
                            parts[3] +
                            "&Year=" + year +
                            "&Month=" + month +
                            "&Day=" + day +
                            "&timeframe=1&submit=Download+Data");

                    URLConnection windconn = windurl.openConnection();
                    InputStream windinputStream = windconn.getInputStream();
                    BufferedReader windreader = new BufferedReader(new InputStreamReader(windinputStream));

                    String windline;
                    while ((windline = windreader.readLine()) != null) {
                        windline = windline.replaceAll("\"", "");
                        String windparts[] = windline.split(",");
                        if (windparts.length > 2) {
                            try {
                                WindRecord windRecord = new WindRecord();
                                windRecord.setYear(Integer.parseInt(windparts[1]));
                                windRecord.setMonth(Integer.parseInt(windparts[2]));
                                windRecord.setDay(Integer.parseInt(windparts[3]));
                                windRecord.setTime(windparts[4]);
                                windRecord.setWindspeed(Integer.parseInt(windparts[13]));
                                windRecord.setLatitude(Float.parseFloat(parts[6]));
                                windRecord.setLongitude(Float.parseFloat(parts[7]));

                                add(windRecord);

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    windreader.close();
                } catch (Exception e) {

                }
            }
        }
        reader.close();
    }

    public void downloadData2(int year, int month, int day) throws IOException {

        URL url = new URL("ftp://client_climate@ftp.tor.ec.gc.ca/Pub/Get_More_Data_Plus_de_donnees/Station Inventory EN.csv");

        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\"", "");
            appendToFile("stations.csv", line);
            String parts[] = line.split(",");
            if (parts.length > 2 && !parts[0].equalsIgnoreCase("name")) {
                try {

                    int lastyear = (parts[12] != null) ? (Integer.parseInt(parts[12])) : 0;
                    if (lastyear < 2015) {
                        continue;
                    }

                    String province = (parts[1] != null) ? (parts[1]) : "";
                    if (!province.equalsIgnoreCase("ontario")) {
                        continue;
                    }

                    URL windurl = new URL("http://climate.weather.gc.ca/climate_data/bulk_data_e.html?format=csv&stationID=" +
                            parts[3] +
                            "&Year=" + year +
                            "&Month=" + month +
                            "&Day=" + day +
                            "&timeframe=1&submit=Download+Data");

                    URLConnection windconn = windurl.openConnection();
                    InputStream windinputStream = windconn.getInputStream();
                    BufferedReader windreader = new BufferedReader(new InputStreamReader(windinputStream));

                    String windline;
                    while ((windline = windreader.readLine()) != null) {
                        windline = windline.replaceAll("\"", "");
                        String windparts[] = windline.split(",");
                        if (windparts.length > 2) {
                            try {
                                WindRecord windRecord = new WindRecord();
                                windRecord.setYear(Integer.parseInt(windparts[1]));
                                windRecord.setMonth(Integer.parseInt(windparts[2]));
                                windRecord.setDay(Integer.parseInt(windparts[3]));
                                windRecord.setTime(windparts[4]);
                                windRecord.setWindspeed(Integer.parseInt(windparts[13]));
                                windRecord.setLatitude(Float.parseFloat(parts[6]));
                                windRecord.setLongitude(Float.parseFloat(parts[7]));

                                add(windRecord);

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    windreader.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        reader.close();
    }

    private void appendToFile(String s, String line) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(s, true)));
            out.println(line);
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addAll() {

        deleteAll();

        try {

            File allData = new File("./data/adddata.csv");
            if (allData.exists()) {
                allData.delete();
            }

            File folder = new File("./data/");
            for (final File fileEntry : folder.listFiles()) {
                String stationId = fileEntry.getName().replaceFirst("[.][^.]+$", "");
                BufferedReader windreader = new BufferedReader(new FileReader(fileEntry));
                String windline;
                while ((windline = windreader.readLine()) != null) {
                    //windline = windline.replaceAll("\"", "");
                    String windparts[] = windline.split(",");
                    if (windparts.length > 12 && windparts[0].startsWith("2")) {
                        try {
                            // day INTEGER, latitude DOUBLE, longitude DOUBLE, month INTEGER, stationid VARCHAR(255), time VARCHAR(255), windspeed INTEGER, YEAR0 INTEGER,
                            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(allData, true)));
                            out.println(
                                    windparts[3] +
                                            "," + windparts[2] +
                                            "," + windparts[4] +
                                            "," + windparts[13] +
                                            "," + windparts[1] +
                                            ",0,0," + stationId);

                            out.close();
                        } catch (Exception e) {
                        }
                    }
                }
                windreader.close();


            }
            Object[] objparams = new Object[1];
            objparams[0] = allData.getPath();

            SystemDataAccess.getNativeSingleWithParams("CALL SYSCS_UTIL.SYSCS_IMPORT_DATA(NULL, 'WINDRECORD', 'DAY,MONTH,TIME,WINDSPEED,YEAR0,LATITUDE,LONGITUDE,STATIONID', null, '?1', null, null, null,0)", objparams);

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }
}
