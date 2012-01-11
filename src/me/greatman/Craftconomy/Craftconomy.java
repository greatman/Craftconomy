package me.greatman.Craftconomy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.greatman.Craftconomy.commands.*;
import me.greatman.Craftconomy.commands.bank.*;
import me.greatman.Craftconomy.commands.money.*;
import me.greatman.Craftconomy.listeners.CCPlayerListener;
import me.greatman.Craftconomy.utils.Config;
import me.greatman.Craftconomy.utils.DatabaseHandler;
import me.greatman.Craftconomy.utils.Metrics;

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
	public List<BaseCommand> bankCommands = new ArrayList<BaseCommand>();
	
	public CCPlayerListener playerListener = new CCPlayerListener();
	
	public static Craftconomy plugin;
	
	public void onEnable() {
		name = this.getDescription().getName();
		version = this.getDescription().getVersion();
		ILogger.info("Starting");
		Config.load(this);
		plugin = this;
		if (!DatabaseHandler.load(this))
		{
			ILogger.error("A error occured while trying to open the database. Please check your configuration.");
			onDisable();
			return;
		}
		
		new AccountHandler();
		
		//Enable the plugin stats Metrics
		try {
		    // create a new metrics object
		    Metrics metrics = new Metrics();

		    // 'this' in this context is the Plugin object
		    metrics.beginMeasuringPlugin(this);
		} catch (IOException e) {
		    ILogger.error("A error occured while starting the plugin stats");
		}
		//TODO: Help commands
		//Insert all /money commands
		//commands.add(new iConomyHelpCommand());
		commands.add(new PayCommand());
		commands.add(new CreateCommand());
		commands.add(new RemoveCommand());
		commands.add(new GiveCommand());
		commands.add(new TakeCommand());
		commands.add(new SetCommand());
		commands.add(new PurgeCommand());
		commands.add(new EmptyCommand());
		
		//Insert all /bank commands
		bankCommands.add(new BankGiveCommand());
		bankCommands.add(new BankTakeCommand());
		bankCommands.add(new BankOwnBalanceCommand());
		bankCommands.add(new BankOtherBalanceCommand());
		bankCommands.add(new BankSetCommand());
		bankCommands.add(new BankDepositCommand());
		bankCommands.add(new BankWithdrawCommand());
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener ,Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener ,Event.Priority.Normal, this);
		Player[] playerList = Craftconomy.plugin.getServer().getOnlinePlayers();
		if (playerList.length != 0)
		{
			for (int i = 0; i < playerList.length; i++)
				AccountHandler.getAccount(playerList[i]);
		}
		ILogger.info("Started!");
		
	}
	
	@Override
	public void onDisable() {
		
		if (name != null)
			name = null;
		if (version != null)
			version = null;
		if (AccountHandler.thread != null)
			AccountHandler.thread.cancel();
		commands.clear();
		ILogger.info("Iconomy unloaded!");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		List<String> parameters = new ArrayList<String>(Arrays.asList(args));
		if (cmd.getLabel().equals("money"))
			this.handleMoneyCommand(cmd, sender, parameters);
		if (cmd.getLabel().equals("bank"))
			this.handleBankCommand(cmd,sender, parameters);
		
		return true;
		
	}
	
	public void handleMoneyCommand(Command cmd, CommandSender sender, List<String> parameters) {
		if (parameters.size() == 0)
		{
			OwnMoneyCommand command = new OwnMoneyCommand();
			command.execute(sender,parameters);
			return;
		}
		String commandName = parameters.get(0);
		for (BaseCommand CraftconomyCommand : this.commands) {
			if (CraftconomyCommand.getCommands().contains(commandName)) {
				CraftconomyCommand.execute(sender, parameters);
				return;
			}
		}
		OtherMoneyCommand command = new OtherMoneyCommand();
		command.execute(sender,parameters);
	}
	
	public void handleBankCommand(Command cmd, CommandSender sender, List<String> parameters) {
		if (parameters.size() == 0)
		{
			BankOwnBalanceCommand command = new BankOwnBalanceCommand();
			command.execute(sender,parameters);
			return;
		}
		String commandName = parameters.get(0);
		for (BaseCommand CraftconomyCommand : this.bankCommands) {
			if (CraftconomyCommand.getCommands().contains(commandName)) {
				CraftconomyCommand.execute(sender, parameters);
				return;
			}
		}
		BankOtherBalanceCommand command = new BankOtherBalanceCommand();
		command.execute(sender,parameters);
	}
	
	public static String format(double amount)
	{
		//TODO: Use all the stuff we need.
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
	
	public static boolean isValidAmount(String amount)
	{
		
			try{
				double amountParsed = Double.parseDouble(amount);
				if (amountParsed > 0.00)
					return true;
				else
					return false;
			}
			catch (NumberFormatException e)
			{
				return false;
			}
	}
}
