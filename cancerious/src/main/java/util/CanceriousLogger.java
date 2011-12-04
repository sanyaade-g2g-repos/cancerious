package util;

import org.apache.log4j.Logger;

public class CanceriousLogger {

	private static Logger logger = Logger.getLogger(CanceriousLogger.class);

	public static void debug(Object message) {
		logger.debug(message);
	}

	public static void error(Object message) {
		logger.error(message);
	}

	public static void info(Object message) {
		logger.info(message);
	}

	public static void warn(Object message) {
		logger.warn(message);
	}
	
}
