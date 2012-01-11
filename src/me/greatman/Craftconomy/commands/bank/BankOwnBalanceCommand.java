package me.greatman.Craftconomy.commands.bank;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

public class BankOwnBalanceCommand extends BaseCommand{

	public BankOwnBalanceCommand() {
		this.command.add("");
		permFlag = ("Craftconomy.bank.holdings");
		helpDescription = "Check your balance";
	}
	
	public void perform() {
		
		Account playerAccount = AccountHandler.getAccount((Player) sender);
		sendMessage("Bank balance: " + ChatColor.WHITE + Craftconomy.format(playerAccount.getBank().getBalance()));
	}
}
