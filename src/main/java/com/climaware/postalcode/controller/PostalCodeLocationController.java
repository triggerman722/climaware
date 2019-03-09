package com.climaware.postalcode.controller;


import com.climaware.postalcode.model.PostalCodeLocation;
import com.climaware.postalcode.service.PostalCodeLocationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/postalcode/*")
public class PostalCodeLocationController extends HttpServlet {

    private PostalCodeLocationService postalCodeLocationService;

    @Override
    public void init() throws ServletException {
        super.init();
        postalCodeLocationService = new PostalCodeLocationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        List<PostalCodeLocation> postalCodeLocations = new ArrayList<>();

        if (id == null) {
            postalCodeLocations = postalCodeLocationService.getAll();
        } else {
            PostalCodeLocation postalCodeLocation = postalCodeLocationService.get(Long.valueOf(id));
            postalCodeLocations.add(postalCodeLocation);
        }

        req.setAttribute("postalcodelocations", postalCodeLocations);
        getServletContext().getRequestDispatcher("/postalcode/postalcodelocationlist.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String postalcode = req.getParameter("postalcode");
        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");

        if (postalcode != "" && latitude != "" && longitude != "") {
            PostalCodeLocation postalCodeLocation = new PostalCodeLocation();
            postalCodeLocation.setPostalcode(postalcode);
            postalCodeLocation.setLatitude(Float.parseFloat(latitude));
            postalCodeLocation.setLongitude(Float.parseFloat(longitude));

            postalCodeLocationService.add(postalCodeLocation);
        } else {
            postalCodeLocationService.reloadAllPostalCodes();
        }

        resp.sendRedirect(req.getContextPath() + "/postalcode/");
    }

}
