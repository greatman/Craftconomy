package me.greatman.Craftconomy;

import me.greatman.Craftconomy.utils.DatabaseHandler;

public class Currency {

	private String name;
	private int databaseId;
	
	public Currency(String currencyName)
	{
		name = currencyName;
		databaseId = DatabaseHandler.getCurrencyId(name);
	}
	
	public String getName(){
		return name;
	}
	
	public int getdatabaseId()
	{
		return databaseId;
	}
}
