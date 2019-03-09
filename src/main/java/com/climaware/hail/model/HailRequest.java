package com.climaware.hail.model;

/**
 * Created by greg on 3/2/19.
 */
public class HailRequest {

    String longitude;
    String latitude;
    String occurrencedate;


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getOccurrencedate() {
        return occurrencedate;
    }

    public void setOccurrencedate(String occurrencedate) {
        this.occurrencedate = occurrencedate;
    }
}
