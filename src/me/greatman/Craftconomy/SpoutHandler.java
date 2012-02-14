package me.greatman.Craftconomy;

import java.util.TimerTask;

import me.greatman.Craftconomy.utils.Config;

import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SpoutHandler extends TimerTask
{
	private SpoutPlayer player = null;
	private GenericLabel label = null;
	public SpoutHandler(SpoutPlayer thePlayer)
	{
		player = thePlayer;
		label = new GenericLabel();
		updateText();
		player.getMainScreen().attachWidget(Craftconomy.plugin, label);
	}
	@Override
	public void run()
	{
		updateText();
		
	}
	public void updateText()
	{
		label.setX(0).setY(0).setWidth(400).setHeight(10);
		label.setText("You have " + Craftconomy.format(AccountHandler.getAccount(player).getDefaultBalance(),CurrencyHandler.getCurrency(Config.currencyDefault, true)));
		label.setAlign(WidgetAnchor.TOP_LEFT);
		label.setAnchor(WidgetAnchor.TOP_LEFT);
		label.setAuto(true);
	}
}
