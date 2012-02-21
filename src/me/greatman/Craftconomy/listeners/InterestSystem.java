package me.greatman.Craftconomy.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import me.greatman.Craftconomy.Currency;
import me.greatman.Craftconomy.CurrencyHandler;
import me.greatman.Craftconomy.ILogger;
import me.greatman.Craftconomy.utils.Config;

/**
 * Somehow an interest "listener"
 * @author steffengy
 *
 */
public class InterestSystem extends TimerTask {
	private HashMap<String, Integer> doneInterval = new HashMap<String, Integer>();
	@Override
	public void run() {
		String[] currency_tmp = Config.interestCurrencys.toArray(new String[0]);
		List<Currency> currencys = new ArrayList<Currency>();
		for(String currency_ : currency_tmp) {
			Currency tmp;
			if((tmp = CurrencyHandler.getCurrency(currency_, true)) == null) {
				ILogger.error(currency_ + " was not found by the InterestSystem.");
				return;
			}
			currencys.add(tmp);
		}
		List<Double> percentages = Config.interestPercentage;
		List<Double> endMax = Config.interestEndMax;
		//check if an (account/)currency is ready
		List<Integer> interval = Config.interestInterval;
		int currencyCounter = 0;
		for(Currency item : currencys) {
			if(doneInterval.containsKey(item.getName())) {
				if(doneInterval.get(item.getName()) >= interval.get(currencyCounter)) {
					//Loop through all users and add cash.
					
					doneInterval.put(item.getName(), 0);
				}else {
					doneInterval.put(item.getName(), doneInterval.get(item.getName()) + 1);
				}
			} else {
				doneInterval.put(item.getName(), 0);
			}
			currencyCounter++;
		}
	}

}
