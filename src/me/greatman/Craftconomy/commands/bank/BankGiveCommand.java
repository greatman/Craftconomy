package me.greatman.Craftconomy.commands.bank;

import org.bukkit.ChatColor;
import org.bukkit.World;

import me.greatman.Craftconomy.BankHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.Currency;
import me.greatman.Craftconomy.CurrencyHandler;
import me.greatman.Craftconomy.commands.BaseCommand;
import me.greatman.Craftconomy.utils.Config;

public class BankGiveCommand extends BaseCommand
{

	public BankGiveCommand()
	{
		this.command.add("give");
		this.requiredParameters.add("Bank Name");
		this.requiredParameters.add("Amount");
		this.optionalParameters.add("Currency");
		this.optionalParameters.add("World");
		permFlag = ("Craftconomy.bank.give");
		helpDescription = "Give money in a bank account";
	}

	public void perform()
	{
		Currency currency = CurrencyHandler.getCurrency(Config.currencyDefault, true);
		World world = player.getWorld();
		double amount;
		if (BankHandler.exists(this.parameters.get(0)))
		{
			if (Craftconomy.isValidAmount(this.parameters.get(1)))
			{
				amount = Double.parseDouble(this.parameters.get(1));
				if (this.parameters.size() == 3)
				{
					if (CurrencyHandler.exists(this.parameters.get(2), false))
					{
						currency = CurrencyHandler.getCurrency(this.parameters.get(2), false);
					}
					else
					{
						sendMessage("This currency doesn't exists!");
						return;
					}
				}
				if (this.parameters.size() == 4)
				{
					World worldtest = Craftconomy.plugin.getServer().getWorld(this.parameters.get(3));
					if (worldtest == null)
					{
						sendMessage(ChatColor.RED + "This world doesn't exists!");
						return;
					}
					else
					{
						world = worldtest;
					}

				}

				BankHandler.getBank(this.parameters.get(0)).addMoney(amount, currency, world);
				sendMessage("You added" + ChatColor.WHITE + Craftconomy.format(amount, currency) + ChatColor.GREEN
						+ " to " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.GREEN + " bank account!");
			}
			else sendMessage(ChatColor.RED + "Invalid amount!");

		}
		else sendMessage(ChatColor.RED + "This bank doesn't exists!");
	}
}
