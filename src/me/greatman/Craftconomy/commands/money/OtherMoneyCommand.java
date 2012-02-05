package me.greatman.Craftconomy.commands.money;

import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.Account;
import me.greatman.Craftconomy.AccountHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

public class OtherMoneyCommand extends BaseCommand{

	public OtherMoneyCommand() {
		permFlag = ("Craftconomy.money.holdings.others");
		this.requiredParameters.add("Player Name");
		helpDescription = "Check others balance";
	}
	
	public void perform() {
		
		if (AccountHandler.exists(parameters.get(0)))
		{
			Account playerAccount = AccountHandler.getAccount(parameters.get(0));
			sendMessage(playerAccount.getPlayerName() + " balance: ");
			List<String> balance = Craftconomy.format(playerAccount.getBalance());
			Iterator<String> balanceIterator = balance.iterator();
			while (balanceIterator.hasNext())
				sendMessage(ChatColor.WHITE + balanceIterator.next());
		}
		else
			sendMessage(ChatColor.RED + "This account doesn't exists!");
	}
}
