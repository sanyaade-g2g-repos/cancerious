package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.CanceriousMain;
import entity.Image;

/**
 * view all images kısmında, bir imaj karesine tekabul eden panel
 * @author SEB
 */
public class ShowImage extends JPanel {

	private static final long serialVersionUID = 1L;

	public ShowImage(final Image image) {
		super();
		setLayout(new BorderLayout(0, 0));

		JLabel icon = new JLabel("");
		icon.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Write method
				CanceriousMain.getGuiManager().getViewImages().hideAll();
				CanceriousMain.getGuiManager().getViewImages().add(new ImageRelations(image));
			}
		});
		if(image.fileHandler==null){
			image.openHandler();
		}
		try {
			icon.setIcon(new ImageIcon(image.fileHandler.toURI().toURL()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		add(icon);

		JLabel filename = new JLabel(image.filename);
		add(filename, BorderLayout.SOUTH);

	}


}
