package main;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author selimerenbekce
 *
 */
public class ImageTest extends JFrame {
	
	private static final long serialVersionUID = 1L;

	JButton uploadButton;
	JButton displayButton;
	JLabel status;
	String imagePath;

	public static void main(String[] args) {
		new ImageTest();
	}
	
	public ImageTest() {
		
		uploadButton = new JButton("Upload");
		displayButton = new JButton("Display");
		status = new JLabel("Waiting to load an image.");

		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				uploadButtonAction();
			}
		});

		displayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayButtonAction();
			}
		});
		displayButton.setEnabled(false);

		getContentPane().setLayout(new GridLayout());
		this.setSize(500, 50);
		this.setResizable(false);
		getContentPane().add(uploadButton);
		getContentPane().add(displayButton);
		getContentPane().add(status);
		
		setVisible(true);

	}

	private void uploadButtonAction() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"JPG,GIF,PNG Images", "jpg", "gif", "png");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			imagePath = chooser.getSelectedFile().getPath(); 
			status.setText("Image loaded.");
			displayButton.setEnabled(true);
		}
	}

	private void displayButtonAction() {
		ImageIcon image = new ImageIcon(imagePath);
		JOptionPane.showMessageDialog(this, image);
	}

}
