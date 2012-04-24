package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.CanceriousMain;
import entity.Image;

/**
 * 6 imajdan olusur (ilerde degistirilebilir), 
 * sol üstteki ImageToMatch, diger 5 i ImageRater olmak üzere. 
 * @author SEB
 */
public class MatchImagesPanel extends JPanel {

	private Image headImage;

	private int currentN;

	public MatchImagesPanel() {
		GridBagLayout gbl_matchImages = new GridBagLayout();
		gbl_matchImages.columnWidths = new int[] { 200, 200, 200 };
		gbl_matchImages.rowHeights = new int[] { 200, 200, 50};
		gbl_matchImages.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_matchImages.rowWeights = new double[] { 1.0, 1.0, 0.0};
		this.setLayout(gbl_matchImages);

		nextImage(0, true);
	}

	public void nextImage(final int n, final boolean change){
		this.currentN = n;

		this.removeAll();

		GridBagConstraints gbc;
		if (change) {
			headImage = CanceriousMain.getGraphManager().getNextImageForMatching();
		}
		final Image[] similarImages = CanceriousMain.getGraphManager().getImagesToMatch(headImage,n,5);

		ImageToMatch imageToMatch = new ImageToMatch(headImage);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(imageToMatch, gbc);

		ImageRater rater;
		rater = new ImageRater(similarImages[0], CanceriousMain.getGraphManager().getChoice(headImage, similarImages[0]));
		rater.getSlider().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setChoice(similarImages[0], ((JSlider)arg0.getSource()).getValue());
			}
		});
		gbc_1 = new GridBagConstraints();
		gbc_1.insets = new Insets(0, 0, 5, 5);
		gbc_1.gridx = 1;
		gbc_1.gridy = 0;
		this.add(rater, gbc_1);

		rater = new ImageRater(similarImages[1], CanceriousMain.getGraphManager().getChoice(headImage, similarImages[1]));
		rater.getSlider().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setChoice(similarImages[1], ((JSlider)arg0.getSource()).getValue());
			}
		});
		gbc_2 = new GridBagConstraints();
		gbc_2.insets = new Insets(0, 0, 5, 0);
		gbc_2.gridx = 2;
		gbc_2.gridy = 0;
		this.add(rater, gbc_2);

		rater = new ImageRater(similarImages[2], CanceriousMain.getGraphManager().getChoice(headImage, similarImages[2]));
		rater.getSlider().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setChoice(similarImages[2], ((JSlider)arg0.getSource()).getValue());
			}
		});
		gbc_3 = new GridBagConstraints();
		gbc_3.insets = new Insets(0, 0, 5, 5);
		gbc_3.gridx = 0;
		gbc_3.gridy = 1;
		this.add(rater, gbc_3);

		rater = new ImageRater(similarImages[3], CanceriousMain.getGraphManager().getChoice(headImage, similarImages[3]));
		rater.getSlider().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setChoice(similarImages[3], ((JSlider)arg0.getSource()).getValue());
			}
		});
		gbc_4 = new GridBagConstraints();
		gbc_4.insets = new Insets(0, 0, 5, 5);
		gbc_4.gridx = 1;
		gbc_4.gridy = 1;
		this.add(rater, gbc_4);

		rater = new ImageRater(similarImages[4], CanceriousMain.getGraphManager().getChoice(headImage, similarImages[4]));
		rater.getSlider().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				setChoice(similarImages[4], ((JSlider)arg0.getSource()).getValue());
			}
		});
		gbc_5 = new GridBagConstraints();
		gbc_5.insets = new Insets(0, 0, 5, 0);
		gbc_5.gridx = 2;
		gbc_5.gridy = 1;
		this.add(rater, gbc_5);

		JButton btnPrev = new JButton("Prev 5");
		btnPrev.setVisible(n>0);
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
				if(currentN+10<=CanceriousMain.getGraphManager().imageList.size()) {
					nextImage(currentN + 5, false);
				}
			}
		});
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridx = 2;
		gbc_btnNext.gridy = 2;
		add(btnNext, gbc_btnNext);
	}

	public void setChoice(Image img, int choice){
		CanceriousMain.getGraphManager().setChoice(headImage, img, choice);
	}

	private static final long serialVersionUID = 8185724226211637533L;
	private GridBagConstraints gbc_1;
	private GridBagConstraints gbc_2;
	private GridBagConstraints gbc_3;
	private GridBagConstraints gbc_4;
	private GridBagConstraints gbc_5;


}
