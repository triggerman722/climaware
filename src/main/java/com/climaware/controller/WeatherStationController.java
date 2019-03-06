package com.climaware.controller;

import com.climaware.model.Award;
import com.climaware.model.WeatherStation;
import com.climaware.service.AwardService;
import com.climaware.service.WeatherStationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/weatherstation/*")
public class WeatherStationController extends HttpServlet {

    private WeatherStationService weatherStationService;

    @Override
    public void init() throws ServletException {
        super.init();
        weatherStationService = new WeatherStationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //I think this is just a form to make a request
        getServletContext().getRequestDispatcher("/weatherstationrequest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");
        String distance = req.getParameter("distance");

        List<WeatherStation> weatherStations = weatherStationService.getByLatitudeLongitudeDistance(latitude,longitude, distance);
        req.setAttribute("weatherstations", weatherStations);
        getServletContext().getRequestDispatcher("/weatherstationlist.jsp").forward(req, resp);
    }

}
