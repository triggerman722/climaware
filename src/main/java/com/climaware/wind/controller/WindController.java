package com.climaware.wind.controller;


import com.climaware.util.ControllerUtil;
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
            int offset = ControllerUtil.getParameterAsInt(req.getParameter("offset"), 0, 0, 9999);
            int pagesize = ControllerUtil.getParameterAsInt(req.getParameter("pagesize"), 10, 0, 9999);
            int backoffset = ControllerUtil.getParameterAsInt(String.valueOf(offset - pagesize), 0, 0, 9999);
            windRecords = windRecordService.getAllPaged(offset, pagesize);
            req.setAttribute("offset", offset + pagesize);
            req.setAttribute("backoffset", backoffset);
            req.setAttribute("pagesize", pagesize);
        } else {
            WindRecord windRecord = windRecordService.get(Long.valueOf(id));
            windRecords.add(windRecord);
        }

        req.setAttribute("windrecords", windRecords);
        getServletContext().getRequestDispatcher("/WEB-INF/wind/windrecordlist.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");
        if (latitude != null && longitude != null) {

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
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            windRecordService.downloadData(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
        }


        resp.sendRedirect(req.getContextPath() + "/wind/");
    }

}
