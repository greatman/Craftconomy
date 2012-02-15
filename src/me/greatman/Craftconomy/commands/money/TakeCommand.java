package me.greatman.Craftconomy.commands.money;

import java.math.BigDecimal;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.Currency;
import me.greatman.Craftconomy.CurrencyHandler;
import me.greatman.Craftconomy.commands.BaseCommand;
import me.greatman.Craftconomy.utils.Config;

import org.bukkit.ChatColor;
import org.bukkit.World;

public class TakeCommand extends BaseCommand
{

	public TakeCommand()
	{
		this.command.add("take");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		this.optionalParameters.add("Currency");
		this.optionalParameters.add("World");
		permFlag = ("Craftconomy.money.take");
		helpDescription = "Take money";
	}

	public void perform()
	{
		BigDecimal amount;
		Currency currency = CurrencyHandler.getCurrency(Config.currencyDefault, true);
		
		World world = player.getWorld();
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			if (Craftconomy.isValidAmount(this.parameters.get(1)))
			{
				amount = new BigDecimal(this.parameters.get(1));
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
					else world = worldtest;
				}
				receiverAccount.substractMoney(amount, world);
				sendMessage("You removed " + ChatColor.WHITE + Craftconomy.format(amount, currency) + ChatColor.GREEN
						+ " from " + ChatColor.WHITE + receiverAccount.getPlayerName() + ChatColor.GREEN + " account!");
				sendMessage(receiverAccount.getPlayer(), ChatColor.WHITE + Craftconomy.format(amount, currency)
						+ ChatColor.RED + " has been removed from your account!");
			}
			else sendMessage(ChatColor.RED + "Positive number expected. Received something else.");
		}
		else sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.RED
				+ " does not exists!");
	}
}
