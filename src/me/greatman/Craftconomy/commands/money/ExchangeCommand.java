package me.greatman.Craftconomy.commands.money;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.Currency;
import me.greatman.Craftconomy.CurrencyHandler;
import me.greatman.Craftconomy.commands.BaseCommand;
import me.greatman.Craftconomy.utils.Config;

public class ExchangeCommand extends BaseCommand
{
	public ExchangeCommand()
	{
		this.command.add("exchange");
		this.requiredParameters.add("Source Currency");
		this.requiredParameters.add("Destination Currency");
		this.requiredParameters.add("Amount");
		permFlag = ("Craftconomy.money.exchange");
		helpDescription = "Exchange money to another currency";
	}

	public void perform()
	{
		Currency source = CurrencyHandler.getCurrency(Config.currencyDefault, true);
		int index = 0;
		// - none standard
		if (parameters.size() == 3)
		{
			if (CurrencyHandler.exists(this.parameters.get(0), false))
			{
				source = CurrencyHandler.getCurrency(this.parameters.get(0), false);
			}
			else
			{
				sendMessage(ChatColor.RED + "Currency " + this.parameters.get(0) + " does not exist!");
			}
			index = 1;
		}

		if (CurrencyHandler.exists(this.parameters.get(index), false))
		{
			Currency dest = CurrencyHandler.getCurrency(this.parameters.get(index), false);
			Account client = new Account(this.player.getName());
			// - validate amount parameter
			if (Craftconomy.isValidAmount(parameters.get(index + 1)))
			{
				BigDecimal amount;
				amount = new BigDecimal(parameters.get(index + 1));
				// - has the user enough of source currency?
				if (client.hasEnough(amount, source))
				{
					// now simply convert + add
					client.addMoney(CurrencyHandler.convert(source, dest, amount), dest);
					client.substractMoney(amount, source);
					sendMessage("You just converted " + ChatColor.WHITE + Craftconomy.format(amount, source)
							+ ChatColor.GREEN + " into " + ChatColor.WHITE
							+ Craftconomy.format(CurrencyHandler.convert(source, dest, amount), dest) + ChatColor.GREEN
							+ ".");
				}
				else
				{
					sendMessage(ChatColor.RED + "You don't have " + ChatColor.WHITE
							+ Craftconomy.format(amount, source) + ChatColor.GREEN + "!");
				}
			}
			else
			{
				sendMessage(ChatColor.RED + "Invalid amount! Not numeric!");
				return;
			}
		}
		else
		{
			sendMessage(ChatColor.RED + "Currency " + ChatColor.WHITE + this.parameters.get(index) + ChatColor.GREEN
					+ " does not exist!");
		}
	}
}
