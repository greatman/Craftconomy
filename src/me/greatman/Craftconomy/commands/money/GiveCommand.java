package me.greatman.Craftconomy.commands.money;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

import org.bukkit.ChatColor;

public class GiveCommand extends BaseCommand{
	
	public GiveCommand() {
		this.command.add("give");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("Craftconomy.accounts.give");
		helpDescription = "Give money";
	}
	
	public void perform() {
		double amount;
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			if (Craftconomy.isValidAmount(this.parameters.get(1)))
			{
				amount = Double.parseDouble(this.parameters.get(1));
				receiverAccount.addMoney(amount);
				sendMessage("You gave " + Craftconomy.format(amount) + " to " + receiverAccount.getPlayerName());
				sendMessage(receiverAccount.getPlayer(), "You received " + Craftconomy.format(amount) + "!");
			}
			else
				sendMessage(ChatColor.RED + "Positive number expected. Received something else.");
			
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + " does not exists!");
	}
}
