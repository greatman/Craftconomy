package me.greatman.Craftconomy.commands.config;

import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

public class ConfigHelpCommand extends BaseCommand
{

	public ConfigHelpCommand()
	{
		this.command.add("help");
		permFlag = ("Craftconomy.config.help");
		helpDescription = "Show the config help";
	}

	public void perform()
	{
		for (BaseCommand CraftconomyCommand : Craftconomy.plugin.configCommands)
		{
			if (CraftconomyCommand.hasPermission(sender))
			{
				sendMessage(CraftconomyCommand.getUseageTemplate(true));
			}
		}
	}
}
