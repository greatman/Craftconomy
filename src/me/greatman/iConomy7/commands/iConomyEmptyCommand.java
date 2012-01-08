package me.greatman.iConomy7.commands;

import me.greatman.iConomy7.AccountHandler;

public class iConomyEmptyCommand extends iConomyBaseCommand{
	
	public iConomyEmptyCommand() {
		this.command.add("empty");
		permFlag = ("iConomy.accounts.empty");
		helpDescription = "Empty database of accounts";
	}
	
	public void perform() {
		AccountHandler.deleteAllAccounts();
		sendMessage("All accounts has been deleted.");
	}
	

}
