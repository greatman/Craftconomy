package me.greatman.Craftconomy.commands;

import me.greatman.Craftconomy.AccountHandler;

public class EmptyCommand extends BaseCommand{
	
	public EmptyCommand() {
		this.command.add("empty");
		permFlag = ("iConomy.accounts.empty");
		helpDescription = "Empty database of accounts";
	}
	
	public void perform() {
		AccountHandler.deleteAllAccounts();
		sendMessage("All accounts has been deleted.");
	}
	

}
