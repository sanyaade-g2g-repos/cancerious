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

	@Override
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof Image && ((Image)obj).id == this.id) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", filename=" + filename + "]";
	}

}
