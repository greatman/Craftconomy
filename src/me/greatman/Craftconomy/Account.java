package me.greatman.Craftconomy;

import java.util.List;

import me.greatman.Craftconomy.utils.Config;
import me.greatman.Craftconomy.utils.DatabaseHandler;

import org.bukkit.entity.Player;

/**
 * Money account handling. Please use AccountHandler to access this class.
 * @author greatman
 * @see me.greatman.Craftconomy.AccountHandler
 */
public class Account {

	private String playerName;
	private double balance = Config.defaultHoldings;
	private Player player = null;
	private Bank bankAccount;
	private int playerId;
	/**
	 * Load or create a account
	 * @param user The user we want to load or create a account
	 */
	public Account(String user){
		playerName = user;
		if (!DatabaseHandler.exists(playerName))
			DatabaseHandler.create(playerName);
		else
			balance = DatabaseHandler.getAccountAmount(playerName);
		List<Player> playerList = Craftconomy.plugin.getServer().matchPlayer(user);
		if (playerList.size() == 1)
			player = playerList.get(0);
		playerId = DatabaseHandler.getAccountId(playerName);
		bankAccount = new Bank(this);
		
	}
	
	/**
	 * Load or create a account
	 * @param user The user we want to load or create a account
	 */
	public Account(Player user){
		playerName = user.getName();
		if (!DatabaseHandler.exists(playerName))
			DatabaseHandler.create(playerName);
		else
		{
			balance = DatabaseHandler.getAccountAmount(playerName);
		}
		playerId = DatabaseHandler.getAccountId(playerName);
		bankAccount = new Bank(this);
		player = user;
	}
	
	/**
	 * Get the player balance.
	 * @return The balance
	 */
	public double getBalance()
	{
		return balance;
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
	 * Add money in the player account
	 * @param amount The amount we want to add.
	 * @return The balance after the amount is added.
	 */
	public double addMoney(double amount)
	{
		balance += amount;
		return balance;
	}
	
	/**
	 * Remove money in the player account
	 * @param amount the amount we want to add.
	 * @return The balance after the amount is removed.
	 */
	public double substractMoney(double amount)
	{
		balance -= amount;
		return balance;
	}
	
	/**
	 * Multiply money in the player account
	 * @param amount 	the amount we want to multiply
	 * @return	The balance after the money is multiplied
	 */
	public double multiplyMoney(double amount)
	{
		balance *= amount;
		return balance;
	}
	
	/**
	 * Divide the money in the player account
	 * @param amount The amount we want to divide
	 * @return	The balance after the money is divided.
	 */
	public double divideMoney(double amount)
	{
		balance /= amount;
		return balance;
	}
	
	/**
	 * Set the player account to the balance received
	 * @param amount The balance we want to set the account to
	 * @return The current balance after the change.
	 */
	public double setBalance(double amount)
	{
		balance = amount;
		return balance;
	}
	
	/**
	 * Checks if the player has enough money in his account
	 * @param amount The amount we want to check
	 * @return True if the player has enough else false
	 */
	public boolean hasEnough(double amount)
	{
		boolean result = false;
		if (balance >= amount)
			result = true;
		return result;
	}
	
	public Bank getBank(){
		return bankAccount;
	}
	
	public int getPlayerId() {
		return playerId;
	}
}
