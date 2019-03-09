package com.climaware.wind.controller;

import com.climaware.wind.model.WindScore;
import com.climaware.wind.service.WindRecordService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/wind/score/*")
public class WindScoreController extends HttpServlet {

    WindRecordService windRecordService;

    @Override
    public void init() throws ServletException {
        super.init();
        windRecordService = new WindRecordService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/wind/windscorerequest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //an ADJUSTER can ask if a wind event occurred on a specific date and location
        //an UNDERWRITER can ask if the system "thinks" a location will have a high probability of a wind event in the future
        //in either case, a vague low/medium/high should be returned.
        String action = req.getParameter("action");

        if (action != null && action.equalsIgnoreCase("adj")) {
            String year = req.getParameter("year");
            String month = req.getParameter("month");
            String day = req.getParameter("day");
            String postalcode = req.getParameter("postalcode");

            WindScore windScore = windRecordService.score(year, month, day, postalcode);

            req.setAttribute("score", windScore);

            getServletContext().getRequestDispatcher("/wind/windscoreresponse.jsp").forward(req, resp);


            //on this date/location, get the wind record
            //evaluate its windspeed and return low/medium/high
        } else if (action != null && action.equalsIgnoreCase("udw")) {
            //for this location, get a count of the low/medium/high wind events
            String postalcode = req.getParameter("postalcode");
            //weight the counts
            //evaluate if the total is low/medium/high and return
        }
    }

}
