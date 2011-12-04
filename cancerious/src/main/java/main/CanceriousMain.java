package main;

import javax.swing.UIManager;
import util.CanceriousLogger;

import manager.*;

public class CanceriousMain {

	private static CanceriousMain instance;
	
	private static ConfigurationManager configurationManager;
	private static EmailManager emailManager;
	private static FeatureManager featureManager;
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
		featureManager = new FeatureManager();
		graphManager = new GraphManager();
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

	public static FeatureManager getFeatureManager() {
		return featureManager;
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
