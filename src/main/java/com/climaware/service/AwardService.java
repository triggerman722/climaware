package com.climaware.service;


import com.climaware.model.Award;
import com.climaware.persistence.SystemDataAccess;

import java.util.Date;
import java.util.List;

public class AwardService  {

	public Award get(long id) {
		return (Award) SystemDataAccess.get(Award.class, id);
	}
    public List<Award> getAll() {
        return SystemDataAccess.getAll("select p from Award p ");
    }

	public void add(Award award) {
		award.setId(null);
		award.setDatecreated(new Date());
		SystemDataAccess.add(award);
	}

	public void delete(long awardid) {
		SystemDataAccess.delete(Award.class, awardid);
	}

	public Award set(long awardid, Award award) {
		if (!doesExist(awardid))
			return null;

		Award awardinternal = get(awardid);
		//Is this user allowed to set this?

		return (Award) SystemDataAccess.set(Award.class, awardid, award);
	}

	private boolean doesExist(long id){
		Object object = get(id);
		if (object!=null)
			return true;
		return false;
	}

}
