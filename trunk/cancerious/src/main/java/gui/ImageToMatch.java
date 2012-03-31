package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.Image;

public class ImageToMatch extends JPanel {
	public ImageToMatch(Image image) {
		setLayout(new BorderLayout(0, 0));

		JButton btnNextImage = new JButton("Change Image");
		btnNextImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextImage();
			}
		});
		add(btnNextImage, BorderLayout.SOUTH);

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
		add(label, BorderLayout.CENTER);
	}

	public void nextImage(){
		((MatchImagesPanel)this.getParent()).nextImage();
	}

	private static final long serialVersionUID = -2492212483685159320L;

}
