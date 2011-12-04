package manager;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadFactory;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import entity.Image;

import main.CanceriousMain;

public class GraphManager {

	public Set<Image> imageSet;
	
	private GraphDatabaseService db;
	
	public GraphManager(){
		db = new EmbeddedGraphDatabase(CanceriousMain.getConfigurationManager().dbStorePath);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				db.shutdown();
			}
		});
		
		File imageStoreDir = new File(CanceriousMain.getConfigurationManager().imageStorePath);
		if(imageStoreDir.isDirectory()){
			imageSet = new HashSet<Image>();
			for (File f:imageStoreDir.listFiles()){
				if(f.isFile()){
					Image i = new Image();
					i.filename = f.getName();
					imageSet.add(i);
				}
			}
		}
	}
	
	public Image getNextImageForMatching(){
		if(imageSet!=null){
			return imageSet.iterator().next();
		}
		else
			return null;
	}
	
	public Image[] getImagesToMatch(Image img){
		return null;
	}
	
}
