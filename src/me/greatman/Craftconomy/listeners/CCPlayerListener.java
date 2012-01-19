package me.greatman.Craftconomy.listeners;

import me.greatman.Craftconomy.AccountHandler;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CCPlayerListener extends PlayerListener{

	public void onPlayerJoin(PlayerJoinEvent event)
	{
		AccountHandler.getAccount(event.getPlayer());
	}
}
