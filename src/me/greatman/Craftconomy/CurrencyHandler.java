package me.greatman.Craftconomy;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Map.Entry;

import me.greatman.Craftconomy.utils.DatabaseHandler;

/**
 * Currency Handler
 * 
 * @author greatman
 * @author steffengy
 * @see Currency
 */
public class CurrencyHandler
{

	/**
	 * Contains the list of currently loaded currency
	 */
	private static List<Currency> currencyList = new ArrayList<Currency>();

	/**
	 * Get a currency
	 * 
	 * @param currencyName The name of the currency
	 * @param exact If we want to search for the name or not (Partial or
	 *            Complete)
	 * @return a Currency object
	 */
	public static Currency getCurrency(String currencyName, boolean exact)
	{
		if (exists(currencyName, exact))
		{
			String currencyFullName = DatabaseHandler.getCurrencyName(currencyName, exact);
			for (Currency currency : currencyList)
			{
				if (currency.getName().equalsIgnoreCase(currencyFullName))
				{
					return currency;
				}

			}
			Currency currency = new Currency(currencyFullName);
			for (Entry<String, Double> entry : DatabaseHandler.getExchangeRates(currency).entrySet())
			{
				// Tell the currency the exchange rate
				currency.setExchangeRate(entry.getKey(), entry.getValue());
			}
			currencyList.add(currency);
			return currency;
		}
		return null;

	}

	/**
	 * Converts a -> b
	 * 
	 * @param src The source currency
	 * @param dest The destination currency
	 * @param amount The amount (as source currency)
	 * @return
	 */
	public static BigDecimal convert(Currency src, Currency dest, BigDecimal amount)
	{
		return amount.multiply(new BigDecimal(src.getExchangeRate(dest.getName())));
	}

	/**
	 * Checks if a currency exists
	 * 
	 * @param currencyName The currency name
	 * @param exact Is it supposed to be a exact match?
	 * @return True if the currency exists else false
	 */
	public static boolean exists(String currencyName, boolean exact)
	{
		if (DatabaseHandler.currencyExist(currencyName, exact))
			return true;
		else return false;
	}

	/**
	 * Create a currency
	 * 
	 * @param currencyName The currency name
	 * @return True if success else false
	 */
	public static boolean create(String currencyName)
	{
		return DatabaseHandler.createCurrency(currencyName);
	}

	/**
	 * Rename a currency
	 * 
	 * @param oldCurrencyName The old (Current) currency name
	 * @param newCurrencyName The new currency name
	 * @return True if success else false
	 */
	public static boolean rename(String oldCurrencyName, String newCurrencyName)
	{
		return DatabaseHandler.modifyCurrency(oldCurrencyName, newCurrencyName);
	}

	/**
	 * Delete a currency
	 * 
	 * @param currencyName The currency name
	 * @return True if success else false
	 */
	public static boolean delete(String currencyName)
	{
		return DatabaseHandler.removeCurrency(currencyName);
	}

	/**
	 * Set the currency exchange rate
	 * 
	 * @param src The source currency
	 * @param dest The destination currency
	 * @param rate The rate
	 */
	public static void setExchangeRate(Currency src, Currency dest, double rate)
	{
		DatabaseHandler.setExchangeRate(src, dest, rate);
	}
}
