package jp.enpit.cloud.ritesavre.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserAccessLog {
	String user;
	List<Date> accessDateList = new ArrayList<Date>();
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public List<Date> getAccessDateList() {
		return accessDateList;
	}
	public void setAccessDateList(List<Date> accessDateList) {
		this.accessDateList = accessDateList;
	}

}
