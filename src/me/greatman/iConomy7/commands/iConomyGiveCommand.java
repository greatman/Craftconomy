package me.greatman.iConomy7.commands;

import me.greatman.iConomy7.Account;
import me.greatman.iConomy7.AccountHandler;
import me.greatman.iConomy7.utils.Config;

import org.bukkit.ChatColor;

public class iConomyGiveCommand extends iConomyBaseCommand{
	
	public iConomyGiveCommand() {
		this.command.add("give");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("iConomy.accounts.give");
		helpDescription = "Give money";
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
			receiverAccount.addMoney(amount);
			sendMessage("You gave " + amount + " " + Config.currencyMajorPlural + " to " + receiverAccount.getPlayerName());
			sendMessage(receiverAccount.getPlayer(), "You received " + amount + " " + Config.currencyMajorPlural + "!");
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + " does not exists!");
	}
}
