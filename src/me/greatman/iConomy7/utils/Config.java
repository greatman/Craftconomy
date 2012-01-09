package me.greatman.iConomy7.utils;

import me.greatman.iConomy7.iConomy;


public class Config {

	static iConomy plugin;
	
	public static String 	currencyMajorSingle,
							currencyMajorPlural,
							currencyMinorSingle,
							currencyMinorPlural,
							databaseType,
							databaseTable,
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
					interestAnnounce;
					
	public static int 		interestInterval;
					
	
	public static void load(iConomy thePlugin)
	{
		plugin = thePlugin;
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
		currencyMajorSingle = plugin.getConfig().getString("System.Default.Currency.Major.Single");
		currencyMajorPlural = plugin.getConfig().getString("System.Default.Currency.Major.Plural");
		currencyMinorSingle = plugin.getConfig().getString("System.Default.Currency.Minor.Single");
		currencyMinorPlural = plugin.getConfig().getString("System.Default.Currency.Minor.Plural");
		
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
		
		databaseType = plugin.getConfig().getString("System.Database.Type");
		databaseTable = plugin.getConfig().getString("System.Database.Table");
		databaseAddress = plugin.getConfig().getString("System.Database.Address");
		databasePort = plugin.getConfig().getString("System.Database.Port");
		databaseUsername = plugin.getConfig().getString("System.Database.Username");
		databasePassword = plugin.getConfig().getString("System.Database.Password");
		databaseDb = plugin.getConfig().getString("System.Database.Db");
	}
}
