package jp.enpit.cloud.ritesavre.controller;

import java.util.ArrayList;
import java.util.List;

import jp.enpit.cloud.ritesavre.model.FluentdLogModel;
import jp.enpit.cloud.ritesavre.view.AccessEntity;
import jp.enpit.cloud.ritesavre.view.UserAccessLog;

public class DisplayFluentdLogAnalysisController {

	public List<UserAccessLog> execute(){
		FluentdLogModel flm = new FluentdLogModel();
		List<String> users = flm.getAccessUsers();
		//System.out.println(users);
		
		List<AccessEntity> userAccess = new ArrayList<AccessEntity>();
		List<UserAccessLog> ual = new ArrayList<UserAccessLog>();
		
		for(String s: users){
			ual.add(flm.getEventSpiralAccessCount(s));
		}
		return ual;
	}
	
}
