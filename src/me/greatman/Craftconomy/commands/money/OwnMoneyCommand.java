package me.greatman.Craftconomy.commands.money;

import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

public class OwnMoneyCommand extends BaseCommand{

	public OwnMoneyCommand() {
		this.command.add("");
		permFlag = ("Craftconomy.holdings");
		helpDescription = "Check your balance";
	}
	
	public void perform() {
		
		Account playerAccount = AccountHandler.getAccount((Player) sender);
		sendMessage("Your Balance: ");
		List<String> balance = Craftconomy.format(playerAccount.getBalance());
		Iterator<String> balanceIterator = balance.iterator();
		while (balanceIterator.hasNext())
			sendMessage(ChatColor.WHITE + balanceIterator.next());
	}
}
