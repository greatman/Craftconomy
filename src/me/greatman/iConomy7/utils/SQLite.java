package me.greatman.iConomy7.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import me.greatman.iConomy7.ILogger;
import me.greatman.iConomy7.iConomy;

public class SQLite {
	
	public static Connection createConnection() {
		  Connection conn = null;
		  try {
		    Class.forName("org.sqlite.JDBC");
		    conn = DriverManager.getConnection("jdbc:sqlite:" + iConomy.plugin.getDataFolder().getAbsolutePath() + "database.db");
		  }
		  catch ( Exception e) { 
		    e.printStackTrace();
		  }
		  return conn;
		}

	public static ResultSet query(String query, boolean result)
	{
		ResultSet rs = null;
		Connection conn = createConnection();
		Statement sqlStatement;
		try {
			sqlStatement = conn.createStatement();
			if (result == false)
				sqlStatement.executeUpdate(query);
			else
				rs = sqlStatement.executeQuery(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}
	/*public static void query(String query, List<String> parameters)
	{
		
		//Statement sqlStatement = conn.prepareStatement(query);
		for(int i = 0; parameters.size() > i; i++)
		{
			sqlStatement.
		}
	}*/
}
