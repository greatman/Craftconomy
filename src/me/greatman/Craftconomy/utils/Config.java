package me.greatman.Craftconomy.utils;

import java.util.List;

import me.greatman.Craftconomy.Craftconomy;


public class Config {

	static Craftconomy plugin;
	
	public static String 	currencyDefault,
							databaseType,
							databaseAccountTable,
							databaseBankTable,
							databaseBankBalanceTable,
							databaseCurrencyTable,
							databaseBalanceTable,
							databaseUsername,
							databasePassword,
							databaseAddress,
							databasePort,
							databaseDb;
					
	public static double	defaultHoldings,
							bankPrice;
	
	public static boolean	multiWorld,
							transactionLogging;
					
	public static List<String> payDayList;
	
	public static void load(Craftconomy thePlugin)
	{
		plugin = thePlugin;
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
		
		currencyDefault = plugin.getConfig().getString("System.Default.Currency.Name");
		
		multiWorld = plugin.getConfig().getBoolean("System.Default.Currency.MultiWorld");
		
		defaultHoldings = plugin.getConfig().getDouble("System.Default.Account.Holdings");
		
		bankPrice = plugin.getConfig().getDouble("System.Default.Bank.Price");
		
		transactionLogging = plugin.getConfig().getBoolean("System.Logging.Enabled");
		
		payDayList = plugin.getConfig().getStringList("System.PayDay.Enabled");
		
		databaseAccountTable = plugin.getConfig().getString("System.Database.AccountTable");
		databaseBankTable = plugin.getConfig().getString("System.Database.BankTable");
		databaseCurrencyTable = plugin.getConfig().getString("System.Database.CurrencyTable");
		databaseBalanceTable = plugin.getConfig().getString("System.Database.BalanceTable");
		databaseBankBalanceTable = plugin.getConfig().getString("System.Database.BankBalanceTable");
		
		databaseType = plugin.getConfig().getString("System.Database.Type");
		databaseAddress = plugin.getConfig().getString("System.Database.Address");
		databasePort = plugin.getConfig().getString("System.Database.Port");
		databaseUsername = plugin.getConfig().getString("System.Database.Username");
		databasePassword = plugin.getConfig().getString("System.Database.Password");
		databaseDb = plugin.getConfig().getString("System.Database.Db");
	}
}
