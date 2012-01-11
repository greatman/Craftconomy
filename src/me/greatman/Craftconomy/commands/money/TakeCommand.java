package me.greatman.Craftconomy.commands.money;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

import org.bukkit.ChatColor;

public class TakeCommand extends BaseCommand{
	
	public TakeCommand() {
		this.command.add("take");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("Craftconomy.accounts.take");
		helpDescription = "Take money";
	}
	
	public void perform() {
		double amount;
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			if (Craftconomy.isValidAmount(this.parameters.get(1)))
			{
				amount = Double.parseDouble(this.parameters.get(1));
				receiverAccount.substractMoney(amount);
				sendMessage("You removed " + ChatColor.WHITE + Craftconomy.format(amount) + ChatColor.GREEN + " from " + ChatColor.WHITE + receiverAccount.getPlayerName() + ChatColor.GREEN + " account!");
				sendMessage(receiverAccount.getPlayer(),  ChatColor.WHITE + Craftconomy.format(amount) + ChatColor.RED + " has been removed from your account!");
			}
			else
				sendMessage(ChatColor.RED + "Positive number expected. Received something else.");
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.RED + " does not exists!");
	}
}
