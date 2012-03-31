package gui;

import java.awt.BorderLayout;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import entity.Image;

public class ImageRater extends JPanel {

	private static final long serialVersionUID = 5740834522108481942L;
	private JSlider slider;

	public ImageRater(final Image image, int similarity) {
		super();
		setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel("");
		if(image.fileHandler==null){
			image.openHandler();
		}
		try {
			label.setIcon(new ImageIcon(image.fileHandler.toURI().toURL()));
			label.setToolTipText(image.filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		add(label);

		slider = new JSlider();
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(1);
		slider.setMinimum(-1);
		slider.setMaximum(2);
		slider.setValue(similarity);
		add(slider, BorderLayout.SOUTH);

	}

	public JSlider getSlider() {
		return slider;
	}

}
