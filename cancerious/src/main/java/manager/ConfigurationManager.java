package manager;

import java.net.URL;

public class ConfigurationManager {

//	public File imageStore;
//	public File featureStore;
//	public File dataStore;
	
	private String imageStorePath;
	private String featureStorePath;
	private String dbStorePath;
	
	public URL dbStoreURL, imageStoreURL, featureStoreURL;
	
	public ConfigurationManager(){
		imageStorePath = "/image_store";
		featureStorePath = "/feature_store";
		dbStorePath = "/cancerious_store";
		
		dbStoreURL = ConfigurationManager.class.getResource(dbStorePath);
		imageStoreURL = ConfigurationManager.class.getResource(imageStorePath);
		featureStoreURL = ConfigurationManager.class.getResource(featureStorePath);
		
//		imageStore = new File(imageStorePath);
//		featureStore = new File(featureStorePath);
//		dataStore = new File(dbStorePath);
		
	}
}
