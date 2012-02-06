package me.greatman.Craftconomy.commands.bank;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.greatman.Craftconomy.BankHandler;
import me.greatman.Craftconomy.Craftconomy;
import me.greatman.Craftconomy.commands.BaseCommand;

public class BankAddCommand extends BaseCommand {

	public BankAddCommand() {
		this.command.add("add");
		this.requiredParameters.add("Bank Name");
		this.requiredParameters.add("Player Name");
		permFlag = "Craftconomy.bank.add";
		helpDescription = "Add someone to a bank account.";
	}
	
	public void perform() {
		if (BankHandler.exists(this.parameters.get(0)))
		{
			if(BankHandler.getBank(this.parameters.get(0)).getOwner().equals(sender.getName()))
			{
				List<Player> playerList = Craftconomy.plugin.getServer().matchPlayer(this.parameters.get(1));
				if (playerList.size() != 1)
				{
					sendMessage(ChatColor.RED + "Player not found or more than 1 match!");
					return;
				}
				Player player = playerList.get(0);
				BankHandler.getBank(this.parameters.get(0)).addMember(player.getName());
				sendMessage("The player " + ChatColor.WHITE + player.getName() + ChatColor.GREEN + " has been added to " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.GREEN + " bank account!");
			}
			else
				sendMessage(ChatColor.RED + "You are not the owner!");
		}
		else
			sendMessage(ChatColor.RED + "This bank doesn't exist!");
	}
}
