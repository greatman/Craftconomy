package me.greatman.Craftconomy.commands.money;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.World;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.Currency;
import me.greatman.Craftconomy.CurrencyHandler;
import me.greatman.Craftconomy.commands.BaseCommand;
import me.greatman.Craftconomy.utils.DatabaseHandler;
import me.greatman.Craftconomy.utils.Config;

public class TopCommand extends BaseCommand {

	public TopCommand() {
		this.command.add("top");
		this.optionalParameters.add("Currency Name");
		this.optionalParameters.add("World");
		this.permFlag = "Craftconomy.money.top";
		this.helpDescription = "Show the toplist";
	}
	
	public void perform() {
		Currency source;
		World world = player.getWorld();
		
		if(this.parameters.size() > 0)
			if (CurrencyHandler.exists(this.parameters.get(0), false))
			{
				source = CurrencyHandler.getCurrency(this.parameters.get(0), false);
			}
			else
			{
				sendMessage(ChatColor.RED + "Currency " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.RED
					+ " does not exist!");
				return;
			}
		else
		{
			source = CurrencyHandler.getCurrency(Config.currencyDefault, true);
		}
		if (this.parameters.size() == 2)
		{
			World worldtest = Craftconomy.plugin.getServer().getWorld(this.parameters.get(1));
			if (worldtest == null)
			{
				sendMessage(ChatColor.RED + "This world doesn't exists!");
				return;
			}
			else 
				world = worldtest;
		}
		Iterator<Account> theList = DatabaseHandler.getTopList(source,world).iterator();
		int i = 1;
		sendMessage("====== Top 10 ======");
		while (theList.hasNext())
		{
			Account account = theList.next();
			sendMessage("" + ChatColor.WHITE + i + ". " + account.getPlayerName() + ChatColor.GREEN + " with "  + ChatColor.WHITE + Craftconomy.format(account.getBalance(source, world), source));
			i++;
		}
	}
}
