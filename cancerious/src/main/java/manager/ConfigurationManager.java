package manager;

import java.io.File;

public class ConfigurationManager {

	public File imageStore;
	public File featureStore;
	public File dataStore;
	
	private String imageStorePath;
	private String featureStorePath;
	private String dbStorePath;
	
	public ConfigurationManager(){
		imageStorePath = "target/classes/image_store";
		featureStorePath = "target/classes/feature_store";
		dbStorePath = "target/classes/cancerious_store";
		
		imageStore = new File(imageStorePath);
		featureStore = new File(featureStorePath);
		dataStore = new File(dbStorePath);
		
	}
}
