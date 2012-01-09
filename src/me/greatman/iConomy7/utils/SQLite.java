package me.greatman.iConomy7.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.sun.rowset.CachedRowSetImpl;

import me.greatman.iConomy7.iConomy;

@SuppressWarnings("restriction")
public class SQLite {
	public static CachedRowSetImpl crs;
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
	public static ResultSet query(String query, boolean result) throws SQLException
	{
		return query(query,result,false);
	}
	public static CachedRowSetImpl query(String query, boolean result, boolean state) throws SQLException
	{
		ResultSet rs = null;
		Connection conn = createConnection();
		Statement sqlStatement;
		try {
			sqlStatement = conn.createStatement();
			if (result)
			{
				rs = sqlStatement.executeQuery(query);
				crs = new CachedRowSetImpl();
				crs.populate(rs);
			}
				
			else
				sqlStatement.executeUpdate(query);
			conn.close();
			return crs;
			//return rs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}
}
