package jp.enpit.cloud.ritesavre.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import jp.enpit.cloud.ritesavre.view.ComponentInfo;
import jp.enpit.cloud.ritesavre.view.EstimateActualSum;
import jp.enpit.cloud.ritesavre.view.TaskEstimation;
import jp.enpit.cloud.ritesavre.view.TicketType;


public class TracModel {
	private Logger logger;

	private Connection conn;

	public TracModel(Connection conn) {
		logger = Logger.getLogger(getClass().getName());
		this.conn = conn;
	}
	public EstimateActualSum getEstimateTimeSum(String milestone){
		PreparedStatement stmt = null;
		EstimateActualSum eas = new EstimateActualSum();

		ResultSet rs = null;

		try{
			stmt = conn.prepareStatement("select ticket.id, ticket_custom.name, ticket_custom.value " +
					"from ticket,ticket_custom where ticket.milestone = ? and " +
					"ticket.id = ticket_custom.ticket and ticket.resolution= 'fixed' and " +
					"(ticket_custom.name = 'estimatedhours' or ticket_custom.name = 'totalhours');");
			stmt.setString(1, milestone);
			rs = stmt.executeQuery();
			while (rs.next()) {
				if(rs.getString("ticket_custom.name").equals("estimatedhours")){
					eas.setEstimate(eas.getEstimate() + Double.parseDouble(rs.getString("ticket_custom.value")));
					eas.setTicketNum(eas.getTicketNum()+1);
				}else if(rs.getString("ticket_custom.name").equals("totalhours")){
					eas.setActual(eas.getActual() + Double.parseDouble(rs.getString("ticket_custom.value")));
				}
			}
			return eas;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
		return eas;
	}

	public ComponentInfo getComponentInfo(String component) throws SQLException{
		ComponentInfo compoInfo = new ComponentInfo();
		compoInfo.setComponent(component);

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//			stmt = conn.prepareStatement("select type, time from ticket where component = ? ORDER BY time");
			stmt = conn.prepareStatement("select ticket.type, ticket_custom.value " +
					"from ticket,ticket_custom where " +
					"ticket.id = ticket_custom.ticket and ticket.resolution= 'fixed' and " +
					"ticket_custom.name = 'enddate' and ticket.component = ? ORDER BY ticket_custom.value;");
			stmt.setString(1, component);
			rs = stmt.executeQuery();
			List<TicketType> ttList = new ArrayList<TicketType>();
			while (rs.next()) {
				TicketType t = new TicketType();
				String type = rs.getString("ticket.type");
				String date = rs.getString("ticket_custom.value");

				t.setType(type);
				if(date.equals("")){
				}else{
					t.setTime(new Date(Long.parseLong(date)/1000));
				}
				/*				if(type.equals("レビュー")){
				}else if(type.equals("結合テスト")){
					t.setType(type);
					if(date.equals("")){
					}else{
						t.setTime(new Date(Long.parseLong(date)/1000));
					}
				}
				 */				ttList.add(t);
			}
			compoInfo.setTicketTypeList(ttList);
			return compoInfo;
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}

	}

	public List<String> getUniqueComponentList()  throws SQLException {
		List<String> componentList = new ArrayList<String>();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select DISTINCT component from ticket where resolution = 'fixed'");
			while (rs.next()) {
				componentList.add(rs.getString("component"));
			}
			return componentList;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * ticketに含まれる全ownerのリストを取得する
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> getOwnerList() throws SQLException {
		logger.info("TracModel.getOwnerList");
		ArrayList<String> owners = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select DISTINCT owner from ticket");
			while (rs.next()) {
				owners.add(rs.getString("owner"));
			}
			return owners;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 引数で指定されたownerの全チケットを取得し，分類ごとにTicketsByOwnerに格納する
	 * @param owner
	 * @return
	 * @throws SQLException
	 */
	public TicketsByOwner getCurrentTicketsByOwner(String owner) throws SQLException{
		logger.info("TracModel.getCurrentTicketsByOwner");
		TicketsByOwner tbo = new TicketsByOwner();
		tbo.setOwner(owner);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("select id, type, time, component, milestone, summary " +
					"from ticket where owner = ? and resolution = 'fixed'");
			stmt.setString(1, owner);
			rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt("id"));
				System.out.println(rs.getString("type"));
				System.out.println(rs.getLong(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getString(5));
				System.out.println(rs.getString(6));

				Ticket t = new Ticket(rs.getInt("id"),
						rs.getLong("time"),
						rs.getString("component"),
						rs.getString("milestone"),
						rs.getString("summary"));
				String type = rs.getString("type");
				if(type.equals("作成(ソース)")){
					tbo.getCreateSource().add(t);
				}else if(type.equals("作成(単体テスト)")){
					tbo.getCreateUTest().add(t);
				}else if(type.equals("作成(結合テスト)")){
					tbo.getCreateITest().add(t);
				}else if(type.equals("レビュー")){
					tbo.getReview().add(t);
				}else if(type.equals("バグ修正(ソース)")){
					tbo.getDebugSource().add(t);
				}else if(type.equals("バグ修正(単体テスト)")){
					tbo.getDebugUTest().add(t);
				}else if(type.equals("バグ修正(結合テスト)")){
					tbo.getDebugITest().add(t);
				}else if(type.equals("結合テスト")){
					tbo.getiTest().add(t);
				}else if(type.equals("その他")){
					tbo.getOtherTask().add(t);
				}else{
					tbo.getOtherTask().add(t);
				}
			}
			return tbo;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}

	}

	/**
	 * 引数で指定されたownerの全チケットを取得し，分類ごとにTicketsByOwnerに格納する
	 * @param owner
	 * @return
	 * @throws SQLException
	 */
	public TicketsByOwner getCurrentTicketsByOwnerWithTotal(String owner) throws SQLException{
		logger.info("TracModel.getCurrentTicketsByOwner");
		TicketsByOwner tbo = new TicketsByOwner();
		tbo.setOwner(owner);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("select id, type, time, component, milestone, summary, ticket_custom.value " +
					"from ticket,ticket_custom where id = ticket_custom.ticket and owner = ? and resolution = 'fixed' and " +
					"ticket_custom.name = 'totalhours' and milestone != 'preSprint2'");

			/*			stmt = conn.prepareStatement("select ticket.id, ticket_custom.name, ticket_custom.value " +
					"from ticket,ticket_custom where ticket.milestone = ? and " +
					"ticket.id = ticket_custom.ticket and ticket.resolution= 'fixed' and " +
					"(ticket_custom.name = 'estimatedhours' or ticket_custom.name = 'totalhours');");
			 */
			stmt.setString(1, owner);
			rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt("id"));
				System.out.println(rs.getString("type"));
				System.out.println(rs.getLong(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getString(5));
				System.out.println(rs.getString(6));
				System.out.println(Double.parseDouble(rs.getString("ticket_custom.value")));

				Ticket t = new Ticket(rs.getInt("id"),
						rs.getLong("time"),
						rs.getString("component"),
						rs.getString("milestone"),
						rs.getString("summary"),
						Double.parseDouble(rs.getString("ticket_custom.value")));
				String type = rs.getString("type");
				if(type.equals("作成(ソース)")){
					tbo.getCreateSource().add(t);
				}else if(type.equals("作成(単体テスト)")){
					tbo.getCreateUTest().add(t);
				}else if(type.equals("作成(結合テスト)")){
					tbo.getCreateITest().add(t);
				}else if(type.equals("レビュー")){
					tbo.getReview().add(t);
				}else if(type.equals("バグ修正(ソース)")){
					tbo.getDebugSource().add(t);
				}else if(type.equals("バグ修正(単体テスト)")){
					tbo.getDebugUTest().add(t);
				}else if(type.equals("バグ修正(結合テスト)")){
					tbo.getDebugITest().add(t);
				}else if(type.equals("結合テスト")){
					tbo.getiTest().add(t);
				}else if(type.equals("その他")){
					tbo.getOtherTask().add(t);
				}else{
					tbo.getOtherTask().add(t);
				}
			}
			return tbo;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}

	}

	public ArrayList<String> getMilestoneList() throws SQLException {
		logger.info("TracModel.getMilestoneList");
		ArrayList<String> milestones = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select name from milestone");
			while (rs.next()) {
				milestones.add(rs.getString("name"));
			}
			return milestones;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	/**@deprecated
	 * */
	public long getStartTime(String milestone) throws SQLException {
		logger.info("TracModel.getStartTime");
		long start = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("select ticket_change.time from ticket,ticket_change where milestone = ? " +
					"and ticket.id = ticket_change.ticket and field= 'status' and newvalue= 'accepted' order by ticket_change.time asc limit 0,1;");
			stmt.setString(1, milestone);
			rs = stmt.executeQuery();
			if (rs.next()) {
				start = rs.getLong("time");
			}
			return start;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
	}
	//
	public List<TaskEstimation> getTaskEffortAndTotalHours(String milestone) throws SQLException{
		logger.info("TracModel.getInitialTaskEffort");
		PreparedStatement stmt = null;
		List<TaskEstimation> taskList = new ArrayList<TaskEstimation>();

		ResultSet rs = null;

		try{
			stmt = conn.prepareStatement("select ticket.id, ticket_custom.name, ticket_custom.value " +
					"from ticket,ticket_custom where ticket.milestone = ? and " +
					"ticket.id = ticket_custom.ticket and ticket.resolution= 'fixed' and " +
					"(ticket_custom.name = 'estimatedhours' or ticket_custom.name = 'totalhours');");
			stmt.setString(1, milestone);
			rs = stmt.executeQuery();
			while (rs.next()) {
				TaskEstimation task = new TaskEstimation();
				if(rs.getString("ticket_custom.name").equals("estimatedhours")){
					task.setTicketId(rs.getInt("ticket.id"));
					task.setEstimatedhours(Double.parseDouble(rs.getString("ticket_custom.value")));
				}else if(rs.getString("ticket_custom.name").equals("totalhours")){
					task.setTicketId(rs.getInt("ticket.id"));
					task.setTotalhours(Double.parseDouble(rs.getString("ticket_custom.value")));
				}
				taskList.add(task);
			}
			return taskList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}


	}


	/**@deprecated
	 * */
	public int getDefaultInitialTaskEffort(String milestone, long start,  int member) throws SQLException{
		long due =0;
		due = this.getEndTime(milestone);

		//calc default initial effort
		return (int) (member * (due-start)/(60*1000*1000));
	}

	/**@deprecated
	 * */
	public int getInitialTaskEffort(String milestone, long time) throws SQLException{
		logger.info("TracModel.getInitialTaskEffort");
		int effort = 0;
		PreparedStatement stmt = null;

		ResultSet rs = null;

		try{
			stmt = conn.prepareStatement("select SUM(value) from (select DISTINCT ticket.id, ticket_custom.value from ticket LEFT JOIN ticket_change on ticket.id=ticket_change.ticket " +
					"LEFT JOIN ticket_custom on ticket.id=ticket_custom.ticket " +
					"where (milestone=? or (ticket_change.field='milestone' and oldvalue=?)) and ticket.time < ? and (resolution ='fixed' or resolution IS NULL) " +
					"and ticket_custom.name='estimatedhours') as dTicket");
			stmt.setString(1, milestone);
			stmt.setString(2, milestone);
			stmt.setLong(3, time + 10 * 60 * 1000 * 1000);//開始時刻から10分以内に登録されたチケットを初期チケットとして認識する
			rs = stmt.executeQuery();
			if (rs.next()) {
				effort = rs.getInt("SUM(value)");
			}
			return effort;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}


	}

	/**@deprecated
	 * */
	public long getEndTime(String milestone) throws SQLException {
		logger.info("TracModel.getEndTime");
		long end = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("select due from milestone where name = ?");
			stmt.setString(1, milestone);
			rs = stmt.executeQuery();
			if (rs.next()) {
				end = rs.getLong("due");
			}
			return end;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
	}


	/**
	 * @deprecated
	 * @param milestone
	 * @param start 始点時刻
	 * @return
	 * @throws SQLException
	 * tracではmilestoneを完了する際に、未完了のチケットを別のmilestoneに移行することができるが、それが行われると
	 * チケットがどのマイルストーンからどのマイルストーンに移行したかという履歴情報が記録されない
	 */
	public int getRemainedTaskEfforts(String milestone, long start) throws SQLException {
		logger.info("TracModel.getRemainedTaskEffort");
		int effort = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//System.out.println("start:" + start);
		//System.out.println("end:" + end);
		try {
			//stmt = conn.prepareStatement("select SUM(value) from (select DISTINCT ticket.id, ticket_custom.value from ticket LEFT JOIN ticket_change on ticket.id=ticket_change.ticket LEFT JOIN ticket_custom on ticket.id=ticket_custom.ticket where milestone = ? and ticket.time <= ? and ((resolution IS NULL and status != 'closed') or (resolution = 'fixed' and status = 'closed' and ticket_change.field='status' and newvalue='closed' and ticket_change.time > ?)) and ticket_custom.name='estimatedhours') as dTicket");
			/*			stmt = conn.prepareStatement("select SUM(value) from (select DISTINCT ticket.id, ticket_custom.value from ticket " +
			"LEFT JOIN ticket_change on ticket.id=ticket_change.ticket LEFT JOIN ticket_custom on ticket.id=ticket_custom.ticket " +
			"where ((milestone=? and ((resolution IS NULL and status != 'closed') or " +
			"(resolution = 'fixed' and status = 'closed' and ticket_change.field='status' and newvalue='closed' " +
			"and ticket_change.time > ?))) or (ticket_change.time > ? and field='milestone' and oldvalue=? " +
			"and ((resolution = 'fixed' and status = 'closed') or (resolution IS NULL and status != 'closed')))) " +
			"and ticket.time <= ? and ticket_custom.name='estimatedhours') as dTicket");
			 */
			stmt = conn.prepareStatement("select SUM(value) from ticket_custom where name = 'estimatedhours' and ticket IN "
					+ "(select DISTINCT ticket.id from ticket LEFT JOIN ticket_change on ticket.id = ticket_change.ticket "
					+ "LEFT JOIN ticket_custom on ticket.id = ticket_custom.ticket "
					+ "where ((status != 'closed') or ((resolution = 'fixed') and (ticket_custom.name = 'enddate' and ticket_custom.value >= ? ))) "
					+ "and (milestone= ?));");
			//stmt.setLong(2, start);
			//stmt.setLong(1, end - 10 * 60 * 1000 * 1000);
			stmt.setLong(1, start);
			stmt.setString(2, milestone);
			//stmt.setString(3, milestone);
			rs = stmt.executeQuery();
			if (rs.next()) {
				effort = rs.getInt("SUM(value)");
			}
			return effort;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * @deprecated
	 * @param milestone
	 * @param start 始点時刻
	 * @param end 終点時刻
	 * @return
	 * @throws SQLException
	 */
	public int getRemainedTaskEffort(String milestone, long start, long end) throws SQLException {
		logger.info("TracModel.getRemainedTaskEffort");
		int effort = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//System.out.println("start:" + start);
		//System.out.println("end:" + end);
		try {
			//stmt = conn.prepareStatement("select SUM(value) from (select DISTINCT ticket.id, ticket_custom.value from ticket LEFT JOIN ticket_change on ticket.id=ticket_change.ticket LEFT JOIN ticket_custom on ticket.id=ticket_custom.ticket where milestone = ? and ticket.time <= ? and ((resolution IS NULL and status != 'closed') or (resolution = 'fixed' and status = 'closed' and ticket_change.field='status' and newvalue='closed' and ticket_change.time > ?)) and ticket_custom.name='estimatedhours') as dTicket");
			/*			stmt = conn.prepareStatement("select SUM(value) from (select DISTINCT ticket.id, ticket_custom.value from ticket " +
			"LEFT JOIN ticket_change on ticket.id=ticket_change.ticket LEFT JOIN ticket_custom on ticket.id=ticket_custom.ticket " +
			"where ((milestone=? and ((resolution IS NULL and status != 'closed') or " +
			"(resolution = 'fixed' and status = 'closed' and ticket_change.field='status' and newvalue='closed' " +
			"and ticket_change.time > ?))) or (ticket_change.time > ? and field='milestone' and oldvalue=? " +
			"and ((resolution = 'fixed' and status = 'closed') or (resolution IS NULL and status != 'closed')))) " +
			"and ticket.time <= ? and ticket_custom.name='estimatedhours') as dTicket");
			 */
			stmt = conn.prepareStatement("select SUM(value) from ticket_custom where name = 'estimatedhours' and ticket IN "
					+ "(select DISTINCT ticket.id from ticket LEFT JOIN ticket_change on ticket.id = ticket_change.ticket "
					+ "LEFT JOIN ticket_custom on ticket.id = ticket_custom.ticket "
					+ "where ((resolution != 'fixed' and resolution !='invalid' or resolution IS NULL) or (ticket_custom.name = 'enddate' and ticket_custom.value >= ? )) "
					+ "and (milestone= ? or (ticket_change.field='milestone' and oldvalue= ? )));");
			//stmt.setLong(2, start);
			//stmt.setLong(1, end - 10 * 60 * 1000 * 1000);
			stmt.setLong(1, start);
			stmt.setString(2, milestone);
			stmt.setString(3, milestone);
			rs = stmt.executeQuery();
			if (rs.next()) {
				effort = rs.getInt("SUM(value)");
			}
			return effort;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
		}
	}


}
