package jp.enpit.cloud.ritesavre.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TracDBUtils {

	/*	private static String TracDBUser = "tracuser";
	private static String TracDBPass = "cspiral2013";*/
	private static String TracDBUser = "tracuser";
	private static String TracDBPass = "cspiral";
	private static String driver = "com.mysql.jdbc.Driver";

	public static Connection getConnection(String project) throws SQLException {
		Connection con = null;
		try {
			Class.forName(driver);
			//con = DriverManager.getConnection("jdbc:mysql://localhost/" + project, TracDBUser, TracDBPass);
			//con = DriverManager.getConnection("jdbc:mysql://133.1.236.131:13306/" + project, TracDBUser, TracDBPass);
			con = DriverManager.getConnection("jdbc:mysql://10.1.0.67:33060/" + project, TracDBUser, TracDBPass);
		} catch (ClassNotFoundException e) {
		}
		return con;
	}
}
