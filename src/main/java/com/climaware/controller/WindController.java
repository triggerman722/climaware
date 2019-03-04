package com.climaware.controller;

import com.climaware.model.Award;
import com.climaware.service.AwardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/wind/*")
public class WindController extends HttpServlet {

    private AwardService awardService;

    @Override
    public void init() throws ServletException {
        super.init();
        awardService = new AwardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("awardid");

        List<Award> awards = new ArrayList<>();

        if (id == null) {
            awards = awardService.getAll();
        } else {
            Award award = awardService.get(Long.valueOf(id));
            awards.add(award);
        }

        req.setAttribute("awards", awards);
        getServletContext().getRequestDispatcher("/awardlist.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //an ADJUSTER can ask if a wind event occurred on a specific date and location
        //an UNDERWRITER can ask if the system "thinks" a location will have a high probability of a wind event in the future
        //in either case, a vague low/medium/high should be returned.
        String action = req.getParameter("action");

        if (action!=null && action.equalsIgnoreCase("adj")) {
            //on this date/location, get the wind record
            //evaluate its windspeed and return low/medium/high
        } else if (action != null && action.equalsIgnoreCase("udw")) {
            //for this location, get a count of the low/medium/high wind events
            //weight the counts
            //evaluate if the total is low/medium/high and return
        }
        Award award = new Award();
        award.setCode("3");
        award.setId(null);
        award.setDatecreated(new Date());
        awardService.add(award);

        resp.sendRedirect(req.getContextPath() + "/awards");
    }

}
