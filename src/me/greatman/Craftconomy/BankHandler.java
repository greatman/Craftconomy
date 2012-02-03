package me.greatman.Craftconomy;

import java.util.ArrayList;
import java.util.List;

import me.greatman.Craftconomy.utils.DatabaseHandler;

public class BankHandler
{
	static List<Bank> bankList = new ArrayList<Bank>();
	
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
	
	public static boolean exists(String bankName)
	{
		return DatabaseHandler.bankExists(bankName);
	}
	
	public static boolean create(String bankName, String playerName)
	{
		if (!exists(bankName))
		{
			return DatabaseHandler.createBank(bankName, playerName);
		}
		return false;
	}
	
	public static boolean delete(String bankName)
	{
		if (exists(bankName))
		{
			return DatabaseHandler.deleteBank(bankName);
		}
		return false;
	}
}
