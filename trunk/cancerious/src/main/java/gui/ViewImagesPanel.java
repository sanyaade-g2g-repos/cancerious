package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.CanceriousMain;

import entity.Image;


public class ViewImagesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image image;
	
	public ViewImagesPanel(){
		
		reviewImage();

	}
	
	public void reviewImage(){
		this.removeAll();

		
		JLabel label = new JLabel("");
		
		if(image.fileHandler==null){
				image.openHandler();
			}
			try {
				label.setIcon(new ImageIcon(image.fileHandler.toURI().toURL()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
		for(int i=0; i<36; i++){
		
			
		
		}
		
		add(label);
		
	}


}
