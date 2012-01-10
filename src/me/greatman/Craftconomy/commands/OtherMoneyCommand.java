package me.greatman.Craftconomy.commands;

import org.bukkit.entity.Player;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.utils.Config;

public class OtherMoneyCommand extends BaseCommand{

	public OtherMoneyCommand() {
		permFlag = ("iConomy.holdings.others");
		this.requiredParameters.add("Player Name");
		helpDescription = "Check others balance";
	}
	
	public void perform() {
		
		if (AccountHandler.exists(parameters.get(0)))
		{
			Account playerAccount = AccountHandler.getAccount((Player) sender);
			sendMessage(playerAccount.getPlayerName() + " account: " + playerAccount.getBalance() + " " + Config.currencyMajorPlural);
		}
		
	}
}
