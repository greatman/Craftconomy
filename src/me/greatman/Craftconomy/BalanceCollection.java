package me.greatman.Craftconomy;


public class BalanceCollection {

	private String	world;
	private double 	balance;
	private Currency currency;
	
	public BalanceCollection(String worldName, double balanceAmount, Currency currency) {
		this.world = worldName;
		balance = balanceAmount;
		this.currency = currency;
	}
	
	public String getWorldName()
	{
		return Craftconomy.plugin.getServer().getWorld(world).getName();
	}
	
	public double getBalance()
	{
		return balance;
	}
	
	public String getCurrencyName()
	{
		return currency.getName();
	}
}
