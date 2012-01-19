package me.greatman.Craftconomy.utils;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.World;

import com.sun.rowset.CachedRowSetImpl;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.Currency;
import me.greatman.Craftconomy.ILogger;

@SuppressWarnings("restriction")
public class DatabaseHandler {

	enum databaseType {
		MYSQL,
		SQLITE;
	}
	public static databaseType type = null;
	/**
	 * Load the DatabaseHandler. Create the tables if needed
	 * @param thePlugin The Craftconomy plugin
	 * @return Success to everything or false.
	 */
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
							"username VARCHAR(30)  UNIQUE NOT NULL)", false);
					ILogger.info(Config.databaseMoneyTable + " table created!");
				} catch (SQLException e) {
					ILogger.error("Unable to create the " + Config.databaseMoneyTable + " table!");
					return false;
				}
			}
			if (!SQLLibrary.checkTable(Config.databaseCurrencyTable))
			{
				try{
					SQLLibrary.query("CREATE TABLE " + Config.databaseCurrencyTable + " (" +
							"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
							"name VARCHAR(30) UNIQUE NOT NULL)", false);
				} catch (SQLException e) {
					ILogger.error("Unable to create the " + Config.databaseCurrencyTable + " table!");
					return false;
				}
				
			}
			if(!SQLLibrary.checkTable(Config.databaseBalanceTable))
			{
				try{
					SQLLibrary.query("CREATE TABLE " + Config.databaseBalanceTable +  " (" +
							"id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
							"username_id INTEGER NOT NULL," +
							"currency_id INTEGER NOT NULL," +
							"worldName VARCHAR(30) NOT NULL," + 
							"balance DOUBLE NOT NULL)", false);
				} catch (SQLException e) {
					ILogger.error("Unable to create the " + Config.databaseBalanceTable + " table!");
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
			ILogger.info("MySQL table loaded!");
			return true;
		}
		return false;
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
		}
		return exists;
	}
	
	public static void create(String account)
	{
		String query = "INSERT INTO " + Config.databaseMoneyTable + "(username) VALUES('" + account +"')";
		try {
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
			query = "DELETE FROM " + Config.databaseBalanceTable;
			SQLLibrary.query(query, false);
			query = "DELETE FROM " + Config.databaseCurrencyTable;
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

	//TODO: Make that it verify if he is a bank owner
	public static void delete(String playerName) 
	{
		String query = "DELETE FROM " + Config.databaseMoneyTable + " WHERE username='" + playerName + "'";
			try {
				int accountId = getAccountId(playerName);
				SQLLibrary.query(query, false);
				if (accountId != 0)
				{
					query = "DELETE FROM " + Config.databaseBalanceTable + " WHERE username_id=" + accountId;
					SQLLibrary.query(query, false);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//TODO: Those 3 next functions looks useless
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
	 * Update an account 
	 */
	
	public static void updateAccount(Account account, double balance, Currency currency, World world)
	{
		String query = "SELECT id FROM " + Config.databaseBalanceTable + " WHERE " +
				"username_id=" + account.getPlayerId() + 
				" AND world='" + world.getName() + 
				" AND currency_id=" + currency.getdatabaseId();
		ResultSet result;
		try {
			result = SQLLibrary.query(query, true);
			if (result != null)
			{
				query = "UPDATE FROM " + Config.databaseBalanceTable + 
						" SET balance=" + balance + 
						" WHERE username_id=" + account.getPlayerId() + 
						" AND world='" + world.getName() + 
						" AND currency_id=" + currency.getdatabaseId();
				SQLLibrary.query(query, false);
			}
			else
			{
				query = "INSERT INTO " + Config.databaseBalanceTable + "(username_id,world,currency_id,balance) VALUES(" +
						account.getPlayerId() + "," +
						"'" + world.getName() + "'," +
						currency.getdatabaseId() + "," +
						balance + ")";
				SQLLibrary.query(query, false);
			}
						
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
	}
	
	
	/*
	 * Balance functions
	 */
	
	/**
	 * Get the default balance (When not MultiWorld)
	 * @param account The account we want to get the default balance from.
	 * @return The requested balance
	 */
	//public static double getDefaultBalance(Account account)
	//{
	//	if (!Config.multiWorld)
	//		return getDefaultBalance(account, Craftconomy.plugin.getServer().getWorlds().get(0));
	//	return 0.00;
	//}
	/**
	 * Get the default balance (When MultiWorld)
	 * @param account The account we want to get the default balance from
	 * @param worldName The world name
	 * @return the requested balance
	 */
	/*
	public static double getDefaultBalance(Account account, World world)
	{
			return getBalanceCurrency(account, world.getName(), CurrencyHandler.getCurrency(Config.currencyDefault, true));
	}*/
	/**
	 * Grab all balance from a account (When not multiWorld)
	 * @param account The account we want to get the balance from
	 * @return The result of the query or null if the system is MultiWorld enabled
	 */
	public static ResultSet getAllBalance(Account account)
	{
		if (!Config.multiWorld)
			return getAllBalance(account,Craftconomy.plugin.getServer().getWorlds().get(0));
		return null;
	}
	
	/**
	 * Grab all balance from a certain world of a account (When MultiWorld)
	 * @param account The account we want to get the balance
	 * @param worldName The world we want to check
	 * @return The result of the query
	 */
	public static ResultSet getAllBalance(Account account, World world) {
		String query = "SELECT balance FROM " + Config.databaseMoneyTable + " LEFT JOIN currency ON " + Config.databaseMoneyTable + ".currency_id = currency.id WHERE username='" +account.getPlayerName()+ "' AND world='" + world.getName() + "'";
		try
		{
			ResultSet result = SQLLibrary.query(query, true);
			if (result != null)
			{
				return result;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get the balance of a account
	 * @param account The account we want to get the balance
	 * @param currency The currency we want to Check
	 * @return The balance
	 */
	public static double getBalanceCurrency(Account account, Currency currency)
	{
		return getBalanceCurrency(account,Craftconomy.plugin.getServer().getWorlds().get(0),currency);
	}
	
	/**
	 * Get the balance of a account
	 * @param account The account we want to get the balance
	 * @param worldName The world that we want to check the balance
	 * @param currency The currency we want to check
	 * @return The balance
	 */
	public static double getBalanceCurrency(Account account, World world, Currency currency)
	{
		int currencyId = getCurrencyId(currency);
		if (currencyId != 0)
		{
			String query = "SELECT balance FROM " + Config.databaseMoneyTable + " WHERE username='" +account.getPlayerName()+ "' AND world='" + world.getName() + "' AND currency_id=" + currencyId;
			ResultSet result;
			try {
				result = SQLLibrary.query(query, true);
				if (result != null)
				{
					result.next();
					return result.getInt("balance");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return 0.00;
	}

	/*
	 * Currency functions
	 */
	
	/**
	 * Get the currency database ID
	 * @param currency The Currency object we want to get the ID 
	 * @return The Currency ID
	 */
	public static int getCurrencyId(Currency currency)
	{
		String query = "SELECT id FROM currency WHERE name='" + currency.getName() + "'";
		ResultSet result;
		try {
			result = SQLLibrary.query(query, true);
			if (result != null)
			{
				result.next();
				return result.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Verify if a currency exists in the database
	 * @param currency The currency name we want to check
	 * @return True if the currency exists, else false
	 */
	public static boolean currencyExist(String currency)
	{
		return currencyExist(currency, false);
	}
	
	/**
	 * Verify if a currency exist in the database
	 * @param currency The currency we want to check
	 * @param exact If we give the exact name or not
	 * @return True if the currency exists, else false.
	 */
	public static boolean currencyExist(String currency, boolean exact)
	{
		String query;
		if (exact)
			query = "SELECT * FROM currency WHERE name='" + currency + "'";
		else
			query = "SELECT * FROM currency WHERE name LIKE '%" + currency + "%'";
		try {
			CachedRowSetImpl result = SQLLibrary.query(query,true);
			if (result != null)
			{
				if (result.size() == 1)
				{
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	//TODO: ???????????????????????
	/**
	 * Get the Currency full name
	 * @param currencyName The currency name we want to get
	 * @param exact If we give the exact name or not
	 * @return The currency name
	 */
	public static String getCurrencyName(String currencyName, boolean exact) {
		String query;
		if (exact)
			query = "SELECT name FROM currency WHERE name='" + currencyName + "'";
		else
			query = "SELECT name FROM currency WHERE name LIKE '%" + currencyName + "%'";
		ResultSet result;
		try {
			result = SQLLibrary.query(query, true);
			if (result != null)
			{
				result.next();
				return result.getString("name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
}
