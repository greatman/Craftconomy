package me.greatman.Craftconomy.commands.money;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

import org.bukkit.ChatColor;

public class SetCommand extends BaseCommand{
	
	public SetCommand() {
		this.command.add("set");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("Craftconomy.accounts.set");
		helpDescription = "Set account balance";
	}
	
	public void perform() {
		double amount;
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			if (Craftconomy.isValidAmount(this.parameters.get(1)))
			{
				amount = Double.parseDouble(this.parameters.get(1));
				receiverAccount.setBalance(amount);
				sendMessage("You set "+ receiverAccount.getPlayerName() + " account to " + Craftconomy.format(amount) + "!");
				sendMessage(receiverAccount.getPlayer(),  "Your account has been set to " + Craftconomy.format(amount) + "!");
			}
			else
				sendMessage(ChatColor.RED + "Positive number expected. Received something else.");
				
			
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.RED + " does not exists!");
	}
}
