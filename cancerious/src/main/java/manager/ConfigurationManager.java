package manager;

public class ConfigurationManager {

	public String imageStorePath;
	public String featureStorePath;
	public String dbStorePath;
	
	public ConfigurationManager(){
		imageStorePath = "target/classes/image_store";
		featureStorePath = "target/classes/feature_store";
		dbStorePath = "target/classes/neo4j_store";
	}
}
