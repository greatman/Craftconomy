package me.greatman.Craftconomy.commands.config;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.CurrencyHandler;
import me.greatman.Craftconomy.commands.BaseCommand;

public class ConfigCurrencyAddCommand extends BaseCommand{

	public ConfigCurrencyAddCommand() {
		this.command.add("currencyadd");
		this.requiredParameters.add("Currency Name");
		permFlag = ("Craftconomy.currency.create");
		helpDescription = "Create a currency";
	}
	public void perform() {
		if (!CurrencyHandler.exists(this.parameters.get(0), true))
		{
			if (CurrencyHandler.create(this.parameters.get(0)))
				sendMessage("Currency created!");
			else
				sendMessage("A error occured!");
		}
		else
			sendMessage(ChatColor.RED + "This currency already exists!");
	}
}
