package gui;

import java.awt.BorderLayout;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import entity.Image;
import entity.Similarity;

public class ImageRater extends JPanel {

	public ImageRater(Image image, Similarity similarity) {
		super();
		setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel("");
		if(image.fileHandler==null){
			image.openHandler();
		}
		try {
			label.setIcon(new ImageIcon(image.fileHandler.toURI().toURL()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		add(label);
		
		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//TODO change similarity value here
			}
		});
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(1);
		slider.setSnapToTicks(true);
		slider.setValue(1);
		slider.setMaximum(4);
		add(slider, BorderLayout.SOUTH);
		
		// TODO Auto-generated constructor stub
	}

}
