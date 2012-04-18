package manager;

import gui.MatchImagesPanel;
import gui.SettingsPanel;
import gui.ViewAllMatchings;
import gui.ViewImagesPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class GUIManager extends JFrame {

	private static final long serialVersionUID = -4465177426944215121L;
	private MatchImagesPanel matchImages;
	private ViewImagesPanel viewImages;
	private SettingsPanel settings;
	private ViewAllMatchings viewAllMatchings;

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

		matchImages = new MatchImagesPanel();
		tabbedPane.addTab("Match Images", null, matchImages, null);

		viewImages = new ViewImagesPanel();
		tabbedPane.addTab("View Images", null, viewImages, null);
		
		viewAllMatchings = new ViewAllMatchings();
		tabbedPane.addTab("View All Matchings", null, viewAllMatchings, null);

		settings = new SettingsPanel();
		tabbedPane.addTab("Settings", null, settings, null);
	}

	public MatchImagesPanel getMatchImages() {
		return matchImages;
	}

	public ViewImagesPanel getViewImages() {
		return viewImages;
	}

	public ViewAllMatchings getViewAllMatchings() {
		return viewAllMatchings;
	}

	public SettingsPanel getSettings() {
		return settings;
	}

}
