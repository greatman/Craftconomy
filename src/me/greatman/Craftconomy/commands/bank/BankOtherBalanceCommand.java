package me.greatman.Craftconomy.commands.bank;

import me.greatman.Craftconomy.commands.BaseCommand;

public class BankOtherBalanceCommand extends BaseCommand{

	//TODO: Everything
	
	public BankOtherBalanceCommand()
	{
		permFlag = ("Craftconomy.bank.holdings.others");
		this.requiredParameters.add("Bank Name");
		helpDescription = "Check other bank balance";
	}
	
	public void perform()
	{
		sendMessage("Not implemented");
	}
}
