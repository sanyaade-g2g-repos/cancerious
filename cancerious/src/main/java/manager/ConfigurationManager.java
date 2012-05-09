package manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import util.Constants;

public class ConfigurationManager implements Serializable {

	private static final long serialVersionUID = 1L;
	public String imageStorePath;
	public String featureStorePath;
	public String dbStorePath;
	public Integer distributionFromFeature;
	public Integer distributionFromBFS;
	public Integer distributionFromRandom;

	public ConfigurationManager() {
		try {
			File settingsDat = getDatabaseFileAsFile(Constants.SETTINGS_DAT);
			if (settingsDat == null) {
				throw new FileNotFoundException();
			}
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(settingsDat));

			ConfigurationManager cm = (ConfigurationManager) ois.readObject();
			this.dbStorePath = cm.dbStorePath;
			this.distributionFromBFS = cm.distributionFromBFS;
			this.distributionFromFeature = cm.distributionFromFeature;
			this.distributionFromRandom = cm.distributionFromRandom;
			this.featureStorePath = cm.featureStorePath;
			this.imageStorePath = cm.imageStorePath;
		} catch (FileNotFoundException e) {
			imageStorePath = "/image_store";
			featureStorePath = "/feature_store";
			dbStorePath = "/cancerious_store";
			distributionFromFeature = 3;
			distributionFromBFS = 0;
			distributionFromRandom = 2;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File getImageAsFile(String name) {
		try {
			return new File(ConfigurationManager.class.getResource(imageStorePath + "/" + name)
					.toURI());
		} catch (Exception e) {
			// CanceriousLogger.info(e);
			return null;
		}
	}

	public File getFeatureAsFile(String name) {
		try {
			return new File(ConfigurationManager.class.getResource(featureStorePath + "/" + name)
					.toURI());
		} catch (Exception e) {
			// CanceriousLogger.info(e);
			return null;
		}
	}

	public File getDatabaseFileAsFile(String name) {
		try {
			return new File(ConfigurationManager.class.getResource(dbStorePath + "/" + name)
					.toURI());
		} catch (Exception e) {
			// CanceriousLogger.info(e);
			return null;
		}
	}

	public File getImageStore() {
		try {
			return new File(ConfigurationManager.class.getResource(imageStorePath).toURI());
		} catch (Exception e) {
			return null;
		}
	}

	public File getFeatureStore() {
		try {
			return new File(ConfigurationManager.class.getResource(featureStorePath).toURI());
		} catch (Exception e) {
			return null;
		}
	}

	public File getDatabaseStore() {
		try {
			return new File(ConfigurationManager.class.getResource(dbStorePath).toURI());
		} catch (Exception e) {
			return null;
		}
	}

	public void writeSettings() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(
					getDatabaseStore(), Constants.SETTINGS_DAT)));
			oos.writeObject(this);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
