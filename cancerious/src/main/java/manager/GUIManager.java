package manager;

import gui.MatchImagesPanel;
import gui.SettingsPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIManager extends JFrame {

	private static final long serialVersionUID = -4465177426944215121L;


	public GUIManager() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		setVisible(true);
		setMinimumSize(new Dimension(700, 550));
		setTitle("Cancerious");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel matchImages = new MatchImagesPanel();
		tabbedPane.addTab("Match Images", null, matchImages, null);

		JPanel viewImages = new JPanel();
		tabbedPane.addTab("View Images", null, viewImages, null);

		JPanel settings = new SettingsPanel();
		tabbedPane.addTab("Settings", null, settings, null);
	}

}
