package me.greatman.Craftconomy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.greatman.Craftconomy.utils.DatabaseHandler;

import org.bukkit.entity.Player;

public class AccountHandler {

	public static List<Account> accounts = new ArrayList<Account>();
	public static Timer thread;
	public static HashMap<Account,Double> saveAccountArray = new HashMap<Account, Double>();
	public static HashMap<Account,Double> saveBankArray = new HashMap<Account, Double>();
	/**
	 * Auto-save class
	 * @author greatman
	 *
	 */
	class saveAccounts extends TimerTask{
		public void run() {
			//TODO: Only if the amount changed somewhere
			//TODO: Remove player from the list if not online
			for (Account playerAccount : accounts) {
				if (saveAccountArray.get(playerAccount) != playerAccount.getBalance() || saveBankArray.get(playerAccount) != playerAccount.getBank().getBalance())
				{
					DatabaseHandler.saveAccount(playerAccount);
					saveAccountArray.remove(playerAccount);
					saveAccountArray.put(playerAccount, playerAccount.getBalance());
					saveBankArray.remove(playerAccount);
					saveBankArray.put(playerAccount, playerAccount.getBalance());
				}
				if (playerAccount.getPlayer() == null || !playerAccount.getPlayer().isOnline())	
				{
					saveAccountArray.remove(playerAccount);
					saveBankArray.remove(playerAccount);
					accounts.remove(playerAccount);
				}
					
			}
		}
	}
	
	public AccountHandler()
	{
		//Initialize the auto 10s saves
		thread = new Timer();
		long time = 10 * 1000L;
		thread.scheduleAtFixedRate(new saveAccounts(), time, time);
		
	}
	
	/**
	 * Get a account
	 * @param player The player name we want to get the account
	 * @return The Account
	 */
	public static Account getAccount(String player)
	{
		List<Player> playerList = Craftconomy.plugin.getServer().matchPlayer(player);
		if (playerList.size() == 1)
		{
			for (Account playerAccount : accounts)
			{
				if (playerAccount.getPlayerName().equals(playerList.get(0).getName()))
					return playerAccount;
			}
		}
		for (Account playerAccount : accounts)
		{
			if (playerAccount.getPlayerName().equals(player))
				return playerAccount;
		}
		Account playerAccount = new Account(player);
		accounts.add(playerAccount);
		saveAccountArray.put(playerAccount, playerAccount.getBalance());
		saveBankArray.put(playerAccount, playerAccount.getBank().getBalance());
		return playerAccount;
	}
	
	/**
	 * Get a account
	 * @param player The player we want to get the account
	 * @return The Account
	 */
	public static Account getAccount(Player player)
	{
		for (Account playerAccount : accounts)
		{
			if (playerAccount.getPlayerName().equals(player.getName()))
				return playerAccount;
		}
		
		Account playerAccount = new Account(player);
		accounts.add(playerAccount);
		saveAccountArray.put(playerAccount, playerAccount.getBalance());
		saveBankArray.put(playerAccount, playerAccount.getBank().getBalance());
		return playerAccount;
	}
	
	public static void deleteAllAccounts() {
		DatabaseHandler.deleteAll();
		accounts.clear();
	}
	/**
	 * Checks if a account exists
	 * @param player The player name we want to get the account
	 * @return True if the account exists else false.
	 */
	public static boolean exists(String player)
	{
		return DatabaseHandler.exists(player);
	}
	/**
	 * Checks if a account exists
	 * @param player The player we want to get the account
	 * @return True if the account exists else false.
	 */
	public static boolean exists(Player player)
	{
		return DatabaseHandler.exists(player.getName());
	}

	public static void deleteAllInitialAccounts() {
		ResultSet result = DatabaseHandler.getAllInitialAccounts();
		try {
			if (result.next())
			{
				do
				{
					if (accounts.contains(result.getString("username")))
						accounts.remove(result.getString("username"));
				}
				while(result.next());
			}
			DatabaseHandler.deleteAllInitialAccounts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void delete(Account account) {
		DatabaseHandler.delete(account.getPlayerName());
		accounts.remove(account);
		saveAccountArray.remove(account);
		saveBankArray.remove(account);
	}
	
	public static void save(Player player)
	{
		Account playerAccount = getAccount(player);
		DatabaseHandler.saveAccount(playerAccount);
		accounts.remove(playerAccount);
		saveAccountArray.remove(playerAccount);
		saveBankArray.remove(playerAccount);
	}
	
}
