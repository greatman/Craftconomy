package me.greatman.Craftconomy;

public class BalanceCollection {

	private String 	world;
	private double balance;
	
	public BalanceCollection(String worldName, double balanceAmount) {
		world = worldName;
		balance = balanceAmount;
	}
	
	public String getWorldName()
	{
		return world;
	}
	
	public double getBalance()
	{
		return balance;
	}
}
