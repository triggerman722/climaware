/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.climaware.wind.model;


import java.util.ArrayList;
import java.util.List;

/**
 * @author greg
 */

public class WindDashboard {

    private int maximum;
    private int minimum;
    private int average;
    private long count;

    private List<WindDashboardItem> windDashboardItems = new ArrayList<>();


    public WindDashboard() {

    }


    public WindDashboard(Object[] columns) {
        this.maximum = (columns[0] != null) ? ((int) columns[0]) : 0;
        this.minimum = (columns[1] != null) ? ((int) columns[1]) : 0;
        this.average = (columns[2] != null) ? ((int) columns[2]) : 0;
        this.count = (columns[3] != null) ? ((long) columns[3]) : 0;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getAverage() {
        return average;
    }

    public void setAverage(int average) {
        this.average = average;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<WindDashboardItem> getWindDashboardItems() {
        return windDashboardItems;
    }

    public void setWindDashboardItems(List<WindDashboardItem> windDashboardItems) {
        this.windDashboardItems = windDashboardItems;
    }
}
