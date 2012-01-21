package manager;

import java.io.File;
import java.net.URISyntaxException;

import util.CanceriousLogger;

public class ConfigurationManager {

//	public File imageStore;
//	public File featureStore;
//	public File dataStore;
	
	public String imageStorePath;
	public String featureStorePath;
	public String dbStorePath;
	
//	public URL dbStoreURL, imageStoreURL, featureStoreURL;
	
	public ConfigurationManager(){
		imageStorePath = "/image_store";
		featureStorePath = "/feature_store";
		dbStorePath = "/cancerious_store";
		
//		if(dbStoreURL==null)
//			CanceriousLogger.warn("dbstore is null");
//		imageStoreURL = ConfigurationManager.class.getResource(imageStorePath);
//		featureStoreURL = ConfigurationManager.class.getResource(featureStorePath);
		
//		imageStore = new File(imageStorePath);
//		featureStore = new File(featureStorePath);
//		dataStore = new File(dbStorePath);
		
	}
	
	public File getImageAsFile(String name){
		try {
			return new File(ConfigurationManager.class.getResource(imageStorePath+"/"+name).toURI());
		} catch (Exception e) {
			CanceriousLogger.info(e);
			return null;
		}
	}
	public File getFeatureAsFile(String name){
		try {
			return new File(ConfigurationManager.class.getResource(featureStorePath+"/"+name).toURI());
		} catch (Exception e) {
			CanceriousLogger.info(e);
			return null;
		}
	}
	public File getDatabaseFileAsFile(String name){
		try {
			return new File(ConfigurationManager.class.getResource(dbStorePath+"/"+name).toURI());
		} catch (Exception e) {
			CanceriousLogger.info(e);
			return null;
		}
	}
}
