package me.greatman.Craftconomy;

import me.greatman.Craftconomy.utils.DatabaseHandler;

public class Bank {

	private double balance;
	private Account playerAccount;
	public Bank(Account moneyAccount) {
		playerAccount = moneyAccount;
		
		balance = DatabaseHandler.getBankAmount(playerAccount);
	}
	
	/**
	 * Add money to the bank account
	 * @param amount The amount of money we want to add to the account
	 * @return The balance after the change
	 */
	public double addMoney(double amount)
	{
		balance += amount;
		return balance;
	}
	
	public double substractMoney(double amount)
	{
		balance -= amount;
		return balance;
	}
	
	public double multiplyMoney(double amount)
	{
		balance *= amount;
		return balance;
	}
	
	public double divideMoney(double amount)
	{
		balance /= amount;
		return balance;
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public boolean hasEnough(double amount)
	{
		boolean result = false;
		if (balance >= amount)
			result = true;
		return result;
	}
}
