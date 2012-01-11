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
		helpDescription = "Set bank account balance";
	}
	
	public void perform() {
		
		double amount;
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			if (Craftconomy.isValidAmount(this.parameters.get(1)))
			{
				amount = Double.parseDouble(this.parameters.get(1));
				receiverAccount.getBank().setBalance(amount);
				sendMessage("You set " + ChatColor.WHITE + receiverAccount.getPlayerName() + ChatColor.GREEN + " bank account to " + ChatColor.WHITE + Craftconomy.format(amount) + ChatColor.GREEN + "!");
				sendMessage(receiverAccount.getPlayer(),  "Your bank account has been set to " + ChatColor.WHITE + Craftconomy.format(amount) + ChatColor.GREEN + "!");
			}
			else
				sendMessage(ChatColor.RED + "Positive number expected. Received something else.");
				
			
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.RED + " does not exists!");
	}
}
