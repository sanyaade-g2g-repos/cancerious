package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.CanceriousMain;
import entity.Image;


public class ViewImagesPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ViewImagesPanel(){
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


}
