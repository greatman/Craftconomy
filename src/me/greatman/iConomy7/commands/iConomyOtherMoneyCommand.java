package me.greatman.iConomy7.commands;

import org.bukkit.entity.Player;

import me.greatman.iConomy7.Account;
import me.greatman.iConomy7.AccountHandler;
import me.greatman.iConomy7.utils.Config;

public class iConomyOtherMoneyCommand extends iConomyBaseCommand{

	public iConomyOtherMoneyCommand() {
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
