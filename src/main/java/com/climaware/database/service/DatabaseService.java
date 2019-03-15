package com.climaware.database.service;

import com.climaware.persistence.SystemDataAccess;

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
}
