package me.greatman.Craftconomy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.greatman.Craftconomy.utils.Config;
import me.greatman.Craftconomy.utils.DatabaseHandler;

import org.bukkit.World;

/**
 * Bank account manager. Please use BankHandler to access this class
 * 
 * @author greatman
 * @see me.greatman.Craftconomy.BankHandler
 */
public class Bank
{

	private String bankName;
	private String owner;
	private int id;

	public Bank(String theBankName)
	{
		bankName = theBankName;
		owner = DatabaseHandler.getBankOwner(bankName);
		id = DatabaseHandler.getBankId(bankName);
	}

	/**
	 * Get the default balance.
	 * 
	 * @return the balance
	 */
	public double getDefaultBalance()
	{
		return getBalance(Craftconomy.plugin.getServer().getWorlds().get(0));
	}

	/**
	 * Get the default balance in the specific world
	 * 
	 * @param world The world we want to get the balance
	 * @return The account balance
	 */
	public double getBalance(World world)
	{
		return getBalance(CurrencyHandler.getCurrency(Config.currencyDefault, true), world);
	}

	/**
	 * Get a specific player currency in a specific world
	 * 
	 * @param currency The currency we want to get
	 * @param world The world we want to get
	 * @return The account balance
	 */
	public double getBalance(Currency currency, World world)
	{
		if (!Config.multiWorld)
			return DatabaseHandler.getBankBalanceCurrency(this, Craftconomy.plugin.getServer().getWorlds().get(0),
					currency);
		return DatabaseHandler.getBankBalanceCurrency(this, world, currency);
	}

	/**
	 * Get all the bank balance.
	 * 
	 * @return The balance
	 */
	public List<BalanceCollection> getBalance()
	{
		ResultSet result;
		List<BalanceCollection> list = new ArrayList<BalanceCollection>();
		result = DatabaseHandler.getAllBankBalance(this);
		if (result != null)
		{
			try
			{
				while (result.next())
				{
					list.add(new BalanceCollection(result.getString("worldName"), result.getDouble("balance"),
							CurrencyHandler.getCurrency(result.getString("name"), true)));
				}

			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			return list;
		}
		return null;
	}

	/**
	 * Add money in the bank account (Default currency, default world)
	 * 
	 * @param amount the amount of money we want to add
	 * @return the new balance
	 */
	public double addMoney(double amount)
	{
		return addMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), Craftconomy.plugin
				.getServer().getWorlds().get(0));
	}

	/**
	 * Add money in a specific world and currency account
	 * 
	 * @param amount The amount we want to add
	 * @param currency The currency we want to add money in
	 * @param world The world the currency is
	 * @return The new balance
	 */
	public double addMoney(double amount, Currency currency, World world)
	{
		double balance = getBalance(currency, world);
		balance += amount;
		if (Config.multiWorld)
			DatabaseHandler.updateBankAccount(this, balance, currency, world);
		else DatabaseHandler.updateBankAccount(this, balance, currency,
				Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}

	/**
	 * Remove money from a bank account
	 * 
	 * @param amount the amount we want to remove
	 * @return The new balance
	 */
	public double substractMoney(double amount)
	{
		return substractMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), Craftconomy.plugin
				.getServer().getWorlds().get(0));
	}

	/**
	 * Remove money from a bank account
	 * 
	 * @param amount The amount we want to remove from the account
	 * @param currency The currency we want to modify
	 * @param world The world we want to modify the money
	 * @return the new balance
	 */
	public double substractMoney(double amount, Currency currency, World world)
	{
		double balance = getBalance(currency, world);
		balance -= amount;
		if (Config.multiWorld)
			DatabaseHandler.updateBankAccount(this, balance, currency, world);
		else DatabaseHandler.updateBankAccount(this, balance, currency,
				Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}

	/**
	 * Multiply money in the bank account
	 * 
	 * @param amount The amount we want to multiply
	 * @param currency The currency we want to fetch
	 * @param world The world from where we fetch the currency
	 * @return The new balance
	 */
	public double multiplyMoney(double amount, Currency currency, World world)
	{
		double balance = getBalance(currency, world);
		balance *= amount;
		if (Config.multiWorld)
			DatabaseHandler.updateBankAccount(this, balance, currency, world);
		else DatabaseHandler.updateBankAccount(this, balance, currency,
				Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}

	/**
	 * Divide money in the bank account
	 * 
	 * @param amount The amount we want to divide
	 * @param currency The currency we want to fetch
	 * @param world The world from where we fetch the currency
	 * @return The balance after the money is divided
	 */
	public double divideMoney(double amount, Currency currency, World world)
	{
		double balance = getBalance(currency, world);
		balance /= amount;
		if (Config.multiWorld)
			DatabaseHandler.updateBankAccount(this, balance, currency, world);
		else DatabaseHandler.updateBankAccount(this, balance, currency,
				Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}

	/**
	 * Set the player account to the balance received
	 * 
	 * @param amount The balance we want to set the account to
	 * @param currency The currency we want to change
	 * @param world The world we want to change
	 * @return The balance after the change.
	 */
	public double setBalance(double amount, Currency currency, World world)
	{
		double balance = amount;
		if (Config.multiWorld)
			DatabaseHandler.updateBankAccount(this, balance, currency, world);
		else DatabaseHandler.updateBankAccount(this, balance, currency,
				Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}

	/**
	 * Checks if the bank account has enough money
	 * 
	 * @param amount The amount we want to check
	 * @return True if the bank account has enough else false
	 */
	public boolean hasEnough(double amount)
	{
		return hasEnough(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), Craftconomy.plugin
				.getServer().getWorlds().get(0));
	}

	/**
	 * Checks if the bank account has enough money
	 * 
	 * @param amount The amount we want to check
	 * @param currency The currency we want to check
	 * @param world The world we want to check
	 * @return True if the bank account has enough else false
	 */
	public boolean hasEnough(double amount, Currency currency, World world)
	{
		double balance;
		if (Config.multiWorld)
			balance = getBalance(currency, world);
		else balance = getBalance(currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		boolean result = false;
		if (balance >= amount)
			result = true;
		return result;
	}

	/**
	 * Get the bank name
	 * 
	 * @return The bank name
	 */
	public String getName()
	{
		return bankName;
	}

	/**
	 * Get the bank database ID
	 * 
	 * @return the database ID
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Get the owner of the bank account
	 * 
	 * @return The owner of the bank account
	 */
	public String getOwner()
	{
		return owner;
	}

	/**
	 * Get the members of the bank account
	 * 
	 * @return the members of the bank account
	 */
	public List<String> getMembers()
	{
		return DatabaseHandler.getBankMembers(this);
	}

	/**
	 * Add a player in the bank account
	 * 
	 * @param playerName The player name
	 */
	public void addMember(String playerName)
	{
		DatabaseHandler.addBankMember(this, playerName);
	}

	/**
	 * Remove a player from a bank account
	 * 
	 * @param playerName The player name
	 */
	public void removeMember(String playerName)
	{
		DatabaseHandler.removeBankMember(this, playerName);
	}

}
