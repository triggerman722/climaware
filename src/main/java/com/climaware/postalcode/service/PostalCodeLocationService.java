package com.climaware.postalcode.service;


import com.climaware.persistence.SystemDataAccess;
import com.climaware.postalcode.model.PostalCodeLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class PostalCodeLocationService {

    public PostalCodeLocation get(long id) {
        return (PostalCodeLocation) SystemDataAccess.get(PostalCodeLocation.class, id);
    }

    public List<PostalCodeLocation> getAll() {
        return SystemDataAccess.getAll("select p from PostalCodeLocation p ");
    }

    public PostalCodeLocation getByPostalCode(String postalcode) {
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "postalcode";
        tvoObject[0][1] = postalcode;
        List<PostalCodeLocation> ppAll = SystemDataAccess.getWithParams("select p from PostalCodeLocation  p where p.postalcode in (:postalcode) ", tvoObject);
        if (ppAll.size() > 0) {
            return ppAll.get(0);
        }
        return null;
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
        while ((line = reader.readLine()) != null) {
            String parts[] = line.split(",");

            try {

                if (parts[1] == null | parts[1] == "" | Double.valueOf(parts[1]) == 0)
                    continue;

                if (parts[2] == null | parts[2] == "" | Double.valueOf(parts[2]) == 0)
                    continue;

                PostalCodeLocation postalCodeLocation = new PostalCodeLocation();
                postalCodeLocation.setPostalcode(parts[0]);
                postalCodeLocation.setLatitude(Double.valueOf(parts[1]));
                postalCodeLocation.setLongitude(Double.valueOf(parts[2]));

                add(postalCodeLocation);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
