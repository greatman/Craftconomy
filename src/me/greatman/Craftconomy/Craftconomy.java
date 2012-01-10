package me.greatman.Craftconomy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.greatman.Craftconomy.commands.*;
import me.greatman.Craftconomy.listeners.iConomyPlayerListener;
import me.greatman.Craftconomy.utils.Config;
import me.greatman.Craftconomy.utils.DatabaseHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Craftconomy extends JavaPlugin{

	public static String 	name,
							version;
	public List<BaseCommand> commands = new ArrayList<BaseCommand>();
	
	public iConomyPlayerListener playerListener = new iConomyPlayerListener();
	
	public static Craftconomy plugin;
	
	public void onEnable() {
		name = this.getDescription().getName();
		version = this.getDescription().getVersion();
		Config.load(this);
		plugin = this;
		if (!DatabaseHandler.load(this))
		{
			ILogger.error("A error occured while trying to open the database. Please check your configuration.");
			onDisable();
			return;
		}
		
		new AccountHandler();
		//commands.add(new iConomyHelpCommand());
		commands.add(new PayCommand());
		commands.add(new CreateCommand());
		commands.add(new RemoveCommand());
		commands.add(new GiveCommand());
		commands.add(new TakeCommand());
		commands.add(new SetCommand());
		commands.add(new PurgeCommand());
		commands.add(new EmptyCommand());
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener ,Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener ,Event.Priority.Normal, this);
		Player[] playerList = Craftconomy.plugin.getServer().getOnlinePlayers();
		if (playerList.length != 0)
		{
			for (int i = 0; i < playerList.length; i++)
				AccountHandler.getAccount(playerList[i]);
		}
		
	}
	
	@Override
	public void onDisable() {
		
		//Nulling every variables
		name = null;
		version = null;
		AccountHandler.thread.cancel();
		commands.clear();
		ILogger.info("Iconomy unloaded!");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		List<String> parameters = new ArrayList<String>(Arrays.asList(args));
		if (cmd.getLabel().equals("money"))
		{
			this.handleCommand(cmd, sender, parameters);
		}
			
		
		return true;
		
	}
	
	public void handleCommand(Command cmd, CommandSender sender, List<String> parameters) {
		if (parameters.size() == 0)
		{
			OwnMoneyCommand command = new OwnMoneyCommand();
			command.execute(sender,parameters);
			return;
		}
		String commandName = parameters.get(0);
		for (BaseCommand iConomyCommand : this.commands) {
			if (iConomyCommand.getCommands().contains(commandName)) {
				iConomyCommand.execute(sender, parameters);
				return;
			}
		}
		OtherMoneyCommand command = new OtherMoneyCommand();
		command.execute(sender,parameters);
	}
	
	public static String format(double amount)
	{
		String result;
		if (amount >= 2)
		{
			result = amount + " " + Config.currencyMajorPlural;
		}
		else
		{
			result = amount + " " + Config.currencyMajorSingle;
		}
		return result;
	}
}