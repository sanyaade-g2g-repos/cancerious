package gui.subimage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.CanceriousMain;
import util.CanceriousLogger;
import entity.Image;
import entity.SubImage;
import entity.SubImageMatch;
import gui.ShowImage;

public class MatchSubImages extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image headImage;

	private int currentN;

	private GridBagConstraints gbc_1;
	private GridBagConstraints gbc_2;
	private GridBagConstraints gbc_3;
	private GridBagConstraints gbc_4;
	private GridBagConstraints gbc_5;

	private ShowImage showImage1;
	private ShowImage showImage2;
	private ShowImage showImage3;
	private ShowImage showImage4;
	private ShowImage showImage5;

	private SubImageToMatch imageToMatch;

	public MatchSubImages(){
		GridBagLayout gbl_matchImages = new GridBagLayout();
		gbl_matchImages.columnWidths = new int[] { 200, 200, 200 };
		gbl_matchImages.rowHeights = new int[] { 200, 200, 50 };
		gbl_matchImages.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_matchImages.rowWeights = new double[] { 1.0, 1.0, 0.0 };
		this.setLayout(gbl_matchImages);

		nextImage();
	}

	public void saveAll(boolean warn) {

		List<SubImageMatch> subImageMatches = CanceriousMain.getGraphManager().getSubImageMatches();
		SubImage headSubImage = imageToMatch.getShowImage().getSubImage();
		if (!headSubImage.isValid()) {
			if (warn) {
				CanceriousLogger.infoWithDisplay("Please select a region in head image first.");
			}
			return;
		}
		int count = 0;
		for (SubImage select : showImage1.getMultiSelects()) {
			subImageMatches.add(new SubImageMatch(headSubImage.clone(), select));
			count++;
		}
		showImage1.clear();
		for (SubImage select : showImage2.getMultiSelects()) {
			subImageMatches.add(new SubImageMatch(headSubImage.clone(), select));
			count++;
		}
		showImage2.clear();
		for (SubImage select : showImage3.getMultiSelects()) {
			subImageMatches.add(new SubImageMatch(headSubImage.clone(), select));
			count++;
		}
		showImage3.clear();
		for (SubImage select : showImage4.getMultiSelects()) {
			subImageMatches.add(new SubImageMatch(headSubImage.clone(), select));
			count++;
		}
		showImage4.clear();
		for (SubImage select : showImage5.getMultiSelects()) {
			subImageMatches.add(new SubImageMatch(headSubImage.clone(), select));
			count++;
		}
		showImage5.clear();
		
		if (count > 0) {
			headSubImage.reset();
			imageToMatch.getShowImage().repaint();
			CanceriousMain.getGraphManager().writeChoices();
			CanceriousLogger.infoWithDisplay(String.format("%d submatchings saved", count));
		}
		else {
			CanceriousLogger.infoWithDisplay("Please select at least one region in matching images.");
		}
		//nextImage();
	}

	private void nextImage(int n, boolean change) {
		this.currentN = n;

		this.removeAll();

		GridBagConstraints gbc;
		if (change) {
			headImage = CanceriousMain.getGraphManager().getNextImageForMatching();
		}
		final Image[] similarImages = CanceriousMain.getGraphManager().getImagesToMatch(headImage,
				n, 5);

		imageToMatch = new SubImageToMatch(headImage);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(imageToMatch, gbc);


		showImage1 = new ShowImage(ShowImage.SELECT_SUBIMAGE_MULTISELECT_MODE, similarImages[0]);
		gbc_1 = new GridBagConstraints();
		gbc_1.insets = new Insets(0, 0, 5, 5);
		gbc_1.gridx = 1;
		gbc_1.gridy = 0;
		this.add(showImage1, gbc_1);

		showImage2 = new ShowImage(ShowImage.SELECT_SUBIMAGE_MULTISELECT_MODE, similarImages[1]);
		gbc_2 = new GridBagConstraints();
		gbc_2.insets = new Insets(0, 0, 5, 0);
		gbc_2.gridx = 2;
		gbc_2.gridy = 0;
		this.add(showImage2, gbc_2);

		showImage3 = new ShowImage(ShowImage.SELECT_SUBIMAGE_MULTISELECT_MODE, similarImages[2]);
		gbc_3 = new GridBagConstraints();
		gbc_3.insets = new Insets(0, 0, 5, 5);
		gbc_3.gridx = 0;
		gbc_3.gridy = 1;
		this.add(showImage3, gbc_3);

		showImage4 = new ShowImage(ShowImage.SELECT_SUBIMAGE_MULTISELECT_MODE, similarImages[3]);
		gbc_4 = new GridBagConstraints();
		gbc_4.insets = new Insets(0, 0, 5, 5);
		gbc_4.gridx = 1;
		gbc_4.gridy = 1;
		this.add(showImage4, gbc_4);

		showImage5 = new ShowImage(ShowImage.SELECT_SUBIMAGE_MULTISELECT_MODE, similarImages[4]);
		gbc_5 = new GridBagConstraints();
		gbc_5.insets = new Insets(0, 0, 5, 0);
		gbc_5.gridx = 2;
		gbc_5.gridy = 1;
		this.add(showImage5, gbc_5);

		JButton btnPrev = new JButton("Prev 5");
		btnPrev.setVisible(n > 0);
		btnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextImage(currentN - 5, false);
			}
		});
		GridBagConstraints gbc_btnPrev = new GridBagConstraints();
		gbc_btnPrev.insets = new Insets(0, 0, 0, 5);
		gbc_btnPrev.gridx = 1;
		gbc_btnPrev.gridy = 2;
		add(btnPrev, gbc_btnPrev);

		JButton btnNext = new JButton("Next 5");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentN + 10 <= CanceriousMain.getGraphManager().imageList.size()) {
					nextImage(currentN + 5, false);
				}
			}
		});
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridx = 2;
		gbc_btnNext.gridy = 2;
		add(btnNext, gbc_btnNext);
	}

	public void nextImage() {
		nextImage(0, true);
	}

}
