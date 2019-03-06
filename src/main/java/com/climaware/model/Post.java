/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.climaware.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author greg
 */
@Entity
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private Long createdby;

    private float latitude;
    private float longitude;
    private Long radius;

    private boolean requestPhotos;
    private boolean requestVideos;
    private boolean requestAudio;

    private Long votesup;
    private Long votesdown;

    private Long acceptedcommentid;


    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datecreated;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateexpired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Long createdby) {
        this.createdby = createdby;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public Long getRadius() {
        return radius;
    }

    public void setRadius(Long radius) {
        this.radius = radius;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Date getDateexpired() {
        return dateexpired;
    }

    public void setDateexpired(Date dateexpired) {
        this.dateexpired = dateexpired;
    }

    public boolean getRequestPhotos() {
        return requestPhotos;
    }

    public void setRequestPhotos(boolean requestPhotos) {
        this.requestPhotos = requestPhotos;
    }

    public Long getVotesup() {
        return votesup;
    }

    public void setVotesup(Long votesup) {
        this.votesup = votesup;
    }

    public Long getVotesdown() {
        return votesdown;
    }

    public void setVotesdown(Long votesdown) {
        this.votesdown = votesdown;
    }

    public boolean isRequestVideos() {
        return requestVideos;
    }

    public void setRequestVideos(boolean requestVideos) {
        this.requestVideos = requestVideos;
    }

    public boolean isRequestAudio() {
        return requestAudio;
    }

    public void setRequestAudio(boolean requestAudio) {
        this.requestAudio = requestAudio;
    }

    public Long getAcceptedcommentid() {
        return acceptedcommentid;
    }

    public void setAcceptedcommentid(Long acceptedcommentid) {
        this.acceptedcommentid = acceptedcommentid;
    }
}
