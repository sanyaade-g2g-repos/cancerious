package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.CanceriousMain;
import util.BidirectionalAdjecencyMatrix;
import util.CanceriousLogger;
import entity.Image;

public class ImageRelations extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel relationPanel;
	private JSlider similaritySlider;
	private Image image;

	public ImageRelations(final Image image){
		super();
		if(image==null){
			CanceriousLogger.error("selected image returned null. ");
			return;
		}
		this.image = image;

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CanceriousMain.getGuiManager().getViewImages().showAll();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnBack, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnBack, 10, SpringLayout.WEST, this);
		add(btnBack);

		JLabel lblImageicon = new JLabel();
		try {
			lblImageicon.setIcon(new ImageIcon(image.fileHandler.toURI().toURL()));
		} catch (MalformedURLException e1) {
			CanceriousLogger.error(e1);
		}
		springLayout.putConstraint(SpringLayout.NORTH, lblImageicon, 6, SpringLayout.SOUTH, btnBack);
		springLayout.putConstraint(SpringLayout.WEST, lblImageicon, 10, SpringLayout.WEST, this);
		add(lblImageicon);

		similaritySlider = new JSlider();
		similaritySlider.setMajorTickSpacing(1);
		springLayout.putConstraint(SpringLayout.NORTH, similaritySlider, 6, SpringLayout.SOUTH, lblImageicon);
		springLayout.putConstraint(SpringLayout.WEST, similaritySlider, 0, SpringLayout.WEST, btnBack);
		similaritySlider.setToolTipText("Similarity");
		similaritySlider.setSnapToTicks(true);
		similaritySlider.setPaintLabels(true);
		similaritySlider.setValue(0);
		similaritySlider.setMinimum(-1);
		similaritySlider.setMaximum(2);
		similaritySlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				similaryStateChanged();
			}
		});
		add(similaritySlider);


		relationPanel = new JPanel();
		relationPanel.setLayout(new GridLayout(0, 3, 0, 0));

		JScrollPane relationScroller = new JScrollPane(relationPanel);
		springLayout.putConstraint(SpringLayout.NORTH, relationScroller, 6, SpringLayout.SOUTH, similaritySlider);
		springLayout.putConstraint(SpringLayout.WEST, relationScroller, 0, SpringLayout.WEST, btnBack);
		springLayout.putConstraint(SpringLayout.SOUTH, relationScroller, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, relationScroller, -10, SpringLayout.EAST, this);
		relationScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		relationScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(relationScroller);

		JLabel lblFilename = new JLabel(image.filename);
		springLayout.putConstraint(SpringLayout.NORTH, lblFilename, 0, SpringLayout.NORTH, btnBack);
		springLayout.putConstraint(SpringLayout.WEST, lblFilename, 6, SpringLayout.EAST, btnBack);
		add(lblFilename);

	}

	private void similaryStateChanged(){
		relationPanel.removeAll();

		int value = similaritySlider.getValue();
		BidirectionalAdjecencyMatrix choices = CanceriousMain.getGraphManager().getChoices();
		int imageIndex = CanceriousMain.getGraphManager().imageSet.indexOf(image);
		double[] values = choices.getAdjecencies(imageIndex);
		for (int i = 0; i < values.length; i++) {
			if (values[i] == value && i != imageIndex) {
				final Image otherImage = CanceriousMain.getGraphManager().imageSet.get(i);
				final ImageRater rater = new ImageRater(otherImage, value);
				rater.getSlider().addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						CanceriousMain.getGraphManager().setChoice(image, otherImage, rater.getSlider().getValue());
						//similaryStateChanged();
					}
				});
				relationPanel.add(rater);
				//CanceriousLogger.info("rater added. " +image.filename+","+otherImage.filename);
			}
		}

		relationPanel.repaint();
	}
}
