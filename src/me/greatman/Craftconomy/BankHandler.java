package me.greatman.Craftconomy;

import java.util.ArrayList;
import java.util.List;

import me.greatman.Craftconomy.utils.DatabaseHandler;

/**
 * Bank Handler
 * 
 * @author greatman
 * @see Bank
 */
public class BankHandler
{
	static List<Bank> bankList = new ArrayList<Bank>();

	/**
	 * Get a bank
	 * 
	 * @param bankName the bank name
	 * @return The Bank object if found else null
	 */
	public static Bank getBank(String bankName)
	{
		if (exists(bankName))
		{
			for (Bank bank : bankList)
			{
				if (bank.getName().equalsIgnoreCase(bankName))
				{
					return bank;
				}

			}
			Bank bank = new Bank(bankName);
			bankList.add(bank);
			return bank;
		}
		return null;
	}

	/**
	 * Checks if a bank account exists
	 * 
	 * @param bankName The bank name
	 * @return True if exists else false
	 */
	public static boolean exists(String bankName)
	{
		return DatabaseHandler.bankExists(bankName);
	}

	/**
	 * Create a bank account
	 * 
	 * @param bankName The bank name
	 * @param playerName The owner name
	 * @return True if success else false
	 */
	public static boolean create(String bankName, String playerName)
	{
		if (!exists(bankName))
		{
			return DatabaseHandler.createBank(bankName, playerName);
		}
		return false;
	}

	/**
	 * Delete a bank account
	 * 
	 * @param bankName The bank name
	 * @return True if success else false
	 */
	public static boolean delete(String bankName)
	{
		if (exists(bankName))
		{
			return DatabaseHandler.deleteBank(bankName);
		}
		return false;
	}

	/**
	 * List all bank accounts
	 * 
	 * @return A list of the bank accounts
	 */
	public static List<String> listBanks()
	{
		return DatabaseHandler.listBanks();
	}
}
