package me.greatman.iConomy7.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;
import me.greatman.iConomy7.*;

public class DatabaseHandler {

	private static MySQL mysql;
	private static SQLite sqlite;
	public static boolean load(iConomy thePlugin)
	{
		boolean result = false;
		if (Config.databaseType.equalsIgnoreCase("sqlite") || Config.databaseType.equalsIgnoreCase("minidb"))
		{
			sqlite = new SQLite(ILogger.log,ILogger.prefix,"iconomy",thePlugin.getDataFolder().getAbsolutePath());
			if (sqlite != null)
			{
				if (sqlite.query("SELECT * FROM " + Config.databaseTable) == null)
				{
					if(sqlite.query("CREATE TABLE " + Config.databaseTable + " (" +
							"id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," + 
							"username VARCHAR(30)  UNIQUE NOT NULL, " +
							"balance DOUBLE DEFAULT '0.00' NOT NULL)") != null);
					{
						ILogger.info("SQLite database created!");
						result = true;
					}
						
				}
				else
				{
					ILogger.info("SQLite database loaded!");
					result = true;
				}
				
			}
			
		}
		else if (Config.databaseType.equalsIgnoreCase("mysql"))
		{
			mysql = new MySQL(ILogger.log,ILogger.prefix,Config.databaseAddress,Config.databasePort,Config.databaseDb,Config.databaseUsername,Config.databasePassword);
			if (mysql != null)
			{
				if (!mysql.checkTable(Config.databaseTable))
				{
					if (mysql.createTable("CREATE TABLE `" + Config.databaseTable + "` (" +
							"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
							"`username` VARCHAR( 30 ) NOT NULL ," +
							"`balance` DOUBLE NOT NULL " +
							") ENGINE = InnoDB;"));
						result = true;
				}
				else
					result = true;
			}
		}
		return result;
	}
	
	public static double getAccountAmount(String account)
	{
		ResultSet result;
		double balance = 0.00;
		String query = "SELECT balance FROM " + Config.databaseTable + " WHERE username='" + account + "'";
		if (sqlite != null)
		{
			result = sqlite.query(query);
			try {
				if (result.next())
				{
						balance = result.getDouble("balance");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			result = mysql.query(query);
			try {
				if (result.next())
				{
						balance = result.getDouble("balance");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return balance;
	}

	public static boolean exists(String account) 
	{
		ResultSet result;
		boolean exists = false;
		String query = "SELECT * FROM " + Config.databaseTable + " WHERE username='" + account + "'";
		if (sqlite != null)
		{
			result = sqlite.query(query);
				try {
					if (result.next())
						exists = true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else
		{
			result = mysql.query(query);
			try {
				if (result.next())
					exists = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return exists;
	}
	
	public static void create(String account)
	{
		String query = "INSERT INTO " + Config.databaseTable + "(username,balance) VALUES('" + account +"'," + Config.defaultHoldings + ")";
		if (sqlite != null)
		{
			sqlite.query(query);
		}	
		else
		{
			mysql.query(query);
		}
			
	}
	
	public static boolean saveAccount(String account, double balance)
	{
		ResultSet result;
		boolean status = false;
		String query = "UPDATE " + Config.databaseTable + " SET balance=" + balance + " WHERE username='" + account + "'";
		if (sqlite != null)
		{
			//sqlite.query(query);
			status = true;
					
		}
		else
		{
			result = mysql.query(query);
			try {
				if (result.rowUpdated())
					status = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}
}
