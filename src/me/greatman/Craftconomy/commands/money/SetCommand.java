package me.greatman.Craftconomy.commands.money;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.Currency;
import me.greatman.Craftconomy.CurrencyHandler;
import me.greatman.Craftconomy.commands.BaseCommand;
import me.greatman.Craftconomy.utils.Config;

import org.bukkit.ChatColor;
import org.bukkit.World;

public class SetCommand extends BaseCommand
{

	public SetCommand()
	{
		this.command.add("set");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		this.optionalParameters.add("Currency");
		this.optionalParameters.add("World");
		permFlag = ("Craftconomy.money.set");
		helpDescription = "Set account balance";
	}

	public void perform()
	{
		double amount;
		Currency currency = CurrencyHandler.getCurrency(Config.currencyDefault, true);
		World world = player.getWorld();
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			if (Craftconomy.isValidAmount(this.parameters.get(1)))
			{
				amount = Double.parseDouble(this.parameters.get(1));
				if (this.parameters.size() >= 3)
				{
					if (CurrencyHandler.exists(this.parameters.get(2), false))
					{
						currency = CurrencyHandler.getCurrency(this.parameters.get(2), false);
					}
					else
					{
						sendMessage(ChatColor.RED + "This currency doesn't exists!");
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
				receiverAccount.setBalance(amount, currency, world);
				sendMessage("You set " + ChatColor.WHITE + receiverAccount.getPlayerName() + ChatColor.GREEN
						+ " account to " + ChatColor.WHITE + Craftconomy.format(amount, currency) + "!");
				sendMessage(receiverAccount.getPlayer(), "Your account has been set to " + ChatColor.WHITE
						+ Craftconomy.format(amount, currency) + ChatColor.GREEN + "!");
			}
			else sendMessage(ChatColor.RED + "Positive number expected. Received something else.");

		}
		else sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.RED
				+ " does not exists!");
	}
}
