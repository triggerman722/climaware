package com.climaware.wind.service;

import com.climaware.persistence.SystemDataAccess;
import com.climaware.postalcode.service.PostalCodeLocationService;
import com.climaware.wind.model.WindDashboard;
import com.climaware.wind.model.WindDashboardItem;

import java.util.List;

public class WindDashboardService {

    PostalCodeLocationService postalCodeLocationService;

    public WindDashboardService() {
        postalCodeLocationService = new PostalCodeLocationService();
    }

    public WindDashboard getDashboard() {

        List<Object[]> ppAllCount = SystemDataAccess.getAll("select max(s.windspeed), min(s.windspeed), avg(s.windspeed), count(s.id)" +
                " from WindRecord s");

        WindDashboard windDashboard = new WindDashboard(ppAllCount.get(0));


        List<Object[]> ppAll = SystemDataAccess.getAll("select s.latitude, s.longitude, count(s.id), min(s.year), max(s.year), min(s.windspeed), max(s.windspeed), avg(s.windspeed), s.postalcode " +
                " from WindRecord s " +
                "GROUP BY s.postalcode, s.latitude, s.longitude");

        for (Object[] rawEntry : ppAll) {
            WindDashboardItem windDashboardItem = new WindDashboardItem(rawEntry);
            windDashboard.getWindDashboardItems().add(windDashboardItem);
        }

        return windDashboard;
    }
}
