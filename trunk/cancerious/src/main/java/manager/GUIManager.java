package manager;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUIManager extends JFrame{

	private static final long serialVersionUID = -4465177426944215121L;
	private JTextField textField;
	private JTextField textField_1;

	public GUIManager() {
		setAlwaysOnTop(true);
		setMinimumSize(new Dimension(400, 300));
		setTitle("Cancerious");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel matchImages = new JPanel();
		tabbedPane.addTab("Match Images", null, matchImages, null);
		
		JPanel viewImages = new JPanel();
		tabbedPane.addTab("View Images", null, viewImages, null);
		
		JPanel settings = new JPanel();
		tabbedPane.addTab("Settings", null, settings, null);
		settings.setLayout(new FormLayout(new ColumnSpec[] {
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
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblImageStoreLocation = new JLabel("Image Store Location");
		settings.add(lblImageStoreLocation, "2, 2, right, default");
		
		textField = new JTextField();
		settings.add(textField, "4, 2, fill, default");
		textField.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		settings.add(btnBrowse, "6, 2");
		
		JLabel lblDataStoreLocation = new JLabel("Feature Store Location");
		settings.add(lblDataStoreLocation, "2, 4, right, default");
		
		textField_1 = new JTextField();
		settings.add(textField_1, "4, 4, fill, default");
		textField_1.setColumns(10);
		
		JButton btnBrowse_1 = new JButton("Browse");
		settings.add(btnBrowse_1, "6, 4");
		
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
