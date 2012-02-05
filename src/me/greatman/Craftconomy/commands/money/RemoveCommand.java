package me.greatman.Craftconomy.commands.money;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.commands.BaseCommand;

import org.bukkit.ChatColor;

public class RemoveCommand extends BaseCommand{
	
	public RemoveCommand() {
		this.command.add("remove");
		this.requiredParameters.add("Player Name");
		permFlag = ("Craftconomy.account.remove");
		helpDescription = "Remove an account.";
	}
	
	public void perform() {
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			AccountHandler.delete(receiverAccount);
			sendMessage(ChatColor.WHITE + receiverAccount.getPlayerName() + ChatColor.RED + " account has been deleted!");
			sendMessage(receiverAccount.getPlayer(),  "Your account has been deleted!");
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.RED + " does not exists!");
	}
}
