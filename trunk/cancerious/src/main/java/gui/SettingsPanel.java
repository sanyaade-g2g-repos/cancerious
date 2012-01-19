package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.CanceriousMain;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class SettingsPanel extends JPanel {
	private JTextField imageStoreField;
	private JTextField featureStoreField;

	public SettingsPanel() {
		super();
		this.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(1dlu;default)"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblImageStoreLocation = new JLabel("Image Store Location");
		this.add(lblImageStoreLocation, "2, 2, right, default");

		imageStoreField = new JTextField(CanceriousMain.getConfigurationManager().imageStorePath);
		this.add(imageStoreField, "4, 2, fill, default");
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
						JOptionPane.showMessageDialog(CanceriousMain.getGuiManager(), "Please specify a directory. ");
					} else {
						CanceriousMain.getConfigurationManager().imageStorePath = file.getPath();
						imageStoreField.setText(file.getPath());
					}
				}
			}
		});
		this.add(imageStoreBrowse, "6, 2");

		JLabel lblDataStoreLocation = new JLabel("Feature Store Location");
		this.add(lblDataStoreLocation, "2, 4, right, default");

		featureStoreField = new JTextField(CanceriousMain.getConfigurationManager().featureStorePath);
		this.add(featureStoreField, "4, 4, fill, default");
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
						JOptionPane.showMessageDialog(CanceriousMain.getGuiManager(), "Please specify a directory. ");
					} else {
						CanceriousMain.getConfigurationManager().featureStorePath = file.getPath();
						featureStoreField.setText(file.getPath());
					}
				}
			}
		});
		this.add(featureStoreBrowse, "6, 4");

		JLabel lblDbStoreLocation = new JLabel("Cancerious Data Storage");
		add(lblDbStoreLocation, "2, 6, right, default");

		dbStoreField = new JTextField(CanceriousMain.getConfigurationManager().dbStorePath);
		add(dbStoreField, "4, 6, fill, default");
		dbStoreField.setColumns(10);

		JButton dbStoreBrowse = new JButton("Browse");
		add(dbStoreBrowse, "6, 6");

		JLabel lblReloadAllData = new JLabel("Reload All Data");
		this.add(lblReloadAllData, "2, 8, right, default");

		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		this.add(btnReload, "4, 8, left, default");

		JButton btnSaveSettings = new JButton("Save Settings");
		this.add(btnSaveSettings, "2, 12");

		JButton btnDiscardSettings = new JButton("Discard Settings");
		this.add(btnDiscardSettings, "4, 12, left, default");

	}

	private static final long serialVersionUID = -8928464335165056558L;
	private JTextField dbStoreField;

}
