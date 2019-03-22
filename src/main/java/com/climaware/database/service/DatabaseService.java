package com.climaware.database.service;

import com.climaware.persistence.SystemDataAccess;
import com.climaware.wind.service.WindRecordService;

import java.io.File;

/**
 * Created by greg on 3/15/19.
 */
public class DatabaseService {
    public int performBackup(String datetime) {

        String backuplocation = "./backup/" + datetime;
        File f = new File(backuplocation);
        f.mkdir();

        Object[] objparams = new Object[1];

        objparams[0] = backuplocation;

        return SystemDataAccess.getNativeSingleWithParams("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?1)", objparams);

    }
    public int performReload() {
        WindRecordService windRecordService = new WindRecordService();
//        windRecordService.deleteAll();

        System.out.println("truncating...");
        SystemDataAccess.getNativeSingle("TRUNCATE TABLE WINDRECORD");

        System.out.println("truncating...done");
        Object[] objparams = new Object[1];
        objparams[0] = "./data/adddata.csv";

        return SystemDataAccess.getNativeSingleWithParams("CALL SYSCS_UTIL.SYSCS_IMPORT_DATA(NULL, 'WINDRECORD', 'DAY,MONTH,TIME,WINDSPEED,YEAR0,LATITUDE,LONGITUDE,STATIONID', null, ?1, null, null, null,0)", objparams);

    }
    public int performReloadPostalcodes() {

        Object[] objparams = new Object[1];
        objparams[0] = "./data/postalcodes.csv";

        return SystemDataAccess.getNativeSingleWithParams("CALL SYSCS_UTIL.SYSCS_IMPORT_DATA(NULL, 'POSTALCODELOCATION', 'POSTALCODE,LATITUDE,LONGITUDE', null, ?1, null, null, null,0)", objparams);

    }
    public int performReloadWeatherstations() {

        Object[] objparams = new Object[1];
        objparams[0] = "./data/stations.csv";

        return SystemDataAccess.getNativeSingleWithParams("CALL SYSCS_UTIL.SYSCS_IMPORT_DATA(NULL, 'WEATHERSTATION', 'NAME,PROVINCE,CLIMATEID,STATIONID,TCID,WMOID,LATITUDE,LONGITUDE,ELEVATION,FIRSTYEAR,LASTYEAR,HOURLYFIRSTYEAR,HOURLYLASTYEAR,DAILYFIRSTYEAR,DAILYLASTYEAR,MONTHLYFIRSTYEAR,MONTHLYLASTYEAR', '1,2,3,4,5,6,7,8,11,12,13,14,15,16,17,18,19', ?1, null, null, null,0)", objparams);

    }
}
