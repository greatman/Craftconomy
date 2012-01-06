package me.greatman.iConomy7;

import CHBaseCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.greatman.iConomy7.commands.*;
import me.greatman.iConomy7.utils.Config;
import me.greatman.iConomy7.utils.DatabaseHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class iConomy extends JavaPlugin{

	public static String 	name,
							version;
	public List<iConomyBaseCommand> commands = new ArrayList<iConomyBaseCommand>();
	
	public void onEnable() {
		name = this.getDescription().getName();
		version = this.getDescription().getVersion();
		
		Config.load(this);
		
		if (!DatabaseHandler.load())
		{
			ILogger.error("A error occured while trying to open the database. Please check your configuration.");
			onDisable();
			return;
		}
		commands.add("help", new iConomyHelpCommand());
		commands.add("pay", new iConomyPayCommand());
		commands.add("create", new iConomyCreateCommand());
		commands.add("remove", new iConomyRemoveCommand());
		commands.add("give", new iConomyGiveCommand());
		commands.add("take", new iConomyTakeCommand());
		commands.add("set", new iConomySetCommand());
		commands.add("status", new iConomySetCommand());
		commands.add("purge", new iConomyPurgeCommand());
		commands.add("empty", new iConomyEmptyCommand());
	}
	
	@Override
	public void onDisable() {
		
		//Nulling every variables
		name = null;
		version = null;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		List<String> parameters = new ArrayList<String>(Arrays.asList(args));
		
		if (cmd.equals("money"))
			this.handleCommand(cmd, sender, parameters);
		
		return true;
		
	}
	
	public void handleCommand(Command cmd, CommandSender sender, List<String> parameters) {
		String commandName = cmd.getName();
		for (iConomyBaseCommand iConomyCommand : this.commands) {
			
			if (iConomyCommand.getCommands().contains(commandName)) {
				iConomyCommand.execute(sender, parameters);
				return;
			}
		}
		
		sender.sendMessage(ChatColor.YELLOW+"Unknown command \""+commandName+"\".");
	}
}
