package com.climaware.wind.controller;

import com.climaware.wind.model.WindDashboard;
import com.climaware.wind.service.WindDashboardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/wind/dashboard/*")
public class WindDashboardController extends HttpServlet {

    WindDashboardService windDashboardService;

    @Override
    public void init() throws ServletException {
        super.init();
        windDashboardService = new WindDashboardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WindDashboard windDashboard = windDashboardService.getDashboard();
        req.setAttribute("dashboard", windDashboard);
        getServletContext().getRequestDispatcher("/WEB-INF/wind/winddashboardlist.jsp").forward(req, resp);
    }

}
