package main;

import manager.*;

public class CanceriousMain {

	private static CanceriousMain instance;
	
	public ConfigurationManager configurationManager;
	public EmailManager emailManager;
	public FeatureManager featureManager;
	public GraphManager graphManager;
	public GUIManager guiManager;
	
	public static CanceriousMain getInstance(){
		return instance;
	}
	
	
	public static void main(String[] args){
		instance = new CanceriousMain();
	}
	
	private CanceriousMain(){
		configurationManager = new ConfigurationManager();
		emailManager = new EmailManager();
		featureManager = new FeatureManager();
		graphManager = new GraphManager();
		guiManager = new GUIManager();
		
		guiManager.setVisible(true);
	}
}
