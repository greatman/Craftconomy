package me.greatman.Craftconomy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.greatman.Craftconomy.utils.Config;
import me.greatman.Craftconomy.utils.DatabaseHandler;

import org.bukkit.World;

public class Bank
{

	String bankName;
	String owner;
	int id;
	public Bank(String theBankName)
	{
		bankName = theBankName;
		owner = DatabaseHandler.getBankOwner(bankName);
		id = DatabaseHandler.getBankId(bankName);
	}

	
	/**
	 * Get the default balance in the specific world
	 * @param world The world we want to get the balance
	 * @return The account balance
	 */
	public double getBalance(World world)
	{
		return getBalance(CurrencyHandler.getCurrency(Config.currencyDefault, true),world);
	}
	/**
	 * Get a specific player currency in a specific world
	 * @param currency The currency we want to get
	 * @param world The world we want to get
	 * @return The account balance
	 */
	public double getBalance(Currency currency,World world)
	{
		if (!Config.multiWorld)
			return DatabaseHandler.getBankBalanceCurrency(this, Craftconomy.plugin.getServer().getWorlds().get(0),currency);
		return DatabaseHandler.getBankBalanceCurrency(this, world, currency);
	}
	
	public List<BalanceCollection> getBalance()
	{
		ResultSet result;
		List<BalanceCollection> list = new ArrayList<BalanceCollection>();
		result = DatabaseHandler.getAllBankBalance(this);
		if (result != null)
		{
			try {
				while (result.next())
				{
					list.add(new BalanceCollection(result.getString("worldName"), result.getDouble("balance"), CurrencyHandler.getCurrency(result.getString("name"), true)));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
		return null;
	}
	
	/**
	 * Add money in a specific world and currency account
	 * @param amount The amount we want to add
	 * @param currency The currency we want to add money in
	 * @param world The world the currency is
	 * @return The balance after all the changes
	 */
	public double addMoney(double amount, Currency currency, World world)
	{
		double balance = getBalance(currency,world);
		balance += amount;
		if (Config.multiWorld)
			DatabaseHandler.updateBankAccount(this, balance, currency, world);
		else
			DatabaseHandler.updateBankAccount(this, balance, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}
	
	/**
	 * Remove money from a player account
	 * @param amount The amount we want to remove from the account
	 * @param currency The currency we want to modify
	 * @param world The world we want to modify the money
	 * @return
	 */
	public double substractMoney(double amount, Currency currency, World world)
	{
		double balance = getBalance(currency,world);
		balance -= amount;
		if (Config.multiWorld)
			DatabaseHandler.updateBankAccount(this, balance, currency, world);
		else
			DatabaseHandler.updateBankAccount(this, balance, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}
	
	/**
	 * Multiply money in the player account
	 * @param amount The amount we want to multiply
	 * @param currency The currency we want to fetch
	 * @param world The world from where we fetch the currency
	 * @return The balance after the money is multiplied
	 */
	public double multiplyMoney(double amount, Currency currency, World world)
	{
		double balance = getBalance(currency,world);
		balance *= amount;
		if (Config.multiWorld)
			DatabaseHandler.updateBankAccount(this, balance, currency, world);
		else
			DatabaseHandler.updateBankAccount(this, balance, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}
	
	/**
	 * Multiply money in the player account
	 * @param amount The amount we want to divide
	 * @param currency The currency we want to fetch
	 * @param world The world from where we fetch the currency
	 * @return The balance after the money is divided
	 */
	public double divideMoney(double amount, Currency currency, World world)
	{
		double balance = getBalance(currency,world);
		balance /= amount;
		if (Config.multiWorld)
			DatabaseHandler.updateBankAccount(this, balance, currency, world);
		else
			DatabaseHandler.updateBankAccount(this, balance, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}
	
	/**
	 * Set the player account to the balance received
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
		else
			DatabaseHandler.updateBankAccount(this, balance, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}
	
	/**
	 * Checks if the player has enough money in his account
	 * @param amount The amount we want to check
	 * @param currency The currency we want to check
	 * @param world The world we want to check
	 * @return True if the player has enough else false
	 */
	public boolean hasEnough(double amount, Currency currency, World world)
	{
		double balance;
		if (Config.multiWorld)
			balance = getBalance(currency,world);
		else
			balance = getBalance(currency,Craftconomy.plugin.getServer().getWorlds().get(0));
		boolean result = false;
		if (balance >= amount)
			result = true;
		return result;
	}
	
	public String getName()
	{
		return bankName;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getOwner()
	{
		return owner;
	}
}
