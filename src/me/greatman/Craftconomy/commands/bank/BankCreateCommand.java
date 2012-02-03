package me.greatman.Craftconomy.commands.bank;

import org.bukkit.ChatColor;

import me.greatman.Craftconomy.BankHandler;
import me.greatman.Craftconomy.commands.BaseCommand;

public class BankCreateCommand extends BaseCommand{
	
	public BankCreateCommand() {
		this.command.add("create");
		this.requiredParameters.add("Bank Name");
		permFlag = "Craftconomy.bank.create";
		helpDescription = "Create a bank account";
	}
	
	public void perform() {
		if (BankHandler.exists(this.parameters.get(0)))
		{
			
		}
		if (BankHandler.create(this.parameters.get(0),player.getName()))
		{
			sendMessage("The bank account " + ChatColor.WHITE + this.parameters.get(0) + ChatColor.GREEN +" has been created!");
		}
		else
			sendMessage(ChatColor.RED + "A error occured or the bank already exists!");
	}
	

}
