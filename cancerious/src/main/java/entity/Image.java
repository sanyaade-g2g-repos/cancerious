package entity;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import main.CanceriousMain;

public class Image {

	public Integer id;
	public String filename;
	public Set<FeatureValue> featureValues;
	public File fileHandler;
	
	public void openHandler(){
		fileHandler = CanceriousMain.getConfigurationManager().getImageAsFile(filename);
	}
	
	public void closeHandler(){
		fileHandler = null;
	}
	
	public Image(String fileName){
		this.filename = fileName;
		featureValues = new HashSet<FeatureValue>();
	}
}
