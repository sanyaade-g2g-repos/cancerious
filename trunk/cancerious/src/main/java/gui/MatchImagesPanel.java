package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.Image;

import main.CanceriousMain;
import manager.GraphManager.CanceriousRelationships;

public class MatchImagesPanel extends JPanel {
	public MatchImagesPanel() {
		GridBagLayout gbl_matchImages = new GridBagLayout();
		gbl_matchImages.columnWidths = new int[] { 0, 0, 0 };
		gbl_matchImages.rowHeights = new int[] { 0, 0, 0 };
		gbl_matchImages.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_matchImages.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		this.setLayout(gbl_matchImages);
		
		GridBagConstraints gbc;
		
		Image img = CanceriousMain.getGraphManager().getNextImageForMatching();
		Image[] similarImages = CanceriousMain.getGraphManager().getImagesToMatch(img);
		
		ImageToMatch imageToMatch = new ImageToMatch(img);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(imageToMatch, gbc);
		
		ImageRater rater;
		rater = new ImageRater(similarImages[0], null);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(rater, gbc);
		
		rater = new ImageRater(similarImages[1], null);
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		this.add(rater, gbc);
		
		rater = new ImageRater(similarImages[2], null);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(rater, gbc);
		
		rater = new ImageRater(similarImages[3], null);
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add(rater, gbc);
		
		rater = new ImageRater(similarImages[4], null);
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		this.add(rater, gbc);
		
	}

	private static final long serialVersionUID = 8185724226211637533L;

	
}
