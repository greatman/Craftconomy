package me.greatman.Craftconomy.commands;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.utils.Config;

import org.bukkit.ChatColor;

public class SetCommand extends BaseCommand{
	
	public SetCommand() {
		this.command.add("set");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("iConomy.accounts.set");
		helpDescription = "Set account balance";
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
				sendMessage(ChatColor.RED + "Number excepted as the amount. String received instead");
				return;
			}
			if (amount < 0.00)
			{
				sendMessage(ChatColor.RED + "A account can't have a negative value!");
			}
			receiverAccount.setBalance(amount);
			sendMessage("You set "+ receiverAccount.getPlayerName() + " account to " + amount + " " + Config.currencyMajorPlural + "!");
			sendMessage(receiverAccount.getPlayer(),  "Your account has been set to " + amount + " " + Config.currencyMajorPlural + "!");
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + " does not exists!");
	}
}
