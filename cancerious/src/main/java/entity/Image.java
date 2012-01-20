package entity;

import java.io.File;
import java.util.Set;

import main.CanceriousMain;

public class Image {

	public Integer id;
	public String filename;
	public Set<FeatureValue> featureValues;
	public File fileHandler;
	
	public void openHandler(){
		fileHandler = new File(CanceriousMain.getConfigurationManager().imageStoreURL.getFile(), filename);
	}
	
	public void closeHandler(){
		fileHandler = null;
	}
	
	public Image(){
		
	}
	public Image(String fileName){
		this.filename = fileName;
	}
}
