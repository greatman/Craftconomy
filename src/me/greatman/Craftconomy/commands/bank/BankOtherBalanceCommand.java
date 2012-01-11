package me.greatman.Craftconomy.commands.bank;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

public class BankOtherBalanceCommand extends BaseCommand{

	public BankOtherBalanceCommand() {
		permFlag = ("Craftconomy.bank.holdings.others");
		this.requiredParameters.add("Player Name");
		helpDescription = "Check others bank balance";
	}
	
	public void perform() {
		
		if (AccountHandler.exists(parameters.get(0)))
		{
			Account playerAccount = AccountHandler.getAccount(parameters.get(0));
			sendMessage(playerAccount.getPlayerName() + " bank account: " + Craftconomy.format(playerAccount.getBank().getBalance()));
		}
		else
			sendMessage(ChatColor.RED + "This account doesn't exists!");
		
	}
}
