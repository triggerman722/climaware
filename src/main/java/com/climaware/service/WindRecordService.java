package com.climaware.service;


import com.climaware.model.WeatherStation;
import com.climaware.model.WindRecord;
import com.climaware.persistence.SystemDataAccess;

import java.util.List;

public class WindRecordService {

	public WindRecord get(long id) {
		return (WindRecord) SystemDataAccess.get(WindRecord.class, id);
	}
    public List<WindRecord> getAll() {
        return SystemDataAccess.getAll("select p from WindRecord p ");
    }

	public void add(WindRecord windRecord) {
		windRecord.setId(null);

		SystemDataAccess.add(windRecord);
	}

	public void delete(long id) {
		SystemDataAccess.delete(WindRecord.class, id);
	}

	public WindRecord set(long id, WindRecord windRecord) {
		if (!doesExist(id))
			return null;

		WindRecord windRecordinternal = get(id);
		//Is this user allowed to set this?

		return (WindRecord) SystemDataAccess.set(WindRecord.class, id, windRecord);
	}

	private boolean doesExist(long id){
		Object object = get(id);
		if (object!=null)
			return true;
		return false;
	}

}
