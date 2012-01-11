package me.greatman.Craftconomy.commands.bank;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

import org.bukkit.ChatColor;

public class BankSetCommand extends BaseCommand{
	
	public BankSetCommand() {
		this.command.add("set");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("Craftconomy.banks.set");
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
				sendMessage("You set "+ receiverAccount.getPlayerName() + " bank account to " + Craftconomy.format(amount) + "!");
				sendMessage(receiverAccount.getPlayer(),  "Your bank account has been set to " + Craftconomy.format(amount) + "!");
			}
			else
				sendMessage(ChatColor.RED + "Positive number expected. Received something else.");
				
			
		}
		else
			sendMessage(ChatColor.RED + "The bank account " + ChatColor.WHITE + this.parameters.get(0) + " does not exists!");
	}
}
