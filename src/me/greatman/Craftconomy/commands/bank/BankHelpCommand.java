package me.greatman.Craftconomy.commands.bank;

import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

public class BankHelpCommand extends BaseCommand{
	
	public BankHelpCommand() {
		this.command.add("help");
		permFlag = "Craftconomy.bank.help";
		helpDescription = "Show the help";
	}
	
	public void perform() {
		for (BaseCommand CraftconomyCommand : Craftconomy.plugin.bankCommands) {
			if (CraftconomyCommand.hasPermission(sender))
			{
				sendMessage(CraftconomyCommand.getUseageTemplate(true));
			}
		}
	}
	

}
