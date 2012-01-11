package me.greatman.Craftconomy.commands.money;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.commands.BaseCommand;

public class PurgeCommand extends BaseCommand{

	
	public PurgeCommand() {
		this.command.add("purge");
		permFlag = ("Craftconomy.accounts.purge");
		helpDescription = "Purge all accounts with initial holdings.";
	}
	
	public void perform() {
		AccountHandler.deleteAllInitialAccounts();
		sendMessage(ChatColor.RED + "All accounts with the initial holdings has been deleted.");
	}
}
