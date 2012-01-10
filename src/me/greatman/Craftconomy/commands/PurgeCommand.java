package me.greatman.Craftconomy.commands;

import me.greatman.Craftconomy.AccountHandler;

public class PurgeCommand extends BaseCommand{

	
	public PurgeCommand() {
		this.command.add("purge");
		permFlag = ("iConomy.accounts.purge");
		helpDescription = "Purge all accounts with initial holdings.";
	}
	
	public void perform() {
		AccountHandler.deleteAllInitialAccounts();
		sendMessage("All accounts with the initial holdings has been deleted.");
	}
}
