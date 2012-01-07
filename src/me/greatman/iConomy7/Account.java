package me.greatman.iConomy7;

import java.util.List;

import me.greatman.iConomy7.utils.Config;
import me.greatman.iConomy7.utils.DatabaseHandler;

import org.bukkit.entity.Player;

public class Account {

	private String playerName;
	private double balance = Config.defaultHoldings;
	private Player player = null;
	public Account(String user){
		playerName = user;
		if (!DatabaseHandler.exists(playerName))
			DatabaseHandler.create(playerName);
		else
			balance = DatabaseHandler.getAccountAmount(playerName);
		List<Player> playerList = iConomy.plugin.getServer().matchPlayer(user);
		if (playerList.size() == 1)
			player = playerList.get(0);
	}
	public Account(Player user){
		playerName = user.getName();
		if (!DatabaseHandler.exists(playerName))
			DatabaseHandler.create(playerName);
		else
			balance = DatabaseHandler.getAccountAmount(playerName);
		player = user;
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
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
}
