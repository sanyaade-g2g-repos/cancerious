package manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import main.CanceriousMain;
import util.BidirectionalAdjecencyMatrix;
import util.CanceriousLogger;
import entity.Feature;
import entity.FeatureValue;
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
		File featureStore = new File(CanceriousMain.getConfigurationManager().featureStoreURL.getFile());
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
		//init feature_cache
		File featureCache = new File(CanceriousMain.getConfigurationManager().dbStoreURL.getFile(), "feature_cache.txt");
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
			try {
				cacheReader.close();
			} catch (IOException e) {
				CanceriousLogger.error(e);
			}
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
				StringTokenizer featureLineToken;
				BufferedReader featureFileReader;
				try {
					featureFileReader = new BufferedReader(new FileReader(featureFile));
				} catch (FileNotFoundException e) {
					CanceriousLogger.warn(String.format("File %s does not exist. ", storeLine));
					continue;
				}
				String featureLine = null;
				boolean firstLine = true;
				boolean secondLine = false;
				Queue<Integer> priorities = null ;
				List<Feature> featureListForFile = new ArrayList<Feature>();
				
				while(true){ //for each line in a file
					try {
						featureLine = featureFileReader.readLine();
					} catch (IOException e) {
						CanceriousLogger.error(e);
						break;
					}
					if(featureLine==null){
						break;
					}
					//burada feature dosya yapısının kesinleştirilmesi gerekiyor. 
					// 1.satır: , priorities, , , 
					// 2.satır: , feature names, , ,
					// 3.satır dosyaadı, değerler, , , 
					featureLineToken = new StringTokenizer(featureLine,",");
					if(firstLine){ //1.satırdayız
						priorities = new LinkedList<Integer>();
						while(featureLineToken.hasMoreTokens()){
							priorities.offer(Integer.parseInt(featureLineToken.nextToken()));
						}
						firstLine=false;
						secondLine=true;
					}
					else if(secondLine){ //2.satırdayız
						while(featureLineToken.hasMoreTokens()){
							Integer priority = priorities.poll();
							String featureName = featureLineToken.nextToken();
							Feature feature = new Feature(featureName, priority);
							featureList.add(feature);
							featureListForFile.add(feature);
						}//feature objects are now initialized.
						priorities = null;
						secondLine = false;
					}
					else{//feature değerlerini okuyoruz. bu kısım bir feature dosyasındaki her bir değer satırı için çalışır
						//ilk hücrede image adı yazması lazım. 
						String imageName = featureLineToken.nextToken();
						// bu değer her hücreden sonra arttırılır ve bir sonraki feature değeri, bu feature kullanılarak yazılır. 
						int featureIndex = 0;
						// image adı image listesinde aranır
						boolean imageFound = false;
						for (Image image : imageSet) {
							if(image.filename.equals(imageName)){
								while(featureLineToken.hasMoreTokens()){
									Feature feature = featureListForFile.get(featureIndex);
									double featureVelue = Double.parseDouble(featureLineToken.nextToken());
									FeatureValue fv = new FeatureValue(feature,featureVelue);
									image.featureValues.add(fv);
									feature.values.add(fv);
									featureIndex++;
								}
								imageFound = true;
								break;
							}
						}
						//imaj bulunamadıysa hata yaz.
						if(!imageFound){
							CanceriousLogger.warn("image "+imageName+" is not found in image store!");
						}
					}//bir satırın değerleri okunup yazıldı. 
				}//bir feature dosyası tamamen okundu.
			}//tüm feature dosyaları okundu
			
			//TODO NORMALIZE ALL FEATURE VALUES
			for (Feature feature : featureList) {
				//TODO NORMALIZATION
			}
			
			//TODO FEATURELARIN BİRBİRİ ARASINDAKİ BENZERLİK HESAPLAMASI BURADA
			featureSimilarities = new BidirectionalAdjecencyMatrix(imageSet.size());
			for (int i = 0; i < imageSet.size(); i++) {
				for (int j = i; j < imageSet.size(); j++) {
					if (i == j) continue;
					// feature değerlerini karşılaştırıp priorityi de işin içine katarak değerleri hesapla.
				}
			}
			
			
			//BENZERLİKLER (ADJ.MATRIX) BİR DOSYAYA YAZILIYOR.  
			try {
				ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(new File(CanceriousMain.getConfigurationManager().dbStoreURL.getFile(),"similarities.dat")));
				fos.writeObject(featureSimilarities);
				fos.close();
			} catch (FileNotFoundException e) {
				CanceriousLogger.error(e);
				return;
			} catch (IOException e) {
				CanceriousLogger.error(e);
				return;
			}
			
			//TODO FEATURE_CACHE.TXT WRITE
			
		}//imajların benzerlik hesaplaması tamamlandı, dosyaya yazıldı ve cache update edildi
		else{ //benzerlik hesaplaması zaten mevcut ve güncel. dosyadan oku.
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(CanceriousMain.getConfigurationManager().dbStoreURL.getFile(),"similarities.dat")));
				featureSimilarities = (BidirectionalAdjecencyMatrix) ois.readObject();
			} catch (FileNotFoundException e) {
				CanceriousLogger.error(e);
				return;
			} catch (IOException e) {
				CanceriousLogger.error(e);
				return;
			} catch (ClassNotFoundException e) {
				CanceriousLogger.error(e);
				return;
			}
		}
		
		
	}//feature okuma ve benzerlik hesaplaması işlemleri tamamlandı

	public void loadAllImages(){
		//init image set
		imageSet = new ArrayList<Image>();
		//init image store dir
		File imageStoreDir = new File(CanceriousMain.getConfigurationManager().imageStoreURL.getFile());
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
