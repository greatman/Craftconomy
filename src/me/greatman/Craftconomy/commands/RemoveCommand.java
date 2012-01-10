package me.greatman.Craftconomy.commands;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;

import org.bukkit.ChatColor;

public class RemoveCommand extends BaseCommand{
	
	public RemoveCommand() {
		this.command.add("set");
		this.requiredParameters.add("Player Name");
		permFlag = ("iConomy.accounts.remove");
		helpDescription = "Remove an account.";
	}
	
	public void perform() {
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			AccountHandler.delete(receiverAccount);
			sendMessage(ChatColor.RED + receiverAccount.getPlayerName() + " account has been deleted!");
			sendMessage(receiverAccount.getPlayer(),  "Your account has been deleted!");
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + " does not exists!");
	}
}
