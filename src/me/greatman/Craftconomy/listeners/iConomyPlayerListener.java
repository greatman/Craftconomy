package me.greatman.Craftconomy.listeners;

import me.greatman.Craftconomy.AccountHandler;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class iConomyPlayerListener extends PlayerListener{

	public void onPlayerJoin(PlayerJoinEvent event)
	{
		AccountHandler.getAccount(event.getPlayer());
	}
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		AccountHandler.save(event.getPlayer());
	}
}
