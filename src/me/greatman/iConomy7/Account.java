package me.greatman.iConomy7;

import me.greatman.iConomy7.utils.DatabaseHandler;

import org.bukkit.entity.Player;

public class Account {

	private String playerName;
	private double amount;
	public Account(Player user){
		playerName = user.getName();
		amount = DatabaseHandler.getAccountAmount(playerName);
	}
}
