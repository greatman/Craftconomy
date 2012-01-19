package me.greatman.Craftconomy.utils;

import me.greatman.Craftconomy.Craftconomy;


public class Config {

	static Craftconomy plugin;
	
	public static String 	currencyDefault,
							databaseType,
							databaseMoneyTable,
							databaseBankTable,
							databaseCurrencyTable,
							databaseBalanceTable,
							databaseUsername,
							databasePassword,
							databaseAddress,
							databasePort,
							databaseDb;
					
	public static double	defaultHoldings,
					interestCutOff,
					interestPercent;
	
	public static boolean	minorFormatting,
					seperateFormatting,
					singleFormatting,
					transactionLogging,
					interestEnabled,
					interestOnlyOnline,
					interestAnnounce,
					multiWorld;
					
	public static int 		interestInterval;
					
	
	public static void load(Craftconomy thePlugin)
	{
		plugin = thePlugin;
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
		
		currencyDefault = plugin.getConfig().getString("System.Default.Currency.Name");
		
		multiWorld = plugin.getConfig().getBoolean("System.Default.Currency.MultiWorld");
		
		defaultHoldings = plugin.getConfig().getDouble("System.Default.Account.Holdings");
		
		minorFormatting = plugin.getConfig().getBoolean("System.Formatting.Minor");
		seperateFormatting = plugin.getConfig().getBoolean("System.Formatting.Seperate");
		singleFormatting = plugin.getConfig().getBoolean("System.Formatting.Single");
		
		transactionLogging = plugin.getConfig().getBoolean("System.Logging.Enabled");
		
		interestEnabled = plugin.getConfig().getBoolean("System.Interest.Enabled");
		interestOnlyOnline = plugin.getConfig().getBoolean("System.Interest.Online");
		interestAnnounce = plugin.getConfig().getBoolean("System.Interest.Announce.Enabled");
		interestInterval = plugin.getConfig().getInt("System.Interest.Interval.Seconds");
		interestCutOff = plugin.getConfig().getDouble("System.Interest.Amount.Cutoff");
		interestPercent = plugin.getConfig().getDouble("System.Interest.Amount.Percent");
		
		databaseMoneyTable = plugin.getConfig().getString("System.Database.MoneyTable");
		databaseBankTable = plugin.getConfig().getString("System.Database.BankTable");
		databaseCurrencyTable = plugin.getConfig().getString("System.Database.CurrencyTable");
		databaseBalanceTable = plugin.getConfig().getString("System.Database.BalanceTable");
		
		databaseType = plugin.getConfig().getString("System.Database.Type");
		databaseAddress = plugin.getConfig().getString("System.Database.Address");
		databasePort = plugin.getConfig().getString("System.Database.Port");
		databaseUsername = plugin.getConfig().getString("System.Database.Username");
		databasePassword = plugin.getConfig().getString("System.Database.Password");
		databaseDb = plugin.getConfig().getString("System.Database.Db");
	}
}
