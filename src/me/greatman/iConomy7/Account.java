package me.greatman.iConomy7;

import me.greatman.iConomy7.utils.DatabaseHandler;

import org.bukkit.entity.Player;

public class Account {

	private String playerName;
	private double balance;
	public Account(Player user){
		playerName = user.getName();
		if (!DatabaseHandler.exists(playerName))
			DatabaseHandler.create(playerName);
		else
			balance = DatabaseHandler.getAccountAmount(playerName);
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public String getPlayerName()
	{
		return playerName;
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
