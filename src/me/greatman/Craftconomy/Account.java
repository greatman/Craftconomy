package me.greatman.Craftconomy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.greatman.Craftconomy.utils.Config;
import me.greatman.Craftconomy.utils.DatabaseHandler;

import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Money account handling. Please use AccountHandler to access this class.
 * @author greatman
 * @see me.greatman.Craftconomy.AccountHandler
 */
public class Account {

	private String playerName;
	private Player player = null;
	private int playerId;
	/**
	 * Load or create a account
	 * @param user The user we want to load or create a account
	 */
	public Account(String user){
		playerName = user;
		if (!DatabaseHandler.exists(playerName))
			DatabaseHandler.create(playerName);
		List<Player> playerList = Craftconomy.plugin.getServer().matchPlayer(user);
		if (playerList.size() == 1)
			player = playerList.get(0);
		playerId = DatabaseHandler.getAccountId(playerName);
	}
	
	/**
	 * Load or create a account
	 * @param user The user we want to load or create a account
	 */
	public Account(Player user){
		playerName = user.getName();
		if (!DatabaseHandler.exists(playerName))
			DatabaseHandler.create(playerName);
		playerId = DatabaseHandler.getAccountId(playerName);
		player = user;
	}
	
	public double getDefaultBalance()
	{
			return getBalance(CurrencyHandler.getCurrency(Config.currencyDefault, true));
	}
	/**
	 * Get the default balance in the specific world
	 * @param world The world we want to get the balance
	 * @return The account balance
	 */
	public double getBalance(World world)
	{
		if (!Config.multiWorld)
			return getBalance(CurrencyHandler.getCurrency(Config.currencyDefault, true));
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
			return getBalance(currency);
		return DatabaseHandler.getBalanceCurrency(this, world, currency);
	}
	/**
	 * Get a specific player currency
	 * @param currency The Currency we want to check
	 * @return The balance of that according currency
	 */
	public double getBalance(Currency currency)
	{
		double balance;
		if (Config.multiWorld)
			balance = DatabaseHandler.getBalanceCurrency(this, player.getWorld(), currency);
		else
			balance = DatabaseHandler.getBalanceCurrency(this,Craftconomy.plugin.getServer().getWorlds().get(0),currency);
		return balance;
	}
	/**
	 * Get All the player balance.
	 * @return The balance
	 */
	public List<BalanceCollection> getBalance()
	{
		ResultSet result;
		List<BalanceCollection> list = new ArrayList<BalanceCollection>();
		result = DatabaseHandler.getAllBalance(this);
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
	 * Get the player name.
	 * @return The player name
	 */
	public String getPlayerName()
	{
		return playerName;
	}
	
	/**
	 * Get the player Entity.
	 * @return The player entity
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	/**
	 * Add money in the player account. Default money
	 * @param amount The amount we want to add.
	 * @return The balance after the amount is added.
	 */
	public double addMoney(double amount)
	{
		if (Config.multiWorld)
			return addMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), player.getWorld());
		else
			return addMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), Craftconomy.plugin.getServer().getWorlds().get(0));
	}
	
	/**
	 * Add money in the player account on the specified currency
	 * @param amount The amount we want to add
	 * @param currency The currency we want to add the money
	 * @return The balance after the changes
	 */
	public double addMoney(double amount, Currency currency)
	{
		if (Config.multiWorld)
			return addMoney(amount, currency, player.getWorld());
		else
			return addMoney(amount, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
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
			DatabaseHandler.updateAccount(this, balance, currency, world);
		else
			DatabaseHandler.updateAccount(this, balance, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}
	/**
	 * Add money in a specific world with the default currency
	 * @param amount The amount we want to add
	 * @param world The world we want to add the money
	 * @return The balance after all the changes
	 */
	public double addMoney(double amount, World world)
	{
		return addMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), world);
	}
	
	/**
	 * Remove money in the player account. Default world and currency
	 * @param amount the amount we want to add.
	 * @return The balance after the amount is removed.
	 */
	public double substractMoney(double amount)
	{
		if (Config.multiWorld)
			return substractMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), player.getWorld());
		else
			return substractMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), Craftconomy.plugin.getServer().getWorlds().get(0));
	}
	
	/**
	 * Remove money from a player account.
	 * @param amount The amount we want to remove from the account
	 * @param currency The currency we want to modify
	 * @return
	 */
	public double substractMoney(double amount, Currency currency)
	{
		if (Config.multiWorld)
			return substractMoney(amount, currency, player.getWorld());
		else
			return substractMoney(amount, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
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
			DatabaseHandler.updateAccount(this, balance, currency, world);
		else
			DatabaseHandler.updateAccount(this, balance, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}
	
	/**
	 * Remove money from a player account
	 * @param amount The amount we want to remove from the account
	 * @param world The world we want to modify the money
	 * @return
	 */
	public double substractMoney(double amount, World world)
	{
		return substractMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), world);
	}
	
	/**
	 * Multiply money in the player account
	 * @param amount 	the amount we want to multiply
	 * @return	The balance after the money is multiplied
	 */
	public double multiplyMoney(double amount)
	{
		if (Config.multiWorld)
			return multiplyMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), player.getWorld());
		else
			return multiplyMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), Craftconomy.plugin.getServer().getWorlds().get(0));
	}
	
	/**
	 * Multiply money in the player account
	 * @param amount The amount we want to multiply
	 * @param currency The currency we want to fetch
	 * @return The balance after the money is multiplied.
	 */
	public double multiplyMoney(double amount, Currency currency)
	{
		if (Config.multiWorld)
			return multiplyMoney(amount, currency, player.getWorld());
		else
			return multiplyMoney(amount, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
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
			DatabaseHandler.updateAccount(this, balance, currency, world);
		else
			DatabaseHandler.updateAccount(this, balance, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}
	
	/**
	 * Multiply money in the player account
	 * @param amount The amount we want to multiply
	 * @param world The world we fetch the money
	 * @return The balance after the money is multiplied
	 */
	public double multiplyMoney(double amount, World world)
	{
		return multiplyMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), world);
	}
	/**
	 * Divide the money in the player account
	 * @param amount The amount we want to divide
	 * @return	The balance after the money is divided.
	 */
	public double divideMoney(double amount)
	{
		if (Config.multiWorld)
			return divideMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), player.getWorld());
		else
			return divideMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), Craftconomy.plugin.getServer().getWorlds().get(0));
	}
	
	/**
	 * Multiply money in the player account
	 * @param amount The amount we want to divide
	 * @param currency The currency we want to fetch
	 * @return The balance after the money is divided
	 */
	public double divideMoney(double amount, Currency currency)
	{
		if (Config.multiWorld)
			return divideMoney(amount, currency, player.getWorld());
		else
			return divideMoney(amount, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
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
			DatabaseHandler.updateAccount(this, balance, currency, world);
		else
			DatabaseHandler.updateAccount(this, balance, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}
	
	/**
	 * Divide money in the player account
	 * @param amount The amount we want to divide
	 * @param world The world we fetch the money
	 * @return The balance after the money is divided
	 */
	public double divideMoney(double amount, World world)
	{
		return divideMoney(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), world);
	}
	
	/**
	 * Set the player account to the balance received
	 * @param amount The balance we want to set the account to
	 * @return The current balance after the change.
	 */
	public double setBalance(double amount)
	{
		if (Config.multiWorld)
			return setBalance(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), player.getWorld());
		else
			return setBalance(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), Craftconomy.plugin.getServer().getWorlds().get(0));
	}
	
	/**
	 * Set the player account to the balance received
	 * @param amount The balance we want to set the account to
	 * @param currency The currency we want to change
	 * @return The current balance after the change
	 */
	public double setBalance(double amount, Currency currency)
	{
		if (Config.multiWorld)
			return setBalance(amount, currency, player.getWorld());
		else
			return setBalance(amount, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
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
			DatabaseHandler.updateAccount(this, balance, currency, world);
		else
			DatabaseHandler.updateAccount(this, balance, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
		return balance;
	}
	
	/**
	 * Set the player account to the balance received
	 * @param amount The balance we want to set the account to
	 * @param world The world we want to change
	 * @return The balance after the change.
	 */
	public double setBalance(double amount, World world)
	{
		return setBalance(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), world);
	}
	
	/**
	 * Checks if the player has enough money in his account
	 * @param amount The amount we want to check
	 * @return True if the player has enough else false
	 */
	public boolean hasEnough(double amount)
	{
		if (Config.multiWorld)
			return hasEnough(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), player.getWorld());
		else
			return hasEnough(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), Craftconomy.plugin.getServer().getWorlds().get(0));
	}
	
	/**
	 * Checks if the player has enough money in his account
	 * @param amount The amount we want to check
	 * @param currency The currency we want to check
	 * @return True if the player has enough else false
	 */
	public boolean hasEnough(double amount, Currency currency) 
	{
		if (Config.multiWorld)
			return hasEnough(amount, currency, player.getWorld());
		else
			return hasEnough(amount, currency, Craftconomy.plugin.getServer().getWorlds().get(0));
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
	
	/**
	 * Checks if the player has enough money in his account
	 * @param amount The amount we want to check
	 * @param world The world we want to check
	 * @return True if the player has enough else false
	 */
	public boolean hasEnough(double amount, World world)
	{
		return hasEnough(amount, CurrencyHandler.getCurrency(Config.currencyDefault, true), world);
	}
	public int getPlayerId() {
		return playerId;
	}

	
}
