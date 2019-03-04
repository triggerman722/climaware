package com.climaware.service;


import com.climaware.model.Award;
import com.climaware.model.HailRequest;
import com.climaware.persistence.SystemDataAccess;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

@WebServlet(value="/hail/*")
public class HailService extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Gson gson = new Gson();
		HailRequest hailRequest = gson.fromJson(new InputStreamReader(req.getInputStream(), "utf-8"), HailRequest.class);

		//Look up hail based on long/lat/occ date

		//return a low/medium/high result


		resp.sendRedirect(req.getContextPath() + "/awards");
	}

}
