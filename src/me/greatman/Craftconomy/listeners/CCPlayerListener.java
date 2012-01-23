package me.greatman.Craftconomy.listeners;

import me.greatman.Craftconomy.AccountHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CCPlayerListener implements Listener{

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		AccountHandler.getAccount(event.getPlayer());
	}
}
