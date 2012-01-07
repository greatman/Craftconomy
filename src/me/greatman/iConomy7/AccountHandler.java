package me.greatman.iConomy7;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.greatman.iConomy7.utils.DatabaseHandler;

import org.bukkit.entity.Player;

public class AccountHandler {

	public static List<Account> accounts = new ArrayList<Account>();
	public static Timer thread;
	class saveAccounts extends TimerTask{
		public void run() {
			for (Account playerAccount : AccountHandler.accounts) {
				DatabaseHandler.saveAccount(playerAccount.getPlayerName(),playerAccount.getBalance());
			}
		}
	}
	public AccountHandler(iConomy thePlugin)
	{
		//Initialise the auto 10s saves
		thread = new Timer();
		long time = 10 * 1000L;
		thread.scheduleAtFixedRate(new saveAccounts(), time, time);
		
	}
	
	public static Account getAccount(String player)
	{
		List<Player> playerList = iConomy.plugin.getServer().matchPlayer(player);
		if (playerList.size() == 1)
		{
			for (Account playerAccount : accounts)
			{
				if (playerAccount.getPlayerName() == playerList.get(0).getName())
					return playerAccount;
			}
		}
		for (Account playerAccount : accounts)
		{
			if (playerAccount.getPlayerName() == player)
				return playerAccount;
		}
		
		Account playerAccount = new Account(player);
		accounts.add(playerAccount);
		return playerAccount;
	}
	public static Account getAccount(Player player)
	{
		for (Account playerAccount : accounts)
		{
			if (playerAccount.getPlayerName() == player.getName())
				return playerAccount;
		}
		
		Account playerAccount = new Account(player);
		accounts.add(playerAccount);
		return playerAccount;
	}
	
	
	public static boolean exists(String player)
	{
		return DatabaseHandler.exists(player);
	}
	public static boolean exists(Player player)
	{
		return DatabaseHandler.exists(player.getName());
	}
	
}
