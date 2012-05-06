package manager;

import gui.Settings;
import gui.ViewAllImages;
import gui.image.MatchImages;
import gui.image.ViewMatchings;
import gui.subimage.MatchSubImages;
import gui.subimage.ViewSubMatchings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class GUIManager extends JFrame {

	private static final long serialVersionUID = -4465177426944215121L;
	private MatchImages matchImages;
	private MatchSubImages matchSubImages;
	private ViewAllImages viewAllImages;
	private Settings settings;
	private ViewMatchings viewMatchings;
	private ViewSubMatchings viewSubMatchings;

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

		matchImages = new MatchImages();
		tabbedPane.addTab("Match Images", null, matchImages, null);

		matchSubImages = new MatchSubImages();
		tabbedPane.addTab("Match Sub Images", null, matchSubImages, null);

		viewMatchings = new ViewMatchings();
		tabbedPane.addTab("View Matchings", null, viewMatchings, null);

		viewSubMatchings = new ViewSubMatchings();
		tabbedPane.addTab("View Sub Matchings", null, viewSubMatchings, null);

		viewAllImages = new ViewAllImages();
		tabbedPane.addTab("View All Images", null, viewAllImages, null);

		settings = new Settings();
		tabbedPane.addTab("Settings", null, settings, null);
	}

	public MatchImages getMatchImages() {
		return matchImages;
	}

	public ViewAllImages getViewImages() {
		return viewAllImages;
	}

	public ViewMatchings getViewAllMatchings() {
		return viewMatchings;
	}

	public Settings getSettings() {
		return settings;
	}

	public MatchSubImages getMatchSubImages() {
		return matchSubImages;
	}

	public ViewAllImages getViewAllImages() {
		return viewAllImages;
	}

	public ViewMatchings getViewMatchings() {
		return viewMatchings;
	}

	public ViewSubMatchings getViewSubMatchings() {
		return viewSubMatchings;
	}

}
