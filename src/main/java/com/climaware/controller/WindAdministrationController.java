package com.climaware.controller;


import com.climaware.model.WeatherStation;

import com.climaware.service.WeatherStationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/administration/wind/*")
public class WindAdministrationController extends HttpServlet {

    private WeatherStationService weatherStationService;

    @Override
    public void init() throws ServletException {
        super.init();
        weatherStationService = new WeatherStationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        List<WeatherStation> weatherStations = new ArrayList<>();

        if (id == null) {
            weatherStations = weatherStationService.getAll();
        } else {
            WeatherStation weatherStation = weatherStationService.get(Long.valueOf(id));
            weatherStations.add(weatherStation);
        }

        req.setAttribute("weatherstations", weatherStations);
        getServletContext().getRequestDispatcher("/weatherstationlist.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<WeatherStation> weatherStations = weatherStationService.getAll();
        for (WeatherStation weatherStation : weatherStations) {

            URL url = new URL("http://climate.weather.gc.ca/climate_data/bulk_data_e.html?format=csv&stationID="+weatherStation.getStationid()+"&Year="+Calendar.getInstance().get(Calendar.YEAR)+"&Month="+Calendar.getInstance().get(Calendar.MONTH)+"&Day="+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"&timeframe=1&submit=Download+Data");

            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // write the output to stdout
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\"", "");
                String parts[] = line.split(",");
                if (parts.length > 2 && !parts[0].equalsIgnoreCase("name")) {
                    try {
                        System.out.println(parts[1]);//year
                        System.out.println(parts[2]);//month
                        System.out.println(parts[3]);//day
                        System.out.println(parts[4]);//time
                        System.out.println(parts[13]);//windspeed
                        //works? Now to make the object and save them

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/awards");
    }

}
