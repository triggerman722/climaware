package com.climaware.administration.postalcode.controller;


import com.climaware.postalcode.model.PostalCodeLocation;
import com.climaware.postalcode.service.PostalCodeLocationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/administration/postalcode/*")
public class PostalCodeLocationAdministrationController extends HttpServlet {

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

        int ideleted = postalCodeLocationService.deleteAll();

        System.out.println("Just deleted " + ideleted + " records.");

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("postalcodes/postalcodes.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            String parts[] = line.split(",");

            try {

                if (parts[1] == null | parts[1] == "" | Double.valueOf(parts[1]) == 0)
                    continue;

                if (parts[2] == null | parts[2] == "" | Double.valueOf(parts[2]) == 0)
                    continue;

                PostalCodeLocation postalCodeLocation = new PostalCodeLocation();
                postalCodeLocation.setPostalcode(parts[0]);
                postalCodeLocation.setLatitude(Double.valueOf(parts[1]));
                postalCodeLocation.setLongitude(Double.valueOf(parts[2]));

                postalCodeLocationService.add(postalCodeLocation);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        resp.sendRedirect(req.getContextPath() + "/administration/postalcode/");
    }

}
