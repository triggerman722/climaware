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
@WebServlet(value = "/awards/*")
public class AwardController extends HttpServlet {

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
        Award award = new Award();
        award.setCode("3");
        award.setId(null);
        award.setDatecreated(new Date());
        awardService.add(award);

        resp.sendRedirect(req.getContextPath() + "/awards");
    }

}
