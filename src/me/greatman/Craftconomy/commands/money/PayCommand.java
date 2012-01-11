package me.greatman.Craftconomy.commands.money;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PayCommand extends BaseCommand{

	public PayCommand() {
		this.command.add("pay");
		this.requiredParameters.add("Player Name");
		this.requiredParameters.add("Amount");
		permFlag = ("Craftconomy.payment");
		helpDescription = "Send money to others.";
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
				sendMessage(ChatColor.RED + "You don't have " + Craftconomy.format(amount));
				return;
			}
			senderAccount.substractMoney(amount);
			receiverAccount.addMoney(amount);
			sendMessage("You sended " + Craftconomy.format(amount) + " to " + receiverAccount.getPlayerName());
			sendMessage(receiverAccount.getPlayer(), "You received " + Craftconomy.format(amount) + " from " + senderAccount.getPlayerName());
		}
		else
			sendMessage(ChatColor.RED + "The account " + ChatColor.WHITE + this.parameters.get(0) + " does not exists!");
	}
}
