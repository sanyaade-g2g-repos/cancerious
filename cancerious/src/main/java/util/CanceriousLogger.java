package util;

import org.apache.log4j.Logger;

public class CanceriousLogger {

	private static Logger logger = Logger.getLogger(CanceriousLogger.class);

	public void debug(Object message) {
		logger.debug(message);
	}

	public void error(Object message) {
		logger.error(message);
	}

	public void info(Object message) {
		logger.info(message);
	}

	public void warn(Object message) {
		logger.warn(message);
	}
	
}
