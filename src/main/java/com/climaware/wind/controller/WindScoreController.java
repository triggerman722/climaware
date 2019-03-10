package com.climaware.wind.controller;

import com.climaware.wind.model.WindScore;
import com.climaware.wind.service.WindFactService;
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
    WindFactService windFactService;

    @Override
    public void init() throws ServletException {
        super.init();
        windRecordService = new WindRecordService();
        windFactService = new WindFactService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/wind/windscorerequest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //an ADJUSTER can ask if a wind event occurred on a specific date and location
        //an UNDERWRITER can ask if the system "thinks" a location will have a high probability of a wind event in the future
        //in either case, a vague low/medium/high should be returned.
        String action = req.getParameter("action");
        String postalcode = req.getParameter("postalcode");
        WindScore windScore = null;

        if (action != null && action.equalsIgnoreCase("adj")) {
            String year = req.getParameter("year");
            String month = req.getParameter("month");
            String day = req.getParameter("day");

            windScore = windRecordService.score(year, month, day, postalcode);
        } else if (action != null && action.equalsIgnoreCase("udw")) {
            windScore = windFactService.score(postalcode);
        }
        req.setAttribute("score", windScore);

        getServletContext().getRequestDispatcher("/WEB-INF/wind/windscoreresponse.jsp").forward(req, resp);

    }

}
