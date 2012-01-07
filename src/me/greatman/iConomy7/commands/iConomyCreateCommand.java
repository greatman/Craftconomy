package me.greatman.iConomy7.commands;

import org.bukkit.ChatColor;

import me.greatman.iConomy7.AccountHandler;

public class iConomyCreateCommand extends iConomyBaseCommand{

	public iConomyCreateCommand() {
		this.command.add("create");
		this.requiredParameters.add("Player Name");
		permFlag = ("iConomy.create");
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
