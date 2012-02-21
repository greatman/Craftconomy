package me.greatman.Craftconomy.utils;

import java.util.List;

import me.greatman.Craftconomy.Craftconomy;

public class Config
{

	static Craftconomy plugin;

	public static String currencyDefault, currencyDefaultPlural, currencyDefaultMinor, currencyDefaultMinorPlural, databaseType, databaseAccountTable, databaseBankTable,
			databaseBankBalanceTable, databaseBankMemberTable, databaseCurrencyTable, databaseCurrencyExchangeTable,
			databaseBalanceTable, databaseUsername, databasePassword, databaseAddress, databasePort, databaseDb,
			bankCurrency, convertType, convertTableName, convertDatabaseType, convertDatabaseAddress, convertDatabasePort, convertDatabaseUsername, convertDatabasePassword, convertDatabaseDb;

	public static double defaultHoldings, bankPrice;

	public static boolean multiWorld, transactionLogging, convertEnabled, fixName;

	public static List<String> payDayList;

	public static void load(Craftconomy thePlugin)
	{
		plugin = thePlugin;
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();

		fixName = plugin.getConfig().getBoolean("System.Debug.fixName");
		
		currencyDefault = plugin.getConfig().getString("System.Default.Currency.Name");
		currencyDefaultPlural = plugin.getConfig().getString("System.Default.Currency.NamePlural");
		currencyDefaultMinor = plugin.getConfig().getString("System.Default.Currency.Minor");
		currencyDefaultMinorPlural = plugin.getConfig().getString("System.Default.Currency.MinorPlural");

		multiWorld = plugin.getConfig().getBoolean("System.Default.Currency.MultiWorld");

		defaultHoldings = plugin.getConfig().getDouble("System.Default.Account.Holdings");

		bankPrice = plugin.getConfig().getDouble("System.Bank.Price");
		bankCurrency = plugin.getConfig().getString("System.Bank.Currency");

		transactionLogging = plugin.getConfig().getBoolean("System.Logging.Enabled");

		payDayList = plugin.getConfig().getStringList("System.PayDay.Enabled");

		databaseAccountTable = plugin.getConfig().getString("System.Database.AccountTable");
		databaseBankTable = plugin.getConfig().getString("System.Database.BankTable");
		databaseCurrencyTable = plugin.getConfig().getString("System.Database.CurrencyTable");
		databaseCurrencyExchangeTable = plugin.getConfig().getString("System.Database.CurrencyExchangeTable");
		databaseBalanceTable = plugin.getConfig().getString("System.Database.BalanceTable");
		databaseBankBalanceTable = plugin.getConfig().getString("System.Database.BankBalanceTable");
		databaseBankMemberTable = plugin.getConfig().getString("System.Database.BankMemberTable");
		
		databaseType = plugin.getConfig().getString("System.Database.Type");
		databaseAddress = plugin.getConfig().getString("System.Database.Address");
		databasePort = plugin.getConfig().getString("System.Database.Port");
		databaseUsername = plugin.getConfig().getString("System.Database.Username");
		databasePassword = plugin.getConfig().getString("System.Database.Password");
		databaseDb = plugin.getConfig().getString("System.Database.Db");
		
		convertEnabled = plugin.getConfig().getBoolean("System.Convert.Enabled");
		convertType = plugin.getConfig().getString("System.Convert.Type");
		convertTableName = plugin.getConfig().getString("System.Convert.TableName");
		convertDatabaseType = plugin.getConfig().getString("System.Convert.DatabaseType");
		convertDatabaseAddress = plugin.getConfig().getString("System.Convert.Address");
		convertDatabasePort = plugin.getConfig().getString("System.Convert.Port");
		convertDatabaseUsername = plugin.getConfig().getString("System.Convert.Username");
		convertDatabasePassword = plugin.getConfig().getString("System.Convert.Password");
		convertDatabaseDb = plugin.getConfig().getString("System.Convert.Database");
	}
}
