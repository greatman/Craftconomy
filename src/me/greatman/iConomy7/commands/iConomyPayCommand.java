package me.greatman.iConomy7.commands;

public class iConomyPayCommand extends iConomyBaseCommand{

	public iConomyPayCommand() {
		this.command.add("pay");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("iConomy.payment");
		helpDescription = "Pay to someone";
	}
	
	public void perform() {
		
	}
}
