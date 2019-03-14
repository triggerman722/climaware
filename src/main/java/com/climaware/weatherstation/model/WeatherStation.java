/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.climaware.weatherstation.model;

import javax.persistence.*;

/**
 * "Name","Province","Climate ID","Station ID","WMO ID","TC ID","Latitude (Decimal Degrees)",
 * "Longitude (Decimal Degrees)","Latitude","Longitude","Elevation (m)","First Year",
 * "Last Year","HLY First Year","HLY Last Year","DLY First Year","DLY Last Year",
 * "MLY First Year","MLY Last Year"
 *
 * @author greg
 */
@Entity
@Table(name = "weatherstation")
public class WeatherStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String province;
    @Column(length = 255)
    private String climateid;
    private String stationid;
    private String wmoid;
    private String tcid;
    private double latitude;
    private double longitude;
    private double elevation;
    private int firstyear;
    private int lastyear;
    private int hourlyfirstyear;
    private int hourlylastyear;
    private int dailyfirstyear;
    private int dailylastyear;
    private int monthlyfirstyear;
    private int monthlylastyear;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getClimateid() {
        return climateid;
    }

    public void setClimateid(String climateid) {
        this.climateid = climateid;
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }

    public String getWmoid() {
        return wmoid;
    }

    public void setWmoid(String wmoid) {
        this.wmoid = wmoid;
    }

    public String getTcid() {
        return tcid;
    }

    public void setTcid(String tcid) {
        this.tcid = tcid;
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

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public int getFirstyear() {
        return firstyear;
    }

    public void setFirstyear(int firstyear) {
        this.firstyear = firstyear;
    }

    public int getLastyear() {
        return lastyear;
    }

    public void setLastyear(int lastyear) {
        this.lastyear = lastyear;
    }

    public int getHourlyfirstyear() {
        return hourlyfirstyear;
    }

    public void setHourlyfirstyear(int hourlyfirstyear) {
        this.hourlyfirstyear = hourlyfirstyear;
    }

    public int getHourlylastyear() {
        return hourlylastyear;
    }

    public void setHourlylastyear(int hourlylastyear) {
        this.hourlylastyear = hourlylastyear;
    }

    public int getDailyfirstyear() {
        return dailyfirstyear;
    }

    public void setDailyfirstyear(int dailyfirstyear) {
        this.dailyfirstyear = dailyfirstyear;
    }

    public int getDailylastyear() {
        return dailylastyear;
    }

    public void setDailylastyear(int dailylastyear) {
        this.dailylastyear = dailylastyear;
    }

    public int getMonthlyfirstyear() {
        return monthlyfirstyear;
    }

    public void setMonthlyfirstyear(int monthlyfirstyear) {
        this.monthlyfirstyear = monthlyfirstyear;
    }

    public int getMonthlylastyear() {
        return monthlylastyear;
    }

    public void setMonthlylastyear(int monthlylastyear) {
        this.monthlylastyear = monthlylastyear;
    }
}