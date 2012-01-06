package me.greatman.iConomy7.listeners;

import me.greatman.iConomy7.AccountHandler;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class iConomyPlayerListener extends PlayerListener{

	public void onPlayerJoin(PlayerJoinEvent event)
	{
		AccountHandler.getAccount(event.getPlayer());
	}
}
