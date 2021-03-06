package com.climaware.wind.controller;

import com.climaware.postalcode.model.PostalCodeLocation;
import com.climaware.postalcode.service.PostalCodeLocationService;
import com.climaware.weatherstation.model.WeatherStation;
import com.climaware.weatherstation.service.WeatherStationService;
import com.climaware.wind.model.WindScore;
import com.climaware.wind.service.WindFactService;
import com.climaware.wind.service.WindRecordService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/wind/score/*")
public class WindScoreController extends HttpServlet {

    WindRecordService windRecordService;
    WindFactService windFactService;
    PostalCodeLocationService postalCodeLocationService;
    WeatherStationService weatherStationService;

    @Override
    public void init() throws ServletException {
        super.init();
        windRecordService = new WindRecordService();
        windFactService = new WindFactService();
        postalCodeLocationService = new PostalCodeLocationService();
        weatherStationService = new WeatherStationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PostalCodeLocation> postalCodeLocations = postalCodeLocationService.getRandomPostalCodes(10);
        req.setAttribute("randompostalcodes", postalCodeLocations);
        getServletContext().getRequestDispatcher("/WEB-INF/wind/windscorerequest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //an ADJUSTER can ask if a wind event occurred on a specific date and location
        //an UNDERWRITER can ask if the system "thinks" a location will have a high probability of a wind event in the future
        //in either case, a vague low/medium/high should be returned.
        String action = req.getParameter("action");
        String postalcode = req.getParameter("postalcode");
        String stationid = "0";

        WindScore windScore = null;

        if (action != null && action.equalsIgnoreCase("adj")) {
            String year = req.getParameter("year");
            String month = req.getParameter("month");
            String day = req.getParameter("day");

            windScore = windRecordService.score(year, month, day, postalcode);
        } else if (action != null && action.equalsIgnoreCase("udw")) {

            String distance = req.getParameter("distance");

            //I can't know the PC the user will enter. So I must use the postalcodeservice to look it up.
            PostalCodeLocation postalCodeLocation = postalCodeLocationService.getByPostalCode(postalcode);

            //Then I need to find the nearest weatherstation.
            List<WeatherStation> weatherStation = weatherStationService.getByLatitudeLongitudeDistance(
                    String.valueOf(postalCodeLocation.getLatitude()),
                    String.valueOf(postalCodeLocation.getLongitude()),
                    distance);

            req.setAttribute("longitude", postalCodeLocation.getLongitude());
            req.setAttribute("latitude", postalCodeLocation.getLatitude());

            if (weatherStation.size() > 0) {
                stationid = weatherStation.get(0).getStationid();
            }

            //Then I need to find the weather records that correspond to that weather station.


            windScore = windFactService.score(stationid);
        }
        req.setAttribute("score", windScore);
        req.setAttribute("postalcode", postalcode);
        req.setAttribute("stationid", stationid);

        getServletContext().getRequestDispatcher("/WEB-INF/wind/windscoreresponse.jsp").forward(req, resp);

    }

}
