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
					ResultSet queryResult = SQLite.query("SELECT * FROM " + Config.databaseTable, true);
					if (queryResult != null)
					{
						ILogger.info("SQLite database loaded!");
						result = true;
					}
					else
					{
						if(SQLite.query("CREATE TABLE " + Config.databaseTable + " (" +
								"id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," + 
								"username VARCHAR(30)  UNIQUE NOT NULL, " +
								"balance DOUBLE DEFAULT '0.00' NOT NULL)", false) != null)
						{
							ILogger.info("SQLite database created!");
							result = true;
						}
					}
				}
				catch (java.sql.SQLException e)
				{
						// TODO Auto-generated catch block
						e.printStackTrace();
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
		ResultSet result = null;
		double balance = 0.00;
		ILogger.info(account);
		String query = "SELECT balance FROM " + Config.databaseTable + " WHERE username='" + account + "'";
		if (type == databaseType.SQLITE)
		{
			ILogger.info("Wow");
			try {
				result = SQLite.query(query, true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		ResultSet result = null;
		boolean exists = false;
		String query = "SELECT * FROM " + Config.databaseTable + " WHERE username='" + account + "'";
		if (type == databaseType.SQLITE)
		{
			try {
				result = SQLite.query(query,true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
			try {
				SQLite.query(query,false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			try {
				SQLite.query(query,false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public static void deleteAll() {
		String query = "DELETE FROM " + Config.databaseTable;
		if (type == databaseType.SQLITE)
		{
			try {
				SQLite.query(query, false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			mysql.query(query);
		}
		
	}

	public static void deleteAllInitialAccounts() {
		String query = "DELETE FROM " + Config.databaseTable + " WHERE balance=" + Config.defaultHoldings;
		if (type == databaseType.SQLITE)
		{
			try {
				SQLite.query(query, false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			mysql.query(query);
		}
	}

	public static ResultSet getAllInitialAccounts() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM " + Config.databaseTable + " WHERE balance=" + Config.defaultHoldings;
		if (type == databaseType.SQLITE)
		{
			try {
				return SQLite.query(query, true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			return mysql.query(query);
		return null;
	}

	public static void delete(String playerName) {
		// TODO Auto-generated method stub
		String query = "DELETE FROM " + Config.databaseTable + " WHERE username=" + playerName;
		if (type == databaseType.SQLITE)
		{
			try {
				SQLite.query(query, false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			mysql.query(query);
	}
}
