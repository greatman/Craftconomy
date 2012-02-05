package me.greatman.Craftconomy.commands.bank;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.BankHandler;
import me.greatman.Craftconomy.commands.BaseCommand;
import me.greatman.Craftconomy.utils.Config;

public class BankCreateCommand extends BaseCommand{
	
	public BankCreateCommand() {
		this.command.add("create");
		this.requiredParameters.add("Bank Name");
		permFlag = "Craftconomy.bank.create";
		helpDescription = "Create a bank account";
	}
	
	public void perform() {
		Account account = AccountHandler.getAccount(player);
		if (account.hasEnough(Config.bankPrice))
		{
			if (BankHandler.create(this.parameters.get(0),player.getName()))
			{
				account.substractMoney(Config.bankPrice);
				sendMessage("The bank account " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.GREEN +" has been created!");
			}
			else
				sendMessage(ChatColor.RED + "A error occured or the bank already exists!");
		}
		else
			sendMessage(ChatColor.RED + "You don't have enough money! You need " + Config.bankPrice + " " + Config.bankCurrency + "!");
		
	}
	

}
