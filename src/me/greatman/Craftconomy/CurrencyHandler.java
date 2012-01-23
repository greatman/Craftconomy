package me.greatman.Craftconomy;

import java.util.List;
import java.util.ArrayList;

import me.greatman.Craftconomy.utils.DatabaseHandler;

public class CurrencyHandler {

	static List<Currency> currencyList = new ArrayList<Currency>();
	
	/**
	 * Get a currency
	 * @param currencyName The name of the currency
	 * @param exact If we want to search for the name or not (Partial or Complete)
	 * @return a Currency object
	 */
	public static Currency getCurrency(String currencyName, boolean exact)
	{
		if (exists(currencyName,exact))
		{
			String currencyFullName = DatabaseHandler.getCurrencyName(currencyName,exact);
			for (Currency currency : currencyList)
			{
				if (currency.getName().equalsIgnoreCase(currencyFullName))
				{
					return currency;
				}
				
			}
			Currency currency = new Currency(currencyFullName);
			currencyList.add(currency);
		}
		return null;
		
	}

	public static boolean exists(String currencyName, boolean exact) {
		if (DatabaseHandler.currencyExist(currencyName,exact))
			return true;
		else
			return false;
	}
	
	public static boolean create(String currencyName)
	{
		return DatabaseHandler.createCurrency(currencyName);
	}

	public static boolean rename(String oldCurrencyName, String newCurrencyName) {
		return DatabaseHandler.modifyCurrency(oldCurrencyName, newCurrencyName);
	}

	public static boolean delete(String currencyName) {
		return DatabaseHandler.removeCurrency(currencyName);
	}
}
