package me.greatman.Craftconomy.commands.bank;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;
import org.bukkit.ChatColor;

public class BankTakeCommand extends BaseCommand{
	
	public BankTakeCommand() {
		this.command.add("take");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("Craftconomy.banks.take");
		helpDescription = "Take money from a bank account";
	}
	
	public void perform() {
		double amount;
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			if (Craftconomy.isValidAmount(this.parameters.get(1)))
			{
				amount = Double.parseDouble(this.parameters.get(1));
				receiverAccount.getBank().substractMoney(amount);
				sendMessage("You removed " + Craftconomy.format(amount) + " from " + receiverAccount.getPlayerName() + " bank account!");
				sendMessage(receiverAccount.getPlayer(),  "" + ChatColor.RED + Craftconomy.format(amount) + " has been removed from your bank account!");
			}
			else
				sendMessage(ChatColor.RED + "Positive number expected. Received something else.");
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.RED + " does not exists!");
	}
}
