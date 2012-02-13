package me.greatman.Craftconomy.commands.money;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.commands.BaseCommand;

public class EmptyCommand extends BaseCommand
{

	public EmptyCommand()
	{
		this.command.add("empty");
		permFlag = ("Craftconomy.accounts.empty");
		helpDescription = "Empty database of accounts";
	}

	public void perform()
	{
		AccountHandler.deleteAllAccounts();
		sendMessage(ChatColor.RED + "All accounts has been deleted.");
	}

}
