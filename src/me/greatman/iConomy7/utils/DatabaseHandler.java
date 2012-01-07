package me.greatman.iConomy7.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.PatPeter.SQLibrary.MySQL;
import me.greatman.iConomy7.*;

public class DatabaseHandler {

	enum databaseType {
		MYSQL,
		SQLITE;
	}
	private static MySQL mysql;
	public static databaseType type = null;
	public static boolean load(iConomy thePlugin)
	{
		boolean result = false;
		if (Config.databaseType.equalsIgnoreCase("SQLite") || Config.databaseType.equalsIgnoreCase("minidb"))
		{
			type = databaseType.SQLITE;
			//SQLite = new Db(thePlugin,thePlugin.getDataFolder().getAbsolutePath() + "database.db")
			//SQLite = new SQLite(ILogger.log,ILogger.prefix,"iconomy",thePlugin.getDataFolder().getAbsolutePath());
			if (type == databaseType.SQLITE)
			{
				try{
					SQLite.query("SELECT * FROM " + Config.databaseTable, true);
					ILogger.info("SQLite database loaded!");
					result = true;
				}
				catch (SQLException e)
				{
					if(SQLite.query("CREATE TABLE " + Config.databaseTable + " (" +
							"id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," + 
							"username VARCHAR(30)  UNIQUE NOT NULL, " +
							"balance DOUBLE DEFAULT '0.00' NOT NULL)", false) != null);
					{
						ILogger.info("SQLite database created!");
						result = true;
					}
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
		ILogger.info(account);
		String query = "SELECT balance FROM " + Config.databaseTable + " WHERE username='" + account + "'";
		if (type == databaseType.SQLITE)
		{
			ILogger.info("Wow");
			result = SQLite.query(query, true);
			try {
				ILogger.info("Wow");
				if (result.next())
				{
					ILogger.info("Wow");
						balance = result.getDouble("balance");
				}
				ILogger.info("" + balance);
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
		if (type == databaseType.SQLITE)
		{
			result = SQLite.query(query,true);
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
		if (type == databaseType.SQLITE)
		{
			SQLite.query(query,false);
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
		if (type == databaseType.SQLITE)
		{
			SQLite.query(query,false);
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
