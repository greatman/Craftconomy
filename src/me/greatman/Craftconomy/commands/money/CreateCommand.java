package me.greatman.Craftconomy.commands.money;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.commands.BaseCommand;

public class CreateCommand extends BaseCommand{

	public CreateCommand() {
		this.command.add("create");
		this.requiredParameters.add("Player Name");
		permFlag = ("Craftconomy.create");
		helpDescription = "Create a account";
	}
	
	public void perform() {
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			sendMessage(ChatColor.RED + "This user already exists!");
			return;
		}
		AccountHandler.getAccount(this.parameters.get(0));
		sendMessage("Account created!");
	}
}
