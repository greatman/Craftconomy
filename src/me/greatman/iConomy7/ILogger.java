package me.greatman.iConomy7;

import java.util.logging.Logger;

public class ILogger {

	public static Logger log = Logger.getLogger("Minecraft");
	
	public static String prefix = "["+ iConomy.name + " " + iConomy.version + "]";
	
	
    public static void info(String message) {
        log.info(prefix + message);
    }

    public static void error(String message) {
        log.severe(prefix + message);
    }
    
    public static void severe(String message) {
        log.severe(prefix + message);
    }

    public static void warning(String message) {
        log.warning(prefix + message);
    }
}
