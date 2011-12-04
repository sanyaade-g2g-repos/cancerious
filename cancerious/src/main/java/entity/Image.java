package entity;

import java.io.File;
import java.util.Set;

import main.CanceriousMain;

public class Image {

	public Integer id;
	public String filename;
	public Set<FeatureValue> featureValues;
	public Set<Similarity> similarities;
	public File fileHandler;
	
	public void openHandler(){
		fileHandler = new File(CanceriousMain.getInstance().getConfigurationManager().imageStorePath+File.separator+filename);
	}
	
	public void closeHandler(){
		fileHandler = null;
	}
	
}
