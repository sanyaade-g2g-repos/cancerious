package gui;

import java.awt.BorderLayout;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.Image;

public class ImageToMatch extends JPanel {
	public ImageToMatch(Image image) {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnNextImage = new JButton("Next Image");
		add(btnNextImage, BorderLayout.SOUTH);
		
		JLabel label = new JLabel("");
		if(image.fileHandler==null){
			image.openHandler();
		}
		try {
			label.setIcon(new ImageIcon(image.fileHandler.toURI().toURL()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		add(label, BorderLayout.CENTER);
	}

	private static final long serialVersionUID = -2492212483685159320L;

}
