package me.greatman.Craftconomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.utils.Config;

public class OwnMoneyCommand extends BaseCommand{

	public OwnMoneyCommand() {
		this.command.add("");
		permFlag = ("iConomy.holdings");
		helpDescription = "Check your balance";
	}
	
	public void perform() {
		
		Account playerAccount = AccountHandler.getAccount((Player) sender);
		sendMessage("Balance: " + ChatColor.WHITE + playerAccount.getBalance() + " " + Config.currencyMajorPlural);
	}
}
