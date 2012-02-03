package me.greatman.Craftconomy.commands.bank;

import me.greatman.Craftconomy.commands.BaseCommand;

public class BankOwnBalanceCommand extends BaseCommand{

	//TODO: Everything
	public BankOwnBalanceCommand()
	{
		permFlag = ("Craftconomy.bank.holdings");
		helpDescription = "Check bank balance";
	}
	
	public void perform()
	{
		sendMessage("Not implemented");
	}
}
