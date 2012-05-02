package manager;

import static util.Constants.CHOICES_DAT;
import static util.Constants.CHOICES_TXT;
import static util.Constants.FEATURE_CACHE_TXT;
import static util.Constants.FEATURE_STORE_TXT;
import static util.Constants.IMAGE_STORE_TXT;
import static util.Constants.SIMILARITIES_DAT;
import static util.Constants.SIMILARITIES_TXT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

	public List<Image> imageList;
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

		//load choices
		readChoices();

		writeChoices();
	}

	public void loadFeatures() {
		ConfigurationManager conf = CanceriousMain.getConfigurationManager();

		//init feature_store.txt
		File storeTxt = conf.getFeatureAsFile(FEATURE_STORE_TXT);
		if(!storeTxt.exists()){
			CanceriousLogger.warn("feature_store.txt file inside feature store dir does not exist.");
			return;
		}
		//init feature_cache
		File featureCache = conf.getDatabaseFileAsFile(FEATURE_CACHE_TXT);
		String storeLine;
		BufferedReader storeReader = null;
		try{
			storeReader = new BufferedReader(new FileReader(storeTxt));
		}
		catch (Exception e) {
			CanceriousLogger.error(e);
		}

		if(featureCache!=null && featureCache.exists()){
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
						FileInputStream fis = new FileInputStream(conf.getFeatureAsFile(cacheFileName) );
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

			featureList = new ArrayList<Feature>();
			try{
				storeReader = new BufferedReader(new FileReader(storeTxt));
			}
			catch (Exception e) {
				CanceriousLogger.error(e);
			}

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
				else if(storeLine.startsWith("#")){
					CanceriousLogger.info(String.format("Skipping comment line %s in feature_store.txt", storeLine));
					continue;
				}
				File featureFile = conf.getFeatureAsFile(storeLine);
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
						for (Image image : imageList) {
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
			CanceriousLogger.info("Feature reading complete");

			//NORMALIZE ALL FEATURE VALUES
			for (Feature feature : featureList) {
				//NORMALIZATION
				feature.mean = 0.0;
				for (FeatureValue fv : feature.values) {
					feature.mean +=fv.value;
				}
				feature.mean/=feature.values.size();
				feature.stddev=0.0;
				for (FeatureValue fv : feature.values) {
					feature.stddev += Math.pow(fv.value-feature.mean,2);
				}
				feature.stddev=Math.sqrt(feature.stddev/feature.values.size());
				for (FeatureValue fv : feature.values) {
					fv.normalizedValue=(fv.value-feature.mean)/feature.stddev;
				}
			}
			CanceriousLogger.info("Normalization complete");

			//FEATURELARIN BİRBİRİ ARASINDAKİ BENZERLİK HESAPLAMASI BURADA
			featureSimilarities = new BidirectionalAdjecencyMatrix(imageList.size(), -1);
			for (int i = 0; i < imageList.size(); i++) {
				Image imageI = imageList.get(i);
				for (int j = i; j < imageList.size(); j++) {
					if (i == j) {
						continue;
					}
					// feature değerlerini karşılaştırıp priorityi de işin içine katarak değerleri hesapla.
					Image imageJ = imageList.get(j);
					double total=0.0;
					Set<FeatureValue> jValues = new HashSet<FeatureValue>();
					for (FeatureValue fv : imageJ.featureValues) {
						jValues.add(fv);
					}
					for (FeatureValue fvi : imageI.featureValues) {
						for (FeatureValue fvj : jValues) {
							if(fvi.feature == fvj.feature){
								total += (fvi.feature.priority*0.1)*Math.pow(fvi.normalizedValue - fvj.normalizedValue, 2);
								jValues.remove(fvj);
								break;
							}
						}
					}
					total = Math.sqrt(total);
					featureSimilarities.set(i, j, total);
				}
			}
			CanceriousLogger.info("Similarity calculation complete");


			//BENZERLİKLER (ADJ.MATRIX) BİR DOSYAYA YAZILIYOR.
			try {
				CanceriousLogger.info(conf.getDatabaseFileAsFile(".").getPath());
				ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(new File(conf.getDatabaseFileAsFile("."),SIMILARITIES_DAT)));
				fos.writeObject(featureSimilarities);
				fos.close();
			} catch (FileNotFoundException e) {
				CanceriousLogger.error(e);
				return;
			} catch (IOException e) {
				CanceriousLogger.error(e);
				return;
			}
			CanceriousLogger.info("Similarities are written to file");

			//TODO FEATURE_CACHE.TXT WRITE
			//CanceriousLogger.info("Feature cache updated");

		}//imajların benzerlik hesaplaması tamamlandı, dosyaya yazıldı ve cache update edildi
		else{ //benzerlik hesaplaması zaten mevcut ve güncel. dosyadan oku.
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(conf.getDatabaseFileAsFile(SIMILARITIES_DAT)));
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
			CanceriousLogger.info("Similarities are written to file");
		}

		for (Image img : imageList) {
			img.featureValues = new HashSet<FeatureValue>();
		}

		for (Feature f : featureList) {
			f.values = new ArrayList<FeatureValue>();
		}

		CanceriousLogger.info("Features are locked and loaded");

	}//feature okuma ve benzerlik hesaplaması işlemleri tamamlandı

	public void loadAllImages(){
		ConfigurationManager conf = CanceriousMain.getConfigurationManager();

		//init image set
		imageList = new ArrayList<Image>();

		//init image_store.txt
		File storeTxt = conf.getImageAsFile(IMAGE_STORE_TXT);
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
		int index=0;
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
			File imgFile = conf.getImageAsFile(line);
			if(!imgFile.exists()){
				CanceriousLogger.warn(String.format("File %s does not exist. ", line));
				continue;
			}
			Image img = new Image(line);
			img.id = index++;
			imageList.add(img);
		}
	}

	public Image getNextImageForMatching(){
		Random r = new Random();
		return imageList.get(r.nextInt(imageList.size()));

		//		int index = choices.getLeastEdgedVertice();
		//		return imageSet.get(index);
	}

	private int[] matchingCache = null;
	private Image matchingImage = null;

	public Image[] getImagesToMatch(Image img, int skip, int count){
		int FROM_FEATURE = CanceriousMain.getConfigurationManager().distributionFromFeature;
		int FROM_BFS = CanceriousMain.getConfigurationManager().distributionFromBFS;
		//int FROM_RANDOM = CanceriousMain.getConfigurationManager().distributionFromRandom;

		boolean recalculate = false;
		if(img.equals(matchingImage)){
			for (int i = skip; i < skip + count; i++) {
				if (matchingCache[i]==-1) {
					recalculate = true;
					break;
				}
			}
		}
		else{
			matchingImage = img;
			matchingCache = new int[imageList.size()];
			for (int i = 0; i < matchingCache.length; i++) {
				matchingCache[i] = -1;
			}
			recalculate = true;
		}
		if(recalculate){
			int page = skip/count;
			ArrayList<Integer> indexes = new ArrayList<Integer>(count);
			//feature
			int[] minValuedEdgeIndexesFromFeature = featureSimilarities.getMinValuedEdgeIndexes(img.id, page*FROM_FEATURE, FROM_FEATURE);
			for (int i = 0; i < minValuedEdgeIndexesFromFeature.length; i++) {
				indexes.add(minValuedEdgeIndexesFromFeature[i]);
				CanceriousLogger.info("from feature: "+minValuedEdgeIndexesFromFeature[i]);
			}
			//bfs
			int[] maxValuedEdgeIndexesFromBfs = choices.getMaxValuedEdgeIndexes(img.id, page*FROM_BFS, FROM_BFS);
			for (int i = 0; i < maxValuedEdgeIndexesFromBfs.length; i++) {
				if (!indexes.contains(maxValuedEdgeIndexesFromBfs[i]) && maxValuedEdgeIndexesFromBfs[i]!=img.id) {
					indexes.add(maxValuedEdgeIndexesFromBfs[i]);
					CanceriousLogger.info("from bfs: "+maxValuedEdgeIndexesFromBfs[i]);
				}
			}
			//random
			Random r = new Random();
			while (true) {/*for (int i = 0; i < FROM_RANDOM; i++) {*/
				Integer random = r.nextInt(imageList.size());
				if (!indexes.contains(random) && random!=img.id) {
					indexes.add(random);
					CanceriousLogger.info("from random: "+random);
				}
				if (indexes.size()>=count) {
					break;
				}
			}
			Collections.shuffle(indexes);
			for (int i = skip; i < skip + count; i++) {
				matchingCache[i] = indexes.remove(0);
			}
		}
		Image[] ret = new Image[count];
		for (int i = skip; i < skip + count; i++) {
			ret[i-skip] = imageList.get(matchingCache[i]);
		}
		return ret;
	}

	public Image[] getImagesToMatch(Image img, int size){
		//		Image[] arr = new Image[size];
		//		int in=0;
		//		Random r = new Random();
		//		Set<Integer> rndSet = new HashSet<Integer>();
		//		while(in<arr.length){
		//			int rnd = r.nextInt(imageSet.size());
		//			rndSet.add(rnd);
		//			if(rndSet.size()==in+1){
		//				if(!imageSet.get(rnd).filename.equals(img.filename)){
		//					arr[in++]=imageSet.get(rnd);
		//				}
		//				else{
		//					rndSet.remove(rnd);
		//				}
		//			}
		//		}
		//		return arr;

		int[] indexes = featureSimilarities.getMinValuedEdgeIndexes(imageList.indexOf(img), 0, size);
		Image[] ret = new Image[size];
		for (int i = 0; i < size; i++) {
			ret[i] = imageList.get(indexes[i]);
		}
		return ret;
	}

	public void setChoice(Image imgA, Image imgB, int choice){
		int i = imageList.indexOf(imgA);
		int j = imageList.indexOf(imgB);
		choices.set(i, j, choice);
		CanceriousLogger.info(String.format("%s %s %d", imgA.filename, imgB.filename, choice));
		writeChoices();
	}

	public int getChoice(Image imgA, Image imgB){
		int i = imageList.indexOf(imgA);
		int j = imageList.indexOf(imgB);
		return (int) choices.get(i, j);
	}

	private void writeChoices(){
		try {
			ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(new File(CanceriousMain.getConfigurationManager().getDatabaseFileAsFile("."),CHOICES_DAT)));
			fos.writeObject(choices);
			fos.close();
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(CanceriousMain.getConfigurationManager().getDatabaseFileAsFile("."),CHOICES_TXT)));
			bw.write(choices.toString());
			bw.close();
			bw = new BufferedWriter(new FileWriter(new File(CanceriousMain.getConfigurationManager().getDatabaseFileAsFile("."),SIMILARITIES_TXT)));
			bw.write(featureSimilarities.toString());
			bw.close();
			//CanceriousLogger.info("Choices Written");
		} catch (FileNotFoundException e) {
			CanceriousLogger.error(e);
			return;
		} catch (IOException e) {
			CanceriousLogger.error(e);
			return;
		}
	}

	private void readChoices(){
		try {
			File dat = CanceriousMain.getConfigurationManager().getDatabaseFileAsFile(CHOICES_DAT);
			if(dat==null){
				choices = new BidirectionalAdjecencyMatrix(imageList.size(), -1);
				return;
			}
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dat));
			choices = (BidirectionalAdjecencyMatrix) ois.readObject();
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

	public BidirectionalAdjecencyMatrix getFeatureSimilarities() {
		return featureSimilarities;
	}

	public void setFeatureSimilarities(BidirectionalAdjecencyMatrix featureSimilarities) {
		this.featureSimilarities = featureSimilarities;
	}

	public BidirectionalAdjecencyMatrix getChoices() {
		return choices;
	}

	public void setChoices(BidirectionalAdjecencyMatrix choices) {
		this.choices = choices;
	}


}
