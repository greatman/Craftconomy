package me.greatman.Craftconomy.commands.config;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.CurrencyHandler;
import me.greatman.Craftconomy.commands.BaseCommand;

public class ConfigCurrencyModifyCommand extends BaseCommand
{

	public ConfigCurrencyModifyCommand()
	{
		this.command.add("currencymodify");
		this.requiredParameters.add("Currency Name");
		this.requiredParameters.add("New currency Name");
		permFlag = ("Craftconomy.currency.modify");
		helpDescription = "Modify a currency";
	}

	public void perform()
	{
		if (CurrencyHandler.exists(this.parameters.get(0), true))
		{
			if (CurrencyHandler.rename(this.parameters.get(0), this.parameters.get(1)))
				sendMessage("Currency modified!");
			else sendMessage("A error occured!");
		}
		else sendMessage(ChatColor.RED + "This currency doesn't exists!");
	}
}
