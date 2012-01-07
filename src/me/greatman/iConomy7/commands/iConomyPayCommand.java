package me.greatman.iConomy7.commands;

import me.greatman.iConomy7.Account;
import me.greatman.iConomy7.AccountHandler;
import me.greatman.iConomy7.ILogger;
import me.greatman.iConomy7.utils.Config;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class iConomyPayCommand extends iConomyBaseCommand{

	public iConomyPayCommand() {
		this.command.add("pay");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("iConomy.payment");
		helpDescription = "Pay to someone";
	}
	
	public void perform() {
		double amount;
		if (AccountHandler.exists(this.parameters.get(0)))
		{
			Account senderAccount = AccountHandler.getAccount((Player) sender);
			Account receiverAccount = AccountHandler.getAccount(this.parameters.get(0));
			try{
				amount = Double.parseDouble(this.parameters.get(1));
			}
			catch (NumberFormatException e)
			{
				sendMessage("Number excepted as the amount. String received instead");
				return;
			}
			if (senderAccount.getBalance() < amount)
			{
				sendMessage(ChatColor.RED + "You don't have " + amount + " " + Config.currencyMajorPlural);
				return;
			}
			senderAccount.substractMoney(amount);
			receiverAccount.addMoney(amount);
			sendMessage("You sended " + amount + " " + Config.currencyMajorPlural + " to " + receiverAccount.getPlayerName());
			sendMessage(receiverAccount.getPlayer(), "You received " + amount + " " + Config.currencyMajorPlural + " from " + senderAccount.getPlayerName());
		}
	}
}
