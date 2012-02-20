package util;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

public class CanceriousLogger {

	private static Logger logger = Logger.getLogger(CanceriousLogger.class);

	public static void debug(Object message) {
		logger.debug(message);
	}

	public static void error(Object message) {
		if(message instanceof Exception){
			logger.info(message, (Exception) message);
		}
		else{
			logger.error(message);			
		}
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void info(Object message) {
		if(message instanceof Exception){
			logger.error(message, (Exception) message);
		}
		else{
			logger.info(message);			
		}
	}

	public static void warn(Object message) {
		if(message instanceof Exception){
			logger.warn(message, (Exception) message);
		}
		else{
			logger.warn(message);
		}
		JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
	}

}
