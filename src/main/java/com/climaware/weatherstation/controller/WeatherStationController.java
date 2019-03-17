package com.climaware.weatherstation.controller;

import com.climaware.postalcode.model.PostalCodeLocation;
import com.climaware.postalcode.service.PostalCodeLocationService;
import com.climaware.weatherstation.model.WeatherStation;
import com.climaware.weatherstation.service.WeatherStationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/weatherstation/*")
public class WeatherStationController extends HttpServlet {

    private WeatherStationService weatherStationService;
    private PostalCodeLocationService postalCodeLocationService;

    @Override
    public void init() throws ServletException {
        super.init();
        weatherStationService = new WeatherStationService();
        postalCodeLocationService = new PostalCodeLocationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stationid = req.getParameter("stationid");
        if (stationid != null) {
            WeatherStation weatherStation = weatherStationService.getByStationId(stationid);
            req.setAttribute("weatherstation", weatherStation);
            getServletContext().getRequestDispatcher("/WEB-INF/weatherstation/weatherstationedit.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/weatherstation/weatherstationlist.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stationid = req.getParameter("stationid");
        weatherStationService.deleteByStationId(stationid);
        getServletContext().getRequestDispatcher("/WEB-INF/weatherstation/weatherstationlist.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String _method = req.getParameter("_method");
        if (_method != null && _method.equalsIgnoreCase("delete")) {
            doDelete(req, resp);
            return;
        }
        String postalcode = req.getParameter("postalcode");
        if (postalcode == null) {
            weatherStationService.addAll();
            getServletContext().getRequestDispatcher("/WEB-INF/weatherstation/weatherstationlist.jsp").forward(req, resp);
            return;
        }

        PostalCodeLocation postalCodeLocation = postalCodeLocationService.getByPostalCode(postalcode);

        List<WeatherStation> weatherStations = weatherStationService.getByLatitudeLongitudeDistance(
                String.valueOf(postalCodeLocation.getLatitude()),
                String.valueOf(postalCodeLocation.getLongitude()),
                "50");

        List<WeatherStation> results = new ArrayList<>();
        for (WeatherStation weatherStation : weatherStations) {
            if (weatherStation.getLastyear() > 2018) {
                weatherStation.setTcid(postalcode);
                results.add(weatherStation);
            }
        }
        req.setAttribute("weatherstations", results);
        getServletContext().getRequestDispatcher("/WEB-INF/weatherstation/weatherstationlist.jsp").forward(req, resp);
    }

}