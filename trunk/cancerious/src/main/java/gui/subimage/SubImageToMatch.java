package gui.subimage;

import entity.Image;
import entity.SubImage;
import gui.ShowImage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.CanceriousMain;

public class SubImageToMatch extends JPanel {
	private static final long serialVersionUID = 1L;
	private ShowImage showImage;

	public SubImageToMatch(Image image) {
		setLayout(new BorderLayout(0, 0));

		JPanel buttonPanel = new JPanel(new FlowLayout());

		JButton btnNextImage = new JButton("Change Image");
		btnNextImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nextImage();
			}
		});
		buttonPanel.add(btnNextImage);

		JButton btnSaveAll = new JButton("Save All");
		btnSaveAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAll();
			}
		});
		buttonPanel.add(btnSaveAll);

		add(buttonPanel, BorderLayout.SOUTH);

		showImage = new ShowImage(ShowImage.SELECT_SUBIMAGE_MODE, new SubImage(image));
		add(showImage, BorderLayout.CENTER);
	}

	public void nextImage(){
		CanceriousMain.getGuiManager().getMatchSubImages().saveAll(false);
		CanceriousMain.getGuiManager().getMatchSubImages().nextImage();
	}

	public void saveAll(){
		CanceriousMain.getGuiManager().getMatchSubImages().saveAll(true);
	}

	public ShowImage getShowImage() {
		return showImage;
	}

}
