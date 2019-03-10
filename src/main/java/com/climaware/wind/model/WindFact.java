/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.climaware.wind.model;


/**
 * @author greg
 */

public class WindFact {

    private int maximum;
    private int minimum;
    private int average;
    private int count;


    public WindFact() {

    }


    public WindFact(Object[] columns) {
        this.maximum = (columns[0] != null) ? ((int) columns[0]) : 0;
        this.minimum = (columns[1] != null) ? ((int) columns[1]) : 0;
        this.average = (columns[2] != null) ? ((int) columns[2]) : 0;
        this.count = (columns[3] != null) ? ((int) columns[3]) : 0;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
