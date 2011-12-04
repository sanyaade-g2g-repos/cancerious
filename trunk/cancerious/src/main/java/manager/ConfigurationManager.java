package manager;

public class ConfigurationManager {

	public String imageStorePath;
	public String featureStorePath;
	public String dbStorePath;
	
	public ConfigurationManager(){
		imageStorePath = "image_store";
		featureStorePath = "feature_store";
		dbStorePath = "neo4j_store";
	}
}
