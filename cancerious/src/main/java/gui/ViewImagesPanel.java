package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.CanceriousMain;
import entity.Image;


/**
 * sistemdeki tüm imajları gosteren panel, tüm imajlar grid layout seklinde gozukur
 * @see ShowImage
 * @author SEB
 */
public class ViewImagesPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ViewImagesPanel(){
		showAll();
	}

	public void showAll(){
		hideAll();

		this.setLayout(new BorderLayout());
		JPanel container = new JPanel(new GridLayout(0, 3, 0, 0));
		int imgCount = CanceriousMain.getGraphManager().imageSet.size();
		container.setBounds(0,0, 700, imgCount*50);

		JScrollPane jScrollPane = new JScrollPane(container);


		for (Image img : CanceriousMain.getGraphManager().imageSet) {
			container.add(new ShowImage(img));
		}

		this.add(jScrollPane, BorderLayout.CENTER);
	}

	public void hideAll(){
		this.removeAll();
	}


}
