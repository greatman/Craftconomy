package me.greatman.iConomy7;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.greatman.iConomy7.utils.DatabaseHandler;

import org.bukkit.entity.Player;

public class AccountHandler {

	public static List<Account> accounts = new ArrayList<Account>();
	
	public AccountHandler(iConomy thePlugin)
	{
		//Initialise the auto 10s saves
		Timer thread = new Timer();
		Long time = 10 * 1000L;
		thread.scheduleAtFixedRate(saveAccounts(), time, time);
	}
	private TimerTask saveAccounts() {
			for (Account playerAccount : accounts) {
				DatabaseHandler.saveAccount(playerAccount.getPlayerName(),playerAccount.getBalance());
			}
		return null;
	}
	public static Account getAccount(Player player)
	{
		Account playerAccount = new Account(player);
		accounts.add(playerAccount);
		return playerAccount;
	}
	
}
