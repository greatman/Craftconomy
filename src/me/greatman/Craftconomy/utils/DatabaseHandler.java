package me.greatman.Craftconomy.utils;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.ILogger;

public class DatabaseHandler {

	enum databaseType {
		MYSQL,
		SQLITE;
	}
	public static databaseType type = null;
	public static boolean load(Craftconomy thePlugin)
	{
		boolean result = false;
		if (Config.databaseType.equalsIgnoreCase("SQLite") || Config.databaseType.equalsIgnoreCase("minidb"))
		{
			type = databaseType.SQLITE;
			SQLLibrary.setUrl("jdbc:sqlite:" + Craftconomy.plugin.getDataFolder().getAbsolutePath() + File.separator + "database.db");
				try{
					ResultSet queryResult = SQLLibrary.query("SELECT * FROM " + Config.databaseMoneyTable, true);
					if (queryResult != null)
						ILogger.info("SQLite database loaded!");
						result = true;
				}
				catch (java.sql.SQLException e)
				{
					try {
						SQLLibrary.query("CREATE TABLE " + Config.databaseMoneyTable + " (" +
								"id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," + 
								"username VARCHAR(30)  UNIQUE NOT NULL, " +
								"balance DOUBLE DEFAULT '0.00' NOT NULL)", false);
						SQLLibrary.query("CREATE TABLE " + Config.databaseBankTable + " (" +
								"id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," + 
								"username_id INTEGER  UNIQUE NOT NULL, " +
								"balance DOUBLE DEFAULT '0.00' NOT NULL)", false);
						ILogger.info("SQLite database created!");
						result = true;
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			
		}
		else if (Config.databaseType.equalsIgnoreCase("mysql"))
		{
			type = databaseType.MYSQL;
			SQLLibrary.setUrl("jdbc:mysql://" + Config.databaseAddress + ":" + Config.databasePort + "/" + Config.databaseDb);
			SQLLibrary.setUsername(Config.databaseUsername);
			SQLLibrary.setPassword(Config.databasePassword);
			if (!SQLLibrary.checkTable(Config.databaseMoneyTable))
			{
					try {
						SQLLibrary.query("CREATE TABLE `" + Config.databaseMoneyTable + "` (" +
								"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
								"`username` VARCHAR( 30 ) NOT NULL ," +
								"`balance` DOUBLE NOT NULL " +
								") ENGINE = InnoDB;",false);
						SQLLibrary.query("CREATE TABLE `" + Config.databaseBankTable + "` (" +
								"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
								"`username_id` INT NOT NULL ," +
								"`balance` DOUBLE NOT NULL " +
								") ENGINE = InnoDB;",false);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ILogger.info("MySQL table created!");
					result = true;
			}
			else
			{
				result = true;
				ILogger.info("MySQL table loaded!");
			}
		}
		return result;
	}
	
	public static double getAccountAmount(String account)
	{
		ResultSet result = null;
		double balance = 0.00;
		String query = "SELECT balance FROM " + Config.databaseMoneyTable + " WHERE username='" + account + "'";
			try {
				result = SQLLibrary.query(query, true);
				if (result.isBeforeFirst())
				{
					result.next();
					balance = result.getDouble("balance");
					
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		return balance;
	}

	public static boolean exists(String account) 
	{
		ResultSet result = null;
		boolean exists = false;
		String query = "SELECT * FROM " + Config.databaseMoneyTable + " WHERE username='" + account + "'";
		try {
			result = SQLLibrary.query(query,true);
			if (result.next())
				exists = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exists;
	}
	
	public static void create(String account)
	{
		String query = "INSERT INTO " + Config.databaseMoneyTable + "(username,balance) VALUES('" + account +"'," + Config.defaultHoldings + ")";
		try {
			SQLLibrary.query(query,false);
			
			query = "INSERT INTO " + Config.databaseBankTable +"(username_id) VALUES('" + getAccountId(account) + "')";
			SQLLibrary.query(query, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void saveAccount(Account moneyAccount)
	{
		String query = "UPDATE " + Config.databaseMoneyTable + " SET balance=" + moneyAccount.getBalance() + " WHERE username='" + moneyAccount.getPlayerName() + "'";
		try {
			SQLLibrary.query(query,false);
			
			query = "UPDATE bank SET balance=" + moneyAccount.getBank().getBalance() + " WHERE username_id=" + moneyAccount.getPlayerId();
			SQLLibrary.query(query,false);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void deleteAll() 
	{
		String query = "DELETE FROM " + Config.databaseMoneyTable;
		try {
			SQLLibrary.query(query, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void deleteAllInitialAccounts() 
	{
		String query = "DELETE FROM " + Config.databaseMoneyTable + " WHERE balance=" + Config.defaultHoldings;
		try {
			SQLLibrary.query(query, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ResultSet getAllInitialAccounts() 
	{
		String query = "SELECT * FROM " + Config.databaseMoneyTable + " WHERE balance=" + Config.defaultHoldings;
		try {
			return SQLLibrary.query(query, true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void delete(String playerName) 
	{
		String query = "DELETE FROM " + Config.databaseMoneyTable + " WHERE username=" + playerName;
			try {
				SQLLibrary.query(query, false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static int getAccountId(String playerName)
	{
		int accountId = 0;
		try {
			ResultSet result = SQLLibrary.query("SELECT id FROM " + Config.databaseMoneyTable + " WHERE username='" + playerName + "'", true);
			if (result != null)
			{
				result.next();
				accountId =  result.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountId;
	}
	
	/*
	 * 
	 * Bank functions
	 */
	public static double getBankAmount(Account moneyAccount)
	{
		ResultSet result = null;
		double balance = 0.00;
		String query = "SELECT balance FROM " + Config.databaseBankTable +" WHERE username_id='" + moneyAccount.getPlayerId() + "'";
			try {
				result = SQLLibrary.query(query, true);
				if (result.isBeforeFirst())
				{
					result.next();
					balance = result.getDouble("balance");
					
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		return balance;
	}
}
