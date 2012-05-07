package main;

import javax.swing.UIManager;

import manager.ConfigurationManager;
import manager.EmailManager;
import manager.GUIManager;
import manager.GraphManager;
import util.CanceriousLogger;

public class CanceriousMain {

	private static CanceriousMain instance;

	private static ConfigurationManager configurationManager;
	private static EmailManager emailManager;
	private static GraphManager graphManager;
	private static GUIManager guiManager;

	private CanceriousMain(){
		CanceriousLogger.info("Initializing cancerious...");
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		configurationManager = new ConfigurationManager();
		emailManager = new EmailManager();
		graphManager = new GraphManager();
		graphManager.init();
		guiManager = new GUIManager();
	}

	public static CanceriousMain getInstance(){
		return instance;
	}

	public static ConfigurationManager getConfigurationManager() {
		return configurationManager;
	}

	public static EmailManager getEmailManager() {
		return emailManager;
	}

	public static GraphManager getGraphManager() {
		return graphManager;
	}

	public static GUIManager getGuiManager() {
		return guiManager;
	}

	public static void main(String[] args){
		instance = new CanceriousMain();
	}
}
