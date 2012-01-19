package manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import main.CanceriousMain;
import util.BidirectionalAdjecencyMatrix;
import util.CanceriousLogger;
import entity.Feature;
import entity.Image;

public class GraphManager {

	public List<Image> imageSet;
	public List<Feature> featureList;
	
	
	private BidirectionalAdjecencyMatrix featureSimilarities;
	
	private BidirectionalAdjecencyMatrix choices;
	
	private boolean reCalculateAllFeatures;
	
	public GraphManager(){
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				CanceriousLogger.info("Shutting down...");
				//TODO save user choices to file then exit. 
			}
		});
		
		//load images
		loadAllImages();
		
		//load features
		loadFeatures();
		
		//calculate similarities if necessary
		
		//if not necessary, then load similarity file
	}
	
	private void loadFeatures() {
		// TODO Auto-generated method stub
		File featureStore = CanceriousMain.getConfigurationManager().featureStore;
		if(!featureStore.isDirectory()){
			CanceriousLogger.warn("feature store is not a directory.");
			return;
		}
		//init feature_store.txt
		File storeTxt = new File(featureStore, "feature_store.txt");
		if(!storeTxt.exists()){
			CanceriousLogger.warn("feature_store.txt file inside feature store dir does not exist.");
			return;
		}
		
		File featureCache = new File(CanceriousMain.getConfigurationManager().dataStore, "feature_cache.txt");
		String storeLine;
		BufferedReader storeReader = null;
		try{
			storeReader = new BufferedReader(new FileReader(storeTxt));
		}
		catch (Exception e) {
			CanceriousLogger.error(e);
		}
		
		if(featureCache.exists()){
			String cacheLine;
			BufferedReader cacheReader = null;
			try{
				cacheReader = new BufferedReader(new FileReader(featureCache));
			}
			catch (Exception e) {
				CanceriousLogger.error(e);
			}
			while(true){
				try{
					cacheLine = cacheReader.readLine();
					storeLine = storeReader.readLine();					
				} catch (IOException e) {
					CanceriousLogger.error(e);
					continue;
				}
				if(cacheLine == null && storeLine == null){
					reCalculateAllFeatures = false;
					break;
				}
				else if (cacheLine != null && storeLine != null){
					StringTokenizer cacheToken = new StringTokenizer(cacheLine,",");
					String cacheFileName = cacheToken.nextToken();
					String cacheFileHash = cacheToken.nextToken();
					if(!storeLine.equals(cacheFileName)){
						reCalculateAllFeatures = true;
						break;
					}
					String realFileHash;
					try {
						FileInputStream fis = new FileInputStream( new File(featureStore, cacheFileName) );
						realFileHash = org.apache.commons.codec.digest.DigestUtils.md5Hex( fis );
					} catch (FileNotFoundException e) {
						CanceriousLogger.error(e);
						reCalculateAllFeatures = true;
						break;
					} catch (IOException e) {
						CanceriousLogger.error(e);
						reCalculateAllFeatures = true;
						break;
					}
					if(!realFileHash.equals(cacheFileHash)){
						reCalculateAllFeatures = true;
						break;
					}
				}
				else{
					reCalculateAllFeatures = true;
					break;
				}
			}
			cacheReader.close();
		}
		else{ //feature cache does not exist yet. 
			reCalculateAllFeatures = true;
		}
		
		//read ALL features from ALL filenames in feature store and put the values to image objects in the set. 
		if(reCalculateAllFeatures){
			
			while(true){ //for each file
				try {
					storeLine = storeReader.readLine();
				} catch (IOException e) {
					CanceriousLogger.error(e);
					continue;
				}
				if(storeLine==null){
					break;
				}
				File featureFile = new File(featureStore, storeLine);
				if(!featureFile.exists()){
					CanceriousLogger.warn(String.format("File %s does not exist. ", storeLine));
					continue;
				}
				StringTokenizer featureLineToken;
				BufferedReader featureFileReader = new BufferedReader(new FileReader(featureFile));
				String featureLine;
				boolean firstLine = true;
				
				while(true){ //for each line in a file
					featureLine = featureFileReader.readLine();
					if(featureFile==null){
						break;
					}
					featureLineToken = new StringTokenizer(featureLine,",");
					if(firstLine){
						Feature f = new Feature(featureLineToken.nextToken(),Integer.parseInt(featureLineToken.nextToken()));
						featureList.add(f);
						firstLine=false;
					}
					else{
						
					}
				}
				

			}
		}
	}

	public void loadAllImages(){
		//init image set
		imageSet = new ArrayList<Image>();
		//init image store dir
		File imageStoreDir = CanceriousMain.getConfigurationManager().imageStore;
		if(!imageStoreDir.isDirectory()){
			CanceriousLogger.warn("image store is not a directory.");
			return;
		}
		//init image_store.txt
		File storeTxt = new File(imageStoreDir, "image_store.txt");
		if(!storeTxt.exists()){
			CanceriousLogger.warn("image_store.txt file inside image store dir does not exist.");
			return;
		}
		//read image_store.txt
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(storeTxt));
		} catch (FileNotFoundException e1) {
			//cannot happen.
			CanceriousLogger.error(e1);
			return;
		}
		String line;
		while(true){
			try {
				line = reader.readLine();
			} catch (IOException e) {
				CanceriousLogger.error(e);
				continue;
			}
			if(line==null){
				break;
			}
			File imgFile = new File(imageStoreDir, line);
			if(!imgFile.exists()){
				CanceriousLogger.warn(String.format("File %s does not exist. ", line));
				continue;
			}
			Image img = new Image();
			img.filename = line;
			imageSet.add(img);
		}
	}
	
	public Image getNextImageForMatching(){
		if(imageSet!=null){
			Random r = new Random();
			return imageSet.get(r.nextInt(imageSet.size()));
		}
		else
			return null;
	}
	
	public Image[] getImagesToMatch(Image img, int size){
		Image[] arr = new Image[size];
		int in=0;
		Random r = new Random();
		Set<Integer> rndSet = new HashSet<Integer>();
		while(in<arr.length){
			int rnd = r.nextInt(imageSet.size());
			rndSet.add(rnd);
			if(rndSet.size()==in+1){
				if(!imageSet.get(rnd).filename.equals(img.filename)){
					arr[in++]=imageSet.get(rnd);
				}
				else{
					rndSet.remove(rnd);
				}
			}
		}
		return arr;
	}
	
}
