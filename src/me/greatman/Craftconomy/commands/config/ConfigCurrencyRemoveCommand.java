package me.greatman.Craftconomy.commands.config;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.CurrencyHandler;
import me.greatman.Craftconomy.commands.BaseCommand;

public class ConfigCurrencyRemoveCommand extends BaseCommand{

	public ConfigCurrencyRemoveCommand() {
		this.command.add("currencyremove");
		this.requiredParameters.add("Currency Name");
		permFlag = ("Craftconomy.currency.remove");
		helpDescription = "Remove a currency";
	}
	public void perform() {
		if (CurrencyHandler.exists(this.parameters.get(0), true))
		{
			if (CurrencyHandler.delete(this.parameters.get(0)))
				sendMessage("Currency created!");
			else
				sendMessage("A error occured!");
		}
		else
			sendMessage(ChatColor.RED + "This currency doesn't exists!");
	}
}
