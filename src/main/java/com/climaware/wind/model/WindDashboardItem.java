/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.climaware.wind.model;


/**
 * @author greg
 */

public class WindDashboardItem {

    private double latitude;
    private double longitude;
    private long count;
    private int minyear;
    private int maxyear;
    private int minwindspeed;
    private int maxwindspeed;
    private int avgwindspeed;
    private String nearpostalcode = "N/A";

    public WindDashboardItem() {

    }

    public WindDashboardItem(Object[] columns) {
        this.latitude = (columns[0] != null) ? ((double) columns[0]) : 0;
        this.longitude = (columns[1] != null) ? ((double) columns[1]) : 0;
        this.count = (columns[2] != null) ? ((long) columns[2]) : 0;
        this.minyear = (columns[3] != null) ? ((int) columns[3]) : 0;
        this.maxyear = (columns[4] != null) ? ((int) columns[4]) : 0;
        this.minwindspeed = (columns[5] != null) ? ((int) columns[5]) : 0;
        this.maxwindspeed = (columns[6] != null) ? ((int) columns[6]) : 0;
        this.avgwindspeed = (columns[7] != null) ? ((int) columns[7]) : 0;
        this.nearpostalcode = (columns[8] != null) ? ((String) columns[8]) : "";
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getMinyear() {
        return minyear;
    }

    public void setMinyear(int minyear) {
        this.minyear = minyear;
    }

    public int getMaxyear() {
        return maxyear;
    }

    public void setMaxyear(int maxyear) {
        this.maxyear = maxyear;
    }

    public int getMinwindspeed() {
        return minwindspeed;
    }

    public void setMinwindspeed(int minwindspeed) {
        this.minwindspeed = minwindspeed;
    }

    public int getMaxwindspeed() {
        return maxwindspeed;
    }

    public void setMaxwindspeed(int maxwindspeed) {
        this.maxwindspeed = maxwindspeed;
    }

    public int getAvgwindspeed() {
        return avgwindspeed;
    }

    public void setAvgwindspeed(int avgwindspeed) {
        this.avgwindspeed = avgwindspeed;
    }

    public String getNearpostalcode() {
        return nearpostalcode;
    }

    public void setNearpostalcode(String nearpostalcode) {
        this.nearpostalcode = nearpostalcode;
    }
}
