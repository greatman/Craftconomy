package me.greatman.Craftconomy.commands.bank;

import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.Bank;
import me.greatman.Craftconomy.BankHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

public class BankOtherBalanceCommand extends BaseCommand{

	//TODO: Everything
	
	public BankOtherBalanceCommand()
	{
		permFlag = ("Craftconomy.bank.holdings.others");
		this.requiredParameters.add("Bank Name");
		helpDescription = "Check other bank balance";
	}
	
	public void perform()
	{
		if (BankHandler.exists(this.parameters.get(0)))
		{
			Bank bank = BankHandler.getBank(this.parameters.get(0));
			if (bank.getOwner().equals(player.getName()))
			{
				sendMessage("Bank " + this.parameters.get(0) + ChatColor.GREEN + " status:");
				List<String> balance = Craftconomy.format(bank.getBalance());
				Iterator<String> balanceIterator = balance.iterator();
				while (balanceIterator.hasNext())
					sendMessage(ChatColor.WHITE + balanceIterator.next());
			}
			else
				sendMessage(ChatColor.RED + "You do not have access to that bank!");
		}
		else
			sendMessage(ChatColor.RED + "This bank doesn't exist!");
	}
}
