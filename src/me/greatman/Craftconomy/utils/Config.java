package me.greatman.Craftconomy.utils;

import java.util.List;

import me.greatman.Craftconomy.Craftconomy;

public class Config
{

	static Craftconomy plugin;

	public static String currencyDefault, databaseType, databaseAccountTable, databaseBankTable,
			databaseBankBalanceTable, databaseBankMemberTable, databaseCurrencyTable, databaseCurrencyExchangeTable,
			databaseBalanceTable, databaseUsername, databasePassword, databaseAddress, databasePort, databaseDb,
			bankCurrency;

	public static double defaultHoldings, bankPrice;

	public static boolean multiWorld, transactionLogging;

	public static List<String> payDayList;

	public static void load(Craftconomy thePlugin)
	{
		plugin = thePlugin;
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();

		currencyDefault = plugin.getConfig().getString("System.Default.Currency.Name");

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
	}
}
