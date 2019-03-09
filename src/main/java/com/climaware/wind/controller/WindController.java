package com.climaware.wind.controller;


import com.climaware.wind.model.WindRecord;
import com.climaware.wind.service.WindRecordService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/wind/*")
public class WindController extends HttpServlet {

    private WindRecordService windRecordService;

    @Override
    public void init() throws ServletException {
        super.init();
        windRecordService = new WindRecordService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        List<WindRecord> windRecords = new ArrayList<>();

        if (id == null) {
            windRecords = windRecordService.getAll();
        } else {
            WindRecord windRecord = windRecordService.get(Long.valueOf(id));
            windRecords.add(windRecord);
        }

        req.setAttribute("windrecords", windRecords);
        getServletContext().getRequestDispatcher("/wind/windrecordlist.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");
        if (latitude != "" && longitude != "") {

            WindRecord windRecord = new WindRecord();
            windRecord.setLatitude(Float.parseFloat(req.getParameter("latitude")));
            windRecord.setLongitude(Float.parseFloat(req.getParameter("longitude")));
            windRecord.setPostalcode(req.getParameter("postalcode"));
            windRecord.setWindspeed(Integer.parseInt(req.getParameter("windspeed")));
            windRecord.setYear(Integer.parseInt(req.getParameter("year")));
            windRecord.setMonth(Integer.parseInt(req.getParameter("month")));
            windRecord.setDay(Integer.parseInt(req.getParameter("day")));
            windRecord.setTime(req.getParameter("time"));

            windRecordService.add(windRecord);
        } else {
            windRecordService.downloadData(
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        }


        resp.sendRedirect(req.getContextPath() + "/wind/");
    }

}
