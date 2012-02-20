package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import main.CanceriousMain;
import entity.Image;

public class MatchImagesPanel extends JPanel {

	private Image headImage;

	public MatchImagesPanel() {
		GridBagLayout gbl_matchImages = new GridBagLayout();
		gbl_matchImages.columnWidths = new int[] { 200, 200, 200 };
		gbl_matchImages.rowHeights = new int[] { 200, 200};
		gbl_matchImages.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_matchImages.rowWeights = new double[] { 1.0, 1.0};
		this.setLayout(gbl_matchImages);

		nextImage();
	}

	public void nextImage(){
		this.removeAll();

		GridBagConstraints gbc;
		headImage = CanceriousMain.getGraphManager().getNextImageForMatching();
		Image[] similarImages = CanceriousMain.getGraphManager().getImagesToMatch(headImage,5);

		ImageToMatch imageToMatch = new ImageToMatch(headImage);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(imageToMatch, gbc);

		ImageRater rater;
		rater = new ImageRater(similarImages[0], CanceriousMain.getGraphManager().getChoice(headImage, similarImages[0]));
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(rater, gbc);

		rater = new ImageRater(similarImages[1], CanceriousMain.getGraphManager().getChoice(headImage, similarImages[1]));
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		this.add(rater, gbc);

		rater = new ImageRater(similarImages[2], CanceriousMain.getGraphManager().getChoice(headImage, similarImages[2]));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(rater, gbc);

		rater = new ImageRater(similarImages[3], CanceriousMain.getGraphManager().getChoice(headImage, similarImages[3]));
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add(rater, gbc);

		rater = new ImageRater(similarImages[4], CanceriousMain.getGraphManager().getChoice(headImage, similarImages[4]));
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		this.add(rater, gbc);
	}

	public void setChoice(Image img, int choice){
		CanceriousMain.getGraphManager().setChoice(headImage, img, choice);
	}

	private static final long serialVersionUID = 8185724226211637533L;


}
