/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.climaware.wind.model;

import javax.persistence.*;

/**
 * @author greg
 */
@Entity
@Table(name = "windrecord")
public class WindRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int month;
    private int day;
    private String time;
    private int windspeed;

    private double latitude;
    private double longitude;

    private String postalcode;
    private String postalcodeprefix;
    private String postalcodesuffix;
    private String postalcoderegionnarrow; //In a PC of N6K4W5, this value is N6 (i.e. the first two digits)
    private String postalcoderegionwide; //For N6K4W5, this value is N (i.e. the first digit).

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(int windspeed) {
        this.windspeed = windspeed;
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

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPostalcodeprefix() {
        return postalcodeprefix;
    }

    public void setPostalcodeprefix(String postalcodeprefix) {
        this.postalcodeprefix = postalcodeprefix;
    }

    public String getPostalcodesuffix() {
        return postalcodesuffix;
    }

    public void setPostalcodesuffix(String postalcodesuffix) {
        this.postalcodesuffix = postalcodesuffix;
    }

    public String getPostalcoderegionnarrow() {
        return postalcoderegionnarrow;
    }

    public void setPostalcoderegionnarrow(String postalcoderegionnarrow) {
        this.postalcoderegionnarrow = postalcoderegionnarrow;
    }

    public String getPostalcoderegionwide() {
        return postalcoderegionwide;
    }

    public void setPostalcoderegionwide(String postalcoderegionwide) {
        this.postalcoderegionwide = postalcoderegionwide;
    }
}
