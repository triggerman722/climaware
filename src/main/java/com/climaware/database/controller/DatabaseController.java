package com.climaware.database.controller;

import com.climaware.database.service.DatabaseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by greg on 3/15/19.
 */
@WebServlet(value = "/database/*")
public class DatabaseController extends HttpServlet {

    DatabaseService databaseService;

    @Override
    public void init() throws ServletException {
        super.init();
        databaseService = new DatabaseService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/database/databaserequest.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String datetime = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        int numrows = databaseService.performBackup(datetime);
        req.setAttribute("rowsaffected", numrows);
        getServletContext().getRequestDispatcher("/WEB-INF/database/databaserequest.jsp").forward(req, resp);
    }
}
