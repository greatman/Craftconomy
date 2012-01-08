package me.greatman.iConomy7.commands;

import me.greatman.iConomy7.AccountHandler;

public class iConomyPurgeCommand extends iConomyBaseCommand{

	
	public iConomyPurgeCommand() {
		this.command.add("purge");
		permFlag = ("iConomy.accounts.purge");
		helpDescription = "Purge all accounts with initial holdings.";
	}
	
	public void perform() {
		AccountHandler.deleteAllInitialAccounts();
		sendMessage("All accounts with the initial holdings has been deleted.");
	}
}
