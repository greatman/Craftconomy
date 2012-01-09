package me.greatman.iConomy7.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.greatman.iConomy7.Account;
import me.greatman.iConomy7.AccountHandler;
import me.greatman.iConomy7.utils.Config;

public class iConomyOwnMoneyCommand extends iConomyBaseCommand{

	public iConomyOwnMoneyCommand() {
		this.command.add("");
		permFlag = ("iConomy.holdings");
		helpDescription = "Check your balance";
	}
	
	public void perform() {
		
		Account playerAccount = AccountHandler.getAccount((Player) sender);
		sendMessage("Balance: " + ChatColor.WHITE + playerAccount.getBalance() + " " + Config.currencyMajorPlural);
	}
}
