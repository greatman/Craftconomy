package me.greatman.Craftconomy.utils;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
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
		if (Config.databaseType.equalsIgnoreCase("SQLite") || Config.databaseType.equalsIgnoreCase("minidb"))
		{
			type = databaseType.SQLITE;
			SQLLibrary.setUrl("jdbc:sqlite:" + Craftconomy.plugin.getDataFolder().getAbsolutePath() + File.separator + "database.db");
			if (!SQLLibrary.checkTable(Config.databaseMoneyTable))
			{
				try {
					SQLLibrary.query("CREATE TABLE " + Config.databaseMoneyTable + " (" +
							"id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," + 
							"username VARCHAR(30)  UNIQUE NOT NULL, " +
							"balance DOUBLE DEFAULT '0.00' NOT NULL)", false);
					ILogger.info(Config.databaseMoneyTable + " table created!");
				} catch (SQLException e) {
					ILogger.error("Unable to create the " + Config.databaseBankTable + " table!");
					return false;
				}
			}
			if (!SQLLibrary.checkTable(Config.databaseBankTable))
			{
				try {
					SQLLibrary.query("CREATE TABLE " + Config.databaseBankTable + " (" +
							"id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," + 
							"username_id INTEGER  UNIQUE NOT NULL, " +
							"balance DOUBLE DEFAULT '0.00' NOT NULL)", false);
					ILogger.info(Config.databaseBankTable + " created!");
				} catch (SQLException e) {
					ILogger.error("Unable to create the " + Config.databaseBankTable + " table!");
					return false;
				}
			}
			if (!SQLLibrary.checkTable(Config.databaseMoneyTable + "_waiting"))
			{
				try {
					SQLLibrary.query("CREATE TABLE " + Config.databaseMoneyTable + "_waiting (" +
							"id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," + 
							"username_id INTEGER NOT NULL, " +
							"balance DOUBLE DEFAULT '0.00' NOT NULL)", false);
					ILogger.info(Config.databaseBankTable + " created!");
				} catch (SQLException e) {
					ILogger.error("Unable to create the " + Config.databaseBankTable + " table!");
					return false;
				}
			}
			if (!SQLLibrary.checkTable(Config.databaseBankTable + "_waiting"))
			{
				try {
					SQLLibrary.query("CREATE TABLE " + Config.databaseBankTable + "_waiting (" +
							"id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," + 
							"username_id INTEGER NOT NULL, " +
							"balance DOUBLE DEFAULT '0.00' NOT NULL)", false);
					ILogger.info(Config.databaseBankTable + " created!");
				} catch (SQLException e) {
					ILogger.error("Unable to create the " + Config.databaseBankTable + " table!");
					return false;
				}
			}
			ILogger.info("SQLite database loaded!");
			return true;
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
						ILogger.info(Config.databaseMoneyTable + " table created!");
					} catch (SQLException e) {
						ILogger.error("Unable to create the " + Config.databaseMoneyTable + " table!");
						return false;
					}
			}
			if (!SQLLibrary.checkTable(Config.databaseBankTable))
			{
				try {
					SQLLibrary.query("CREATE TABLE `" + Config.databaseBankTable + "` (" +
							"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
							"`username_id` INT NOT NULL ," +
							"`balance` DOUBLE NOT NULL " +
							") ENGINE = InnoDB;",false);
					ILogger.info(Config.databaseBankTable + " created!");
				} catch (SQLException e) {
					ILogger.error("Unable to create the " + Config.databaseBankTable + " table!");
					return false;
				}
			}
			if (!SQLLibrary.checkTable(Config.databaseMoneyTable + "_waiting"))
			{
				try {
					SQLLibrary.query("CREATE TABLE `" + Config.databaseMoneyTable + "_waiting" + "` (" +
							"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
							"`username_id` INT NOT NULL ," +
							"`balance` DOUBLE NOT NULL " +
							") ENGINE = InnoDB;",false);
					ILogger.info(Config.databaseBankTable + " created!");
				} catch (SQLException e) {
					ILogger.error("Unable to create the " + Config.databaseBankTable + " table!");
					return false;
				}
			}
			if (!SQLLibrary.checkTable(Config.databaseBankTable + "_waiting"))
			{
				try {
					SQLLibrary.query("CREATE TABLE `" + Config.databaseBankTable + "_waiting` (" +
							"`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
							"`username_id` INT NOT NULL ," +
							"`balance` DOUBLE NOT NULL " +
							") ENGINE = InnoDB;",false);
					ILogger.info(Config.databaseBankTable + " created!");
				} catch (SQLException e) {
					ILogger.error("Unable to create the " + Config.databaseBankTable + " table!");
					return false;
				}
			}
			ILogger.info("MySQL table loaded!");
			return true;
		}
		return false;
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
			
			query = "INSERT INTO " + Config.databaseBankTable +"(username_id,balance) VALUES('" + getAccountId(account) + "', 0.00)";
			SQLLibrary.query(query, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void getWaitingSaveAccount()
	{
		String query = "SELECT * FROM " + Config.databaseMoneyTable + "_waiting";
		ResultSet result;
		try {
			result = SQLLibrary.query(query, true);
			if (result.next())
			{
				do
				{
					Account account = AccountHandler.getAccount(result.getInt("username_id"));
					account.addMoney(result.getDouble("balance"));
				}
				while(result.next());
			}
			SQLLibrary.truncateTable(Config.databaseMoneyTable + "_waiting");
			query = "SELECT * FROM " + Config.databaseBankTable + "_waiting";
			result = SQLLibrary.query(query, true);
			if (result.next())
			{
				do
				{
					Account account = AccountHandler.getAccount(result.getInt("username_id"));
					account.getBank().addMoney(result.getDouble("balance"));
				}
				while(result.next());
			}
			SQLLibrary.truncateTable(Config.databaseBankTable + "_waiting");
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
	
	public static boolean accountIdExists(int id)
	{
		try{
			ResultSet result = SQLLibrary.query("SELECT username FROM " + Config.databaseMoneyTable + " WHERE id=" + id, true);
			if (result != null)
				return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
	}
	public static String getAccountNameById(int id)
	{
		String accountName = null;
		try{
			ResultSet result = SQLLibrary.query("SELECT username FROM " + Config.databaseMoneyTable + " WHERE id=" + id, true);
			if (result != null)
			{
				result.next();
				accountName =  result.getString("username");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		return accountName;
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
