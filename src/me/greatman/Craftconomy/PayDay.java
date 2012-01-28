package me.greatman.Craftconomy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import me.greatman.Craftconomy.utils.PayDayConfig;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PayDay extends TimerTask {

	private String groupName;
	private double amount;
	private Currency currency;
	private World world;
	private String master;
	private String type;
	public PayDay(String group)
	{
		groupName = group;
		amount = PayDayConfig.getAmount(groupName);
		currency = PayDayConfig.getCurrency(groupName);
		world = PayDayConfig.getWorld(groupName);
		master = PayDayConfig.whoPay(groupName);
		type = PayDayConfig.getType(groupName);
	}
	@Override
	public void run() {
		List<Player> theList = new ArrayList<Player>();
		if (!master.equals("none"))
		{
			if (!AccountHandler.exists(master))
			{
				ILogger.error("The player " + master + " in the payday config for group " + groupName + " doesn't exists! Can't continue!");
				return;
			}
			
		}
		Player[] playerList = Craftconomy.plugin.getServer().getOnlinePlayers();
		for (int i = 0; i < playerList.length; i++)
		{
			if (playerList[i].hasPermission("Craftconomy.payday." + groupName))
			{
				theList.add(playerList[i]);
			}
			
		}
		if (playerList.length > 0)
		{
			Player player;
			if (!master.equals("none"))
			{
				
				if (type.equalsIgnoreCase("wage"))
				{
					Iterator<Player> giveMoneyList = theList.iterator();
					if (AccountHandler.getAccount(master).hasEnough(amount * theList.size(), currency, world))
					{
						
						AccountHandler.getAccount(master).substractMoney(amount * theList.size(), currency, world);
						paydayIterator(giveMoneyList);
					}
					else
					{
						while (giveMoneyList.hasNext())
						{
							player = giveMoneyList.next();
							player.sendMessage(ChatColor.RED + "You cannot have your paycheck! The player that gives them don't have enough money.");
						}
					}
				}
				else if (type.equalsIgnoreCase("tax"))
				{
					Account masterAccount = AccountHandler.getAccount(master);
					Iterator<Player> giveMoneyList = theList.iterator();
					while (giveMoneyList.hasNext())
					{
						player = giveMoneyList.next();
						Account account = AccountHandler.getAccount(player);
						if (account.hasEnough(amount,currency,world))
						{
							account.substractMoney(amount,currency,world);
							masterAccount.addMoney(amount,currency,world);
							player.sendMessage(ChatColor.GREEN + "Tax: you paid " + Craftconomy.format(amount, currency));
						}
						else
						{
							player.sendMessage(ChatColor.RED + "You were not able to pay your taxes!");
							if (masterAccount.getPlayer() != null)
							{
								masterAccount.getPlayer().sendMessage(ChatColor.RED + player.getDisplayName() + " was not able to pay his taxes!");
							}
						}
					}
				}
			}
			else
			{
				if (type.equalsIgnoreCase("wage"))
				{
					Iterator<Player> giveMoneyList = theList.iterator();
					paydayIterator(giveMoneyList);
				}
				else if (type.equalsIgnoreCase("tax"))
				{
					Iterator<Player> giveMoneyList = theList.iterator();
					while (giveMoneyList.hasNext())
					{
						player = giveMoneyList.next();
						Account account = AccountHandler.getAccount(player);
						if (account.hasEnough(amount,currency,world))
						{
							account.substractMoney(amount,currency,world);
							player.sendMessage(ChatColor.GREEN + "Tax: you paid " + Craftconomy.format(amount, currency));
						}
						else
						{
							player.sendMessage(ChatColor.RED + "You were not able to pay your taxes!");
						}
					}
				}
			}
		}
	}
	
	public void paydayIterator(Iterator<Player> giveMoneyList)
	{
		Player player;
		while (giveMoneyList.hasNext())
		{
			player = giveMoneyList.next();
			AccountHandler.getAccount(player).addMoney(amount,currency,world);
			player.sendMessage(ChatColor.GREEN + "It's payday! You received " + ChatColor.WHITE + Craftconomy.format(amount, currency));
		}
	}
}
