package me.greatman.Craftconomy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.greatman.Craftconomy.utils.DatabaseHandler;

import org.bukkit.entity.Player;

/**
 * Account handler. <b>Get a account from this class.</b>
 * 
 * @author greatman
 * 
 */
public class AccountHandler
{

	public static List<Account> accounts = new ArrayList<Account>();
	public static Timer thread;

	/**
	 * Auto-save class
	 * 
	 * @author greatman
	 * 
	 */
	class saveAccounts extends TimerTask
	{
		public void run()
		{
			List<Account> accountsToRemove = new ArrayList<Account>();
			for (Account playerAccount : accounts)
			{
				if (playerAccount.getPlayer() == null || !playerAccount.getPlayer().isOnline())
				{
					accountsToRemove.add(playerAccount);
				}

			}
			for (Account playerAccount : accountsToRemove)
			{
				accounts.remove(playerAccount);
			}
		}
	}

	public AccountHandler()
	{
		// Initialize the auto 10s saves
		thread = new Timer();
		long time = 10 * 1000L;
		thread.scheduleAtFixedRate(new saveAccounts(), time, time);

	}

	/**
	 * Get a account
	 * 
	 * @param player The player name we want to get the account
	 * @return The Account
	 */
	public static Account getAccount(String player)
	{
		player = player.toLowerCase();
		List<Player> playerList = Craftconomy.plugin.getServer().matchPlayer(player);
		if (playerList.size() == 1)
		{
			for (Account playerAccount : accounts)
			{
				if (playerAccount.getPlayerName().equals(playerList.get(0).getName().toLowerCase()))
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
		return playerAccount;
	}

	/**
	 * Get a account
	 * 
	 * @param databaseId the account we want to get from the databaseId
	 * @return The Account
	 */
	public static Account getAccount(int databaseId)
	{
		if (DatabaseHandler.getAccountNameById(databaseId) != null)
		{
			return getAccount(DatabaseHandler.getAccountNameById(databaseId));
		}
		return null;
	}

	/**
	 * Get a account
	 * 
	 * @param player The player we want to get his account
	 * @return The player account
	 */
	public static Account getAccount(Player player)
	{
		for (Account playerAccount : accounts)
		{
			if (playerAccount.getPlayerName().equals(player.getName().toLowerCase()))
				return playerAccount;
		}

		Account playerAccount = new Account(player);
		accounts.add(playerAccount);
		return playerAccount;
	}

	public static void deleteAllAccounts()
	{
		DatabaseHandler.deleteAll();
		accounts.clear();
	}

	/**
	 * Checks if a account exists
	 * 
	 * @param player The player name we want to get the account
	 * @return True if the account exists else false.
	 */
	public static boolean exists(String player)
	{
		boolean exists = false;
		player = player.toLowerCase();
		if (!DatabaseHandler.exists(player))
		{
			List<Player> playerList = Craftconomy.plugin.getServer().matchPlayer(player);
			if (playerList.size() == 1)
				exists = true;
		}
		else exists = true;
		return exists;
	}

	/**
	 * Checks if a account exists
	 * 
	 * @param player The player we want to get the account
	 * @return True if the account exists else false.
	 */
	public static boolean exists(Player player)
	{
		return DatabaseHandler.exists(player.getName().toLowerCase());
	}

	public static void deleteAllInitialAccounts()
	{
		ResultSet result = DatabaseHandler.getAllInitialAccounts();
		try
		{
			if (result.next())
			{
				do
				{
					if (accounts.contains(result.getString("username")))
						accounts.remove(result.getString("username"));
				} while (result.next());
			}
			DatabaseHandler.deleteAllInitialAccounts();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Delete a player account
	 * 
	 * @param account The account we want to delete
	 */
	public static void delete(Account account)
	{
		DatabaseHandler.delete(account.getPlayerName().toLowerCase());
		accounts.remove(account);
	}

}
