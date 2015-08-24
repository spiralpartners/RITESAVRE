package jp.enpit.cloud.ritesavre.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.enpit.cloud.ritesavre.model.TracModel;
import jp.enpit.cloud.ritesavre.util.TracDBUtils;
import jp.enpit.cloud.ritesavre.view.ComponentInfo;

public class DisplayTeamComponentInfoController {
	
	public List<ComponentInfo> execute(){
		Connection conn = null;
		try {
			conn = TracDBUtils.getConnection("trac_EventSpiral");
			TracModel trac = new TracModel(conn);
			List<String> componentList = trac.getUniqueComponentList();
			List<ComponentInfo> cInfoList = new ArrayList<ComponentInfo>();
			for(String s:componentList){
				cInfoList.add(trac.getComponentInfo(s));
			}
			return cInfoList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		

	}

	public int getComponentNum(){
		Connection conn = null;
		try {
			conn = TracDBUtils.getConnection("trac_EventSpiral");
			TracModel trac = new TracModel(conn);
			List<String> componentList = trac.getUniqueComponentList();
			return componentList.size();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		

	}

}
