package me.greatman.Craftconomy;

import java.util.HashMap;

import me.greatman.Craftconomy.utils.DatabaseHandler;

/**
 * Currency manager
 * 
 * @author greatman
 * @author steffengy
 * @see CurrencyHandler
 */
public class Currency
{

	/**
	 * Contains the Currency name
	 */
	private String name;

	/**
	 * Contains the database ID
	 */
	private int databaseId;

	/**
	 * This contains how much this currency is worth as other currency f.e. 1 of
	 * this currency is that much worth as 2 of the other :
	 * worthOther.put(OTHER_CURRENCY, 2); String is case-sensitive
	 */
	private HashMap<String, Double> worthOther = new HashMap<String, Double>();

	public Currency(String currencyName)
	{
		name = currencyName;
		databaseId = DatabaseHandler.getCurrencyId(name);
	}

	/**
	 * Get the currency name
	 * 
	 * @return The currency name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Get the database ID
	 * 
	 * @return the database ID
	 */
	public int getdatabaseId()
	{
		return databaseId;
	}

	/**
	 * Get the exchange rate for a currency
	 * 
	 * @param other Name of currency !case sensitive!
	 * @return double
	 */
	public double getExchangeRate(String other)
	{
		if (worthOther.containsKey(other))
		{
			return worthOther.get(other);
		}
		else
		{
			return 1.00; // Same worth as this
		}
	}

	/**
	 * Set the rate of a currency
	 * 
	 * @param other The other currency
	 * @param rate The rate
	 */
	public void setExchangeRate(String other, double rate)
	{
		worthOther.put(other, rate);
	}
}
