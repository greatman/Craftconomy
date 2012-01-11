package me.greatman.Craftconomy.commands.money;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

public class OtherMoneyCommand extends BaseCommand{

	public OtherMoneyCommand() {
		permFlag = ("Craftconomy.holdings.others");
		this.requiredParameters.add("Player Name");
		helpDescription = "Check others balance";
	}
	
	public void perform() {
		
		if (AccountHandler.exists(parameters.get(0)))
		{
			Account playerAccount = AccountHandler.getAccount(parameters.get(0));
			sendMessage(ChatColor.WHITE + playerAccount.getPlayerName() + ChatColor.GREEN + " account: " + ChatColor.WHITE + Craftconomy.format(playerAccount.getBalance()));
		}
		else
			sendMessage(ChatColor.RED + "This account doesn't exists!");
	}
}