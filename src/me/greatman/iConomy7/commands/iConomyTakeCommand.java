package me.greatman.iConomy7.commands;

import me.greatman.iConomy7.Account;
import me.greatman.iConomy7.AccountHandler;
import me.greatman.iConomy7.utils.Config;

import org.bukkit.ChatColor;

public class iConomyTakeCommand extends iConomyBaseCommand{
	
	public iConomyTakeCommand() {
		this.command.add("take");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("iConomy.accounts.take");
		helpDescription = "Take money";
	}
	
	public void perform() {
		double amount;
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			try{
				amount = Double.parseDouble(this.parameters.get(1));
			}
			catch (NumberFormatException e)
			{
				sendMessage("Number excepted as the amount. String received instead");
				return;
			}
			receiverAccount.substractMoney(amount);
			sendMessage("You removed " + amount + " " + Config.currencyMajorPlural + " from " + receiverAccount.getPlayerName() + " account!");
			sendMessage(receiverAccount.getPlayer(),  "" + ChatColor.RED + amount + " " + Config.currencyMajorPlural + " has been removed from your account!");
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + " does not exists!");
	}
}
