package com.climaware.administration.weatherstation.controller;


import com.climaware.wind.service.WindRecordService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/administration/weatherstations/*")
public class WeatherStationAdministrationController extends HttpServlet {

    private WindRecordService windRecordService;

    @Override
    public void init() throws ServletException {
        super.init();
        windRecordService = new WindRecordService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //reach out to 3rd party and gather the wind records.
        //get the stations
        URL url = new URL("ftp://client_climate@ftp.tor.ec.gc.ca/Pub/Get_More_Data_Plus_de_donnees/Station Inventory EN.csv");
        // open the url stream, wrap it an a few "readers"
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        // write the output to stdout
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\"", "");
            String parts[] = line.split(",");
            if (parts.length > 2 && !parts[0].equalsIgnoreCase("name")) {
                try {

                    windRecordService.downloadData(parts[6], parts[7], parts[3],
                            Calendar.getInstance().get(Calendar.YEAR),
                            Calendar.getInstance().get(Calendar.MONTH),
                            Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                    //Date/Time	Year	Month	Day	Time	Temp (°C)	Temp Flag	Dew Point Temp (°C)	Dew Point Temp Flag	Rel Hum (%)	Rel Hum Flag	Wind Dir (10s deg)	Wind Dir Flag	Wind Spd (km/h)	Wind Spd Flag	Visibility (km)	Visibility Flag	Stn Press (kPa)	Stn Press Flag	Hmdx	Hmdx Flag	Wind Chill	Wind Chill Flag	Weather

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        // close our reader
        reader.close();

        resp.sendRedirect(req.getContextPath() + "/awards");
    }

}
