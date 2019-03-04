package com.climaware.controller;


import com.climaware.model.WeatherStation;
import com.climaware.service.WeatherStationService;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greg on 3/3/19.
 */
@WebServlet(value = "/administration/weatherstations/*")
public class WeatherStationAdministrationController extends HttpServlet {

    private WeatherStationService weatherStationService;

    @Override
    public void init() throws ServletException {
        super.init();
        weatherStationService = new WeatherStationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        List<WeatherStation> weatherStations = new ArrayList<>();

        if (id == null) {
            weatherStations = weatherStationService.getAll();
        } else {
            WeatherStation weatherStation = weatherStationService.get(Long.valueOf(id));
            weatherStations.add(weatherStation);
        }

        req.setAttribute("weatherstations", weatherStations);
        getServletContext().getRequestDispatcher("/weatherstationlist.jsp").forward(req, resp);
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
                    WeatherStation weatherStation = new WeatherStation();
                    weatherStation.setName(parts[0]);
                    weatherStation.setProvince(parts[1]);
                    weatherStation.setClimateid(parts[2]);
                    weatherStation.setStationid(parts[3]);
                    weatherStation.setWmoid(parts[4]);
                    weatherStation.setTcid(parts[5]);
                    weatherStation.setLatitude(Float.valueOf(parts[6]));
                    weatherStation.setLongitude(Float.valueOf(parts[7]));
                    weatherStation.setElevation(Float.valueOf(parts[10]));
                    weatherStation.setFirstyear(Integer.valueOf(parts[11]));
                    weatherStation.setLastyear(Integer.valueOf(parts[12]));
                    if (parts[13].equalsIgnoreCase(""))
                        weatherStation.setHourlyfirstyear(1900);
                    else
                        weatherStation.setHourlyfirstyear(Integer.valueOf(parts[13]));

                    if (parts[14].equalsIgnoreCase(""))
                        weatherStation.setHourlylastyear(1900);
                    else
                        weatherStation.setHourlylastyear(Integer.valueOf(parts[14]));

                    if (parts[15].equalsIgnoreCase(""))
                        weatherStation.setDailyfirstyear(1900);
                    else
                        weatherStation.setDailyfirstyear(Integer.valueOf(parts[15]));

                    if (parts[16].equalsIgnoreCase(""))
                        weatherStation.setDailylastyear(1900);
                    else
                        weatherStation.setDailylastyear(Integer.valueOf(parts[16]));

                    if (parts[17].equalsIgnoreCase(""))
                        weatherStation.setMonthlyfirstyear(1900);
                    else
                        weatherStation.setMonthlyfirstyear(Integer.valueOf(parts[17]));

                    if (parts[18].equalsIgnoreCase(""))
                        weatherStation.setMonthlylastyear(1900);
                    else
                        weatherStation.setMonthlylastyear(Integer.valueOf(parts[18]));

                    weatherStationService.add(weatherStation);

                    //for each weatherstation, go its weather data (?)
                    //url: http://climate.weather.gc.ca/climate_data/bulk_data_e.html?format=csv&stationID=1706&Year=2004&Month=6&Day=14&timeframe=1&submit=Download+Data
                    // produces a csv with columns:
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
