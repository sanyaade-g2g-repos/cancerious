package manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import main.CanceriousMain;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Insets;

public class GUIManager extends JFrame {

	private static final long serialVersionUID = -4465177426944215121L;
	private JTextField imageStoreField;
	private JTextField featureStoreField;

	public GUIManager() {
		setVisible(true);
		setAlwaysOnTop(true);
		setMinimumSize(new Dimension(400, 300));
		setTitle("Cancerious");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel matchImages = new JPanel();
		tabbedPane.addTab("Match Images", null, matchImages, null);
		GridBagLayout gbl_matchImages = new GridBagLayout();
		gbl_matchImages.columnWidths = new int[] { 0, 0, 0 };
		gbl_matchImages.rowHeights = new int[] { 0, 0, 0 };
		gbl_matchImages.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_matchImages.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		matchImages.setLayout(gbl_matchImages);
		
				JLabel lblNewLabel = new JLabel("New label");
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.gridx = 1;
				gbc_lblNewLabel.gridy = 0;
				matchImages.add(lblNewLabel, gbc_lblNewLabel);

		JPanel viewImages = new JPanel();
		tabbedPane.addTab("View Images", null, viewImages, null);

		JPanel settings = new JPanel();
		tabbedPane.addTab("Settings", null, settings, null);
		settings.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(1dlu;default)"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblImageStoreLocation = new JLabel("Image Store Location");
		settings.add(lblImageStoreLocation, "2, 2, right, default");

		imageStoreField = new JTextField(CanceriousMain.getConfigurationManager().imageStorePath);
		settings.add(imageStoreField, "4, 2, fill, default");
		imageStoreField.setColumns(10);

		JButton imageStoreBrowse = new JButton("Browse");
		imageStoreBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(CanceriousMain.getGuiManager());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (!file.isDirectory()) {
						JOptionPane.showMessageDialog(CanceriousMain.getGuiManager(),
								"Please specify a directory. ");
					} else {
						CanceriousMain.getConfigurationManager().imageStorePath = file.getPath();
						imageStoreField.setText(file.getPath());
					}
				}
			}
		});
		settings.add(imageStoreBrowse, "6, 2");

		JLabel lblDataStoreLocation = new JLabel("Feature Store Location");
		settings.add(lblDataStoreLocation, "2, 4, right, default");

		featureStoreField = new JTextField(CanceriousMain.getConfigurationManager().featureStorePath);
		settings.add(featureStoreField, "4, 4, fill, default");
		featureStoreField.setColumns(10);

		JButton featureStoreBrowse = new JButton("Browse");
		featureStoreBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(CanceriousMain.getGuiManager());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (!file.isDirectory()) {
						JOptionPane.showMessageDialog(CanceriousMain.getGuiManager(),
								"Please specify a directory. ");
					} else {
						CanceriousMain.getConfigurationManager().featureStorePath = file.getPath();
						featureStoreField.setText(file.getPath());
					}
				}
			}
		});
		settings.add(featureStoreBrowse, "6, 4");

		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		JLabel lblReloadAllData = new JLabel("Reload All Data");
		settings.add(lblReloadAllData, "2, 6");
		settings.add(btnReload, "4, 6, left, default");

		JButton btnSaveSettings = new JButton("Save Settings");
		settings.add(btnSaveSettings, "2, 10");

		JButton btnDiscardSettings = new JButton("Discard Settings");
		settings.add(btnDiscardSettings, "4, 10, left, default");
	}

}
