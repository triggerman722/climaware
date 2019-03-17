package com.climaware.postalcode.controller;


import com.climaware.postalcode.model.PostalCodeLocation;
import com.climaware.postalcode.service.PostalCodeLocationService;
import com.climaware.util.ControllerUtil;

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
            int offset = ControllerUtil.getParameterAsInt(req.getParameter("offset"), 0, 0, 9999);
            int pagesize = ControllerUtil.getParameterAsInt(req.getParameter("pagesize"), 10, 0, 9999);
            int backoffset = ControllerUtil.getParameterAsInt(String.valueOf(offset - pagesize), 0, 0, 9999);
            postalCodeLocations = postalCodeLocationService.getAllPaged(offset, pagesize);
            req.setAttribute("offset", offset + pagesize);
            req.setAttribute("backoffset", backoffset);
            req.setAttribute("pagesize", pagesize);
        } else {
            PostalCodeLocation postalCodeLocation = postalCodeLocationService.get(Long.valueOf(id));
            postalCodeLocations.add(postalCodeLocation);
        }

        req.setAttribute("postalcodelocations", postalCodeLocations);
        getServletContext().getRequestDispatcher("/WEB-INF/postalcode/postalcodelocationlist.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String postalcode = req.getParameter("postalcode");
        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");

        if (postalcode != null && latitude != null && longitude != null) {
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
