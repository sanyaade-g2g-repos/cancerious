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

	private static final long serialVersionUID = 5740834522108481942L;

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
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(1);
		slider.setMinimum(-1);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//TODO change similarity value here
			}
		});
		slider.setValue(0);
		slider.setMaximum(3);
		add(slider, BorderLayout.SOUTH);
		
	}

}
