package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.CanceriousMain;
import manager.ConfigurationManager;
import manager.GraphManager;
import util.CanceriousLogger;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class SettingsPanel extends JPanel {
	private JTextField imageStoreField;
	private JTextField featureStoreField;

	public SettingsPanel() {
		super();

		ConfigurationManager conf = CanceriousMain.getConfigurationManager();

		this.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(1dlu;default)"),},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblImageStoreLocation = new JLabel("Image Store Location");
		this.add(lblImageStoreLocation, "2, 2, right, default");

		imageStoreField = new JTextField(CanceriousMain.getConfigurationManager().imageStorePath);
		this.add(imageStoreField, "4, 2, fill, default");
		imageStoreField.setColumns(10);

		JButton imageStoreBrowse = new JButton("Browse");
		imageStoreBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(CanceriousMain.getGuiManager());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (!file.isDirectory()) {
						JOptionPane.showMessageDialog(CanceriousMain.getGuiManager(), "Please specify a directory. ");
					} else {
						//						try {
						//							//TODO CanceriousMain.getConfigurationManager().imageStoreURL = file.toURI().toURL();
						//						} catch (MalformedURLException e) {
						//							JOptionPane.showMessageDialog(SettingsPanel.this, "Image store is invalid.");
						//							CanceriousLogger.warn(e);
						//						}
						//TODO RELOAD?
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
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(CanceriousMain.getGuiManager());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (!file.isDirectory()) {
						JOptionPane.showMessageDialog(CanceriousMain.getGuiManager(), "Please specify a directory. ");
					} else {
						//						try {
						//							//TODO CanceriousMain.getConfigurationManager().featureStoreURL = file.toURI().toURL();
						//						} catch (MalformedURLException e) {
						//							JOptionPane.showMessageDialog(SettingsPanel.this, "Feature store is invalid.");
						//							CanceriousLogger.warn(e);
						//						}
						//TODO LOAD FEATURES?
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

		JLabel lblReloadAllData = new JLabel("Distribution");
		this.add(lblReloadAllData, "2, 8, right, default");

		JPanel panel = new JPanel();
		add(panel, "4, 8, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:default"),},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));

		JLabel lblFeature = new JLabel("Feature");
		panel.add(lblFeature, "2, 2, right, default");

		fromFeature = new JTextField();
		fromFeature.setText(GraphManager.FROM_FEATURE+"");
		panel.add(fromFeature, "4, 2, fill, default");
		fromFeature.setColumns(10);

		JLabel lblBfs = new JLabel("BFS");
		panel.add(lblBfs, "2, 4, right, default");

		fromBFS = new JTextField();
		fromBFS.setText(GraphManager.FROM_BFS+"");
		panel.add(fromBFS, "4, 4, fill, default");
		fromBFS.setColumns(10);

		JLabel lblRandom = new JLabel("Random");
		panel.add(lblRandom, "2, 6, right, default");

		fromRandom = new JTextField();
		fromRandom.setText(GraphManager.FROM_RANDOM+"");
		panel.add(fromRandom, "4, 6, fill, default");
		fromRandom.setColumns(10);

		JLabel lblFeatures = new JLabel("Features");
		add(lblFeatures, "2, 10, right, top");

		featuresPanel = new JPanel();
		featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.PAGE_AXIS));
		add(featuresPanel, "4, 10, fill, fill");

		File featureStore = conf.getFeatureStore();
		try{
			for(File child:featureStore.listFiles()){
				if (!child.getName().equals(GraphManager.FEATURE_STORE_TXT)) {
					JCheckBox cb = new JCheckBox(child.getName());
					featuresPanel.add(cb);
				}
			}
			File featureStoreTxt = CanceriousMain.getConfigurationManager().getFeatureAsFile(GraphManager.FEATURE_STORE_TXT);
			BufferedReader br = new BufferedReader(new FileReader(featureStoreTxt));
			String line;
			while((line=br.readLine())!=null){
				for(Component c : featuresPanel.getComponents()){
					if(c instanceof JCheckBox){
						if(((JCheckBox) c).getText().equals(line)){
							((JCheckBox) c).setSelected(true);
							break;
						}
					}
				}
			}
			br.close();
		}catch (Exception e) {

		}

		JButton btnSaveSettings = new JButton("Save & Reload");
		btnSaveSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					File featureStoreTxt = CanceriousMain.getConfigurationManager().getFeatureAsFile(GraphManager.FEATURE_STORE_TXT);
					BufferedWriter bw = new BufferedWriter(new FileWriter(featureStoreTxt));
					for(Component c : featuresPanel.getComponents()){
						if(c instanceof JCheckBox && ((JCheckBox) c).isSelected()){
							bw.write(String.format("%s%n", ((JCheckBox) c).getText()));
						}
					}
					bw.close();
					CanceriousMain.getGraphManager().loadFeatures();
					JOptionPane.showMessageDialog(SettingsPanel.this, "Settings saved and features reloaded.");
				}catch (Exception e2) {
					CanceriousLogger.error(e2);
				}
			}
		});
		this.add(btnSaveSettings, "2, 12");

		//		JButton btnDiscardSettings = new JButton("Discard Settings");
		//		this.add(btnDiscardSettings, "4, 12, left, default");

	}

	private static final long serialVersionUID = -8928464335165056558L;
	private JTextField dbStoreField;
	private JPanel featuresPanel;
	private JTextField fromFeature;
	private JTextField fromBFS;
	private JTextField fromRandom;

}
