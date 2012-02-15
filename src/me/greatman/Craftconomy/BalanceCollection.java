package me.greatman.Craftconomy;

import java.math.BigDecimal;

public class BalanceCollection
{

	private String world;
	private BigDecimal balance;
	private Currency currency;

	public BalanceCollection(String worldName, BigDecimal balanceAmount, Currency currency)
	{
		this.world = worldName;
		balance = balanceAmount;
		this.currency = currency;
	}

	public String getWorldName()
	{
		return Craftconomy.plugin.getServer().getWorld(world).getName();
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

	public String getCurrencyName()
	{
		return currency.getName();
	}
}
