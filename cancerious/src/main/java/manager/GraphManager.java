package manager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.CanceriousMain;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import util.CanceriousLogger;
import entity.Image;

public class GraphManager {

	public enum CanceriousRelationships implements RelationshipType
	{
	    SIMILAR_TO, IMAGE_REFERENCE, IMAGE;
	}
	public static final String FILENAME_KEY = "fileName";
	
	
	
	public List<Image> imageSet;
	public List<String> fileNameList;
	
	private GraphDatabaseService db;
	private Index<Node> nodeIndex;
	
	public GraphManager(){
		db = new EmbeddedGraphDatabase(CanceriousMain.getConfigurationManager().dbStorePath);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				CanceriousLogger.info("Shutting down neo4j...");
				db.shutdown();
			}
		});
		nodeIndex = db.index().forNodes("image");
		loadAllImagesWithTransaction();
	}
	
	public void reloadAllImagesWithTransaction(){
		Transaction t = db.beginTx();
		try{
			nodeIndex.delete();
			nodeIndex = db.index().forNodes("image");
			loadAllImages();
			t.success();
		}
		finally{
			t.finish();
		}
	}
	
	public void loadAllImages(){
		fileNameList = new ArrayList<String>();
		
		File imageStoreDir = new File(CanceriousMain.getConfigurationManager().imageStorePath);
		
		//create or get imageReferenceNode for easy traversal. 
		Node imageReferenceNode; 
		Relationship refRel = db.getReferenceNode().getSingleRelationship(CanceriousRelationships.IMAGE_REFERENCE, Direction.OUTGOING);
		if(refRel == null){
			imageReferenceNode = db.createNode();
			db.getReferenceNode().createRelationshipTo(imageReferenceNode, CanceriousRelationships.IMAGE_REFERENCE);
		}
		else{
			imageReferenceNode = refRel.getEndNode();
		}
		
		if(imageStoreDir.isDirectory()){
			imageSet = new ArrayList<Image>();
			for (File f:imageStoreDir.listFiles()){
				if(f.isFile()){
					fileNameList.add(f.getName());
					
					//check index if filename exists, if no, add to index, if yes, skip. 
					Node n = nodeIndex.get(FILENAME_KEY, f.getName()).getSingle();
					if(n==null){
						//add index here
						CanceriousLogger.info("adding image to index");
						n = db.createNode();
						n.setProperty(FILENAME_KEY, f.getName());
						nodeIndex.add(n, FILENAME_KEY, f.getName());
						imageReferenceNode.createRelationshipTo(n, CanceriousRelationships.IMAGE);
						
					}
					
					Image i = new Image();
					i.filename = f.getName();
					imageSet.add(i);
				}
			}
		}
		else{
			CanceriousLogger.warn("image store is not a directory.");
		}
	}
	
	public void loadAllImagesWithTransaction(){
		Transaction t = db.beginTx();
		try{
			loadAllImages();
			t.success();
		}
		finally{
			t.finish();
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
		Image[] arr = new Image[5];
		int in=0;
		for (int i=0;i<imageSet.size();i++){
			Image img2 = imageSet.get(i);
			if(img2.filename!=img.filename && in<arr.length){
				arr[in++]=img2;
			}
		}
		return arr;
	}
	
}
