package me.greatman.Craftconomy.commands.config;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.CurrencyHandler;
import me.greatman.Craftconomy.commands.BaseCommand;

public class ConfigCurrencyModifyCommand extends BaseCommand
{

	public ConfigCurrencyModifyCommand()
	{
		this.command.add("currencymodify");
		this.requiredParameters.add("name/plural/minor/minorplural");
		this.requiredParameters.add("Currency Name");
		this.requiredParameters.add("New Name");
		permFlag = ("Craftconomy.currency.modify");
		helpDescription = "Modify a currency";
	}

	public void perform()
	{
		if (CurrencyHandler.exists(this.parameters.get(1), true))
		{
			if (CurrencyHandler.editType.valueOf(this.parameters.get(0).toUpperCase()) != null)
			{
				if (CurrencyHandler.rename(CurrencyHandler.editType.valueOf(this.parameters.get(0).toUpperCase()),this.parameters.get(1), this.parameters.get(2)))
					sendMessage("Currency modified!");
				else 
					sendMessage(ChatColor.RED + "A error occured!");
			}
			else
				sendMessage(ChatColor.RED + "Wrong edit type! The one accepted are name, plural, minor, minorplural");
		}
		else sendMessage(ChatColor.RED + "This currency doesn't exists!");
	}
}
