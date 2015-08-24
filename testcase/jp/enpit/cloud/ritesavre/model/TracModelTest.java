package jp.enpit.cloud.ritesavre.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.enpit.cloud.ritesavre.util.TracDBUtils;

import org.junit.Test;

public class TracModelTest {

	@Test
	public void testGetMilestoneList() throws SQLException{
		Connection conn = null;
		conn = TracDBUtils.getConnection("trac_legoPrep");
		TracModel trac = new TracModel(conn);
		for(String s:trac.getMilestoneList()){
			System.out.println(s);
		}


	}
	
	@Test
	public void testGetUniqueComponentList() throws SQLException{
		Connection conn = null;
		conn = TracDBUtils.getConnection("trac_EventSpiral");
		TracModel trac = new TracModel(conn);
		for(String s:trac.getUniqueComponentList()){
			System.out.println(s);
		}

	}

	@Test
	public void testGetOwnerList() throws SQLException{
		Connection conn = null;

		conn = TracDBUtils.getConnection("trac_EventSpiral");
		TracModel trac = new TracModel(conn);
		List<String> owners = trac.getOwnerList();
		for(String s : owners){
			System.out.println(s);
		}

	}

	@Test
	public void testGetCurrentTicketsByOwner() throws SQLException{
		Connection conn = null;

		conn = TracDBUtils.getConnection("trac_EventSpiral");
		TracModel trac = new TracModel(conn);

		List<String> owners = trac.getOwnerList();
		List<TicketsByOwner> tboList = new ArrayList<TicketsByOwner>();

		for(String s: owners){
			TicketsByOwner tbo = trac.getCurrentTicketsByOwner(s);
			tboList.add(tbo);
		}



	}
}