package me.greatman.iConomy7.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.PatPeter.SQLibrary.MySQL;
import lib.PatPeter.SQLibrary.SQLite;
import me.greatman.iConomy7.*;

public class DatabaseHandler {

	private static MySQL mysql;
	private static SQLite sqlite;
	
	public static boolean load()
	{
		boolean result = false;
		if (Config.databaseType.equalsIgnoreCase("sqlite"))
		{
			sqlite = new SQLite(ILogger.log,ILogger.prefix,"database","plugins/" + iConomy.name + "/");
			if (sqlite != null)
			{
				ResultSet queryResult = sqlite.query("CREATE TABLE " + Config.databaseTable + " (" +
						"id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," + 
						"username VARCHAR(30)  UNIQUE NOT NULL, " +
						"balance FLOAT DEFAULT '0.00' UNIQUE NOT NULL)");
				if (queryResult == null)
					result = true;
			}
			
		}
		else if (Config.databaseType.equalsIgnoreCase("mysql"))
		{
			mysql = new MySQL(ILogger.log,ILogger.prefix,Config.databaseAddress,Config.databasePort,Config.databaseDb,Config.databaseUsername,Config.databasePassword);
			if (mysql != null)
			{
				if (mysql.createTable("CREATE TABLE `" + Config.databaseTable + "` (" +
						"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
						"`username` VARCHAR( 30 ) NOT NULL ," +
						"`balance` DOUBLE NOT NULL " +
						") ENGINE = InnoDB;"));
					result = true;
			}
		}
		return result;
	}
	
	public static double getAccountAmount(String account)
	{
		ResultSet result;
		if (sqlite != null)
		{
			result = sqlite.query("SELECT balance FROM " + Config.databaseTable + " WHERE username='" + account + "'; ");
			try {
				if (result.next())
				{
						return result.getDouble("balance");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			result = mysql.query("SELECT balance FROM " + Config.databaseTable + " WHERE username='" + account + "'; ");
		try {
			if (result.next())
			{
					return result.getDouble("balance");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.00;
	}
}
