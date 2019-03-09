package com.climaware.postalcode.service;


import com.climaware.persistence.SystemDataAccess;
import com.climaware.postalcode.model.PostalCodeLocation;

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

}
