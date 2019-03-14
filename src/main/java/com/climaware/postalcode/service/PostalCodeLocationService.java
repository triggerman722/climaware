package com.climaware.postalcode.service;


import com.climaware.persistence.SystemDataAccess;
import com.climaware.postalcode.model.PostalCodeLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostalCodeLocationService {

    public PostalCodeLocation get(long id) {
        return (PostalCodeLocation) SystemDataAccess.get(PostalCodeLocation.class, id);
    }

    public List<PostalCodeLocation> getAll() {
        return SystemDataAccess.getAll("select p from PostalCodeLocation p ");
    }

    public List<Long> countAll() {
        return SystemDataAccess.getAll("select COUNT(p.postalcode) from PostalCodeLocation p ");
    }
    public PostalCodeLocation getByPostalCode(String postalcode) {
        String upperpostalcode = postalcode.toUpperCase();

        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "postalcode";
        tvoObject[0][1] = upperpostalcode;
        List<PostalCodeLocation> ppAll = SystemDataAccess.getWithParams("select p from PostalCodeLocation  p where UPPER(p.postalcode) = :postalcode ", tvoObject);
        if (ppAll.size() > 0) {
            return ppAll.get(0);
        } else {
            tvoObject[0][1] = upperpostalcode.substring(0, 3) + "%";
            List<PostalCodeLocation> ppVeryNarrow = SystemDataAccess.getWithParams("select p from PostalCodeLocation  p where UPPER(p.postalcode) like :postalcode ", tvoObject);
            if (ppVeryNarrow.size() > 0) {
                return ppVeryNarrow.get(0);
            } else {
                tvoObject[0][1] = upperpostalcode.substring(0, 2) + "%";
                List<PostalCodeLocation> ppNarrow = SystemDataAccess.getWithParams("select p from PostalCodeLocation  p where UPPER(p.postalcode) like :postalcode ", tvoObject);
                if (ppNarrow.size() > 0) {
                    return ppNarrow.get(0);
                } else {
                    tvoObject[0][1] = upperpostalcode.substring(0, 1) + "%";
                    List<PostalCodeLocation> ppWide = SystemDataAccess.getWithParams("select p from PostalCodeLocation  p where UPPER(p.postalcode) like :postalcode ", tvoObject);
                    if (ppWide.size() > 0) {
                        return ppWide.get(0);
                    }
                }
            }
        }
        return null;
    }

    public List<PostalCodeLocation> getByLatitudeLongitudeDistance(String latitude, String longitude, String distance) {
        Object[] tvoObject = new Object[4];

        tvoObject[0] = latitude;
        tvoObject[1] = latitude;
        tvoObject[2] = longitude;
        tvoObject[3] = distance;

        return SystemDataAccess.getNativeWithParams("SELECT * from PostalCodeLocation s" +
                " WHERE (acos(sin(radians(s.latitude)) * sin(radians(?1)) " +
                "+ cos(radians(s.latitude)) * cos(radians(?2)) * " +
                "cos(radians(s.longitude-(?3)))) * 6371) < ?4" +
                " ORDER BY " +
                "(acos(sin(radians(s.latitude)) * sin(radians(?1)) + " +
                "cos(radians(s.latitude)) * cos(radians(?2)) * " +
                "cos(radians(s.longitude-(?3)))) * 6371) ASC", tvoObject, PostalCodeLocation.class);

    }
    public void add(PostalCodeLocation postalCodeLocation) {
        postalCodeLocation.setId(null);

        SystemDataAccess.add(postalCodeLocation);
    }

    public void delete(long id) {
        SystemDataAccess.delete(PostalCodeLocation.class, id);
    }

    public int deleteAll() {
        return SystemDataAccess.deleteAll("delete from PostalCodeLocation");
    }

    public PostalCodeLocation set(long id, PostalCodeLocation postalCodeLocation) {
        if (!doesExist(id))
            return null;

        PostalCodeLocation postalCodeLocationInternal = get(id);
        //Is this user allowed to set this?

        return (PostalCodeLocation) SystemDataAccess.set(PostalCodeLocation.class, id, postalCodeLocation);
    }

    private boolean doesExist(long id) {
        Object object = get(id);
        return object != null;
    }

    public void reloadAllPostalCodes() throws IOException {

        int ideleted = deleteAll();

        System.out.println("Just deleted " + ideleted + " records.");

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("postalcodes/postalcodes.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        int i = 0;
        while ((line = reader.readLine()) != null) {
            String parts[] = line.split(",");

            try {

                if (parts[1] == null | parts[1] == "" | Double.valueOf(parts[1]) == 0)
                    continue;

                if (parts[2] == null | parts[2] == "" | Double.valueOf(parts[2]) == 0)
                    continue;

                String startwith = parts[0].substring(0, 1).toLowerCase();
                if (!"klmnp".contains(startwith))
                    continue;

                PostalCodeLocation postalCodeLocation = new PostalCodeLocation();
                postalCodeLocation.setPostalcode(parts[0]);
                postalCodeLocation.setLatitude(Double.valueOf(parts[1]));
                postalCodeLocation.setLongitude(Double.valueOf(parts[2]));

                add(postalCodeLocation);
                System.out.println(i++ + ": " + postalCodeLocation.getPostalcode());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<PostalCodeLocation> getRandomPostalCodes(int count) {
        List<PostalCodeLocation> postalCodeLocations = new ArrayList<>();
        long countrecords = 0;
        List<Long> counter = countAll();
        if (counter != null && counter.size() > 0) {
            countrecords = counter.get(0);
        }
        for (int i = 0; i < count; i++) {
            Random random = new Random();
            int number = random.nextInt((int) countrecords);
            PostalCodeLocation postalCodeLocation = get(number);
            if (postalCodeLocation != null)
                postalCodeLocations.add(postalCodeLocation);
        }
        return postalCodeLocations;
    }
}
