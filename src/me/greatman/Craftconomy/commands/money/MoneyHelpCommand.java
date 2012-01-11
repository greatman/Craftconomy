package me.greatman.Craftconomy.commands.money;

import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

public class MoneyHelpCommand extends BaseCommand{
	
	public MoneyHelpCommand() {
		this.command.add("help");
		helpDescription = "Show the help";
	}
	
	public void perform() {
		for (BaseCommand CraftconomyCommand : Craftconomy.plugin.commands) {
			if (CraftconomyCommand.hasPermission(sender))
			{
				sendMessage(CraftconomyCommand.getUseageTemplate(true));
			}
		}
	}
	

}
