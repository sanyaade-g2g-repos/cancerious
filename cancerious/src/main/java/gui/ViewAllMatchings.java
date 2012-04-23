package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.CanceriousMain;
import util.BidirectionalAdjecencyMatrix;
import util.CanceriousLogger;
import entity.Image;

public class ViewAllMatchings extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> doneList;

	public ViewAllMatchings(){
		try {
			showAll();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void showAll() throws MalformedURLException{
		this.removeAll();

		this.setLayout(new BorderLayout());
		JPanel container = new JPanel();
		int imgCount = CanceriousMain.getGraphManager().imageSet.size();
		container.setBounds(0,0, 700, imgCount*50);

		JScrollPane jScrollPane = new JScrollPane(container);
		GridBagLayout gbl_container = new GridBagLayout();
		gbl_container.columnWidths = new int[]{0, 0, 0, 0};
		gbl_container.rowHeights = new int[]{0, 0};
		gbl_container.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_container.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		container.setLayout(gbl_container);

		JLabel lblImage1 = new JLabel("Image 1");
		GridBagConstraints gbc_lblImage1 = new GridBagConstraints();
		gbc_lblImage1.insets = new Insets(0, 0, 0, 5);
		gbc_lblImage1.gridx = 0;
		gbc_lblImage1.gridy = 0;
		container.add(lblImage1, gbc_lblImage1);

		JLabel lblImage2 = new JLabel("Image 2");
		GridBagConstraints gbc_lblImage2 = new GridBagConstraints();
		gbc_lblImage2.insets = new Insets(0, 0, 0, 5);
		gbc_lblImage2.gridx = 1;
		gbc_lblImage2.gridy = 0;
		container.add(lblImage2, gbc_lblImage2);

		JLabel lblRating = new JLabel("Rating");
		GridBagConstraints gbc_lblRating = new GridBagConstraints();
		gbc_lblRating.gridx = 2;
		gbc_lblRating.gridy = 0;
		container.add(lblRating, gbc_lblRating);

		BidirectionalAdjecencyMatrix choices = CanceriousMain.getGraphManager().getChoices();
		int imageCount = CanceriousMain.getGraphManager().imageSet.size();
		doneList = new ArrayList<String>();

		int rowIndex = 1;
		for (int i = 0; i < imageCount; i++) {
			final Image image1 = CanceriousMain.getGraphManager().imageSet.get(i);
			for (int j=0; j<choices.getAdjecencies(i).length;j++){
				int rating = (int)choices.getAdjecencies(i)[j];
				final Image image2 = CanceriousMain.getGraphManager().imageSet.get(j);
				String doneString = String.format("%d,%d", i, j);
				String doneStringReversed = String.format("%d,%d", j, i);

				if(rating>=0 && i!=j && !doneList.contains(doneStringReversed)){

					JLabel image1lbl = new JLabel();
					image1lbl.setIcon(new ImageIcon(image1.fileHandler.toURI().toURL()));
					image1lbl.setToolTipText(image1.filename);
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.insets = new Insets(3, 0, 0, 0);
					gbc.gridx = 0;
					gbc.gridy = rowIndex;
					container.add(image1lbl, gbc);

					JLabel image2lbl = new JLabel();
					image2lbl.setIcon(new ImageIcon(image2.fileHandler.toURI().toURL()));
					image2lbl.setToolTipText(image2.filename);
					gbc = new GridBagConstraints();
					gbc.insets = new Insets(3, 0, 0, 0);
					gbc.gridx = 1;
					gbc.gridy = rowIndex;
					container.add(image2lbl, gbc);

					JSlider slider = new JSlider();
					slider.setSnapToTicks(true);
					slider.setPaintLabels(true);
					slider.setMajorTickSpacing(1);
					slider.setMinimum(-1);
					slider.setMaximum(2);
					slider.setValue(rating);
					slider.addChangeListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent arg0) {
							CanceriousMain.getGraphManager().setChoice(image1, image2, ((JSlider)arg0.getSource()).getValue());
						}
					});
					gbc = new GridBagConstraints();
					gbc.insets = new Insets(3, 0, 0, 0);
					gbc.gridx = 2;
					gbc.gridy = rowIndex;
					container.add(slider, gbc);

					rowIndex++;
					doneList.add(doneString);
				}
			}
		}

		this.add(jScrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					showAll();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		});
		buttonPanel.add(refreshBtn);

		JButton exportBtn = new JButton("Export To File");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(ViewAllMatchings.this);
				if(fc.getSelectedFile()!=null){
					try{
						BufferedWriter bw = new BufferedWriter(new FileWriter(fc.getSelectedFile()));
						String linesep = System.getProperty("line.separator");
						bw.write(new Date().toString()+linesep);
						bw.write("image1 image2 rating"+linesep);
						BidirectionalAdjecencyMatrix choices = CanceriousMain.getGraphManager().getChoices();
						for(String done:doneList){
							int i = Integer.parseInt(done.substring(0, done.indexOf(",")));
							int j = Integer.parseInt(done.substring(done.indexOf(",")+1));
							int rating = (int)choices.getAdjecencies(i)[j];
							String f1name = CanceriousMain.getGraphManager().imageSet.get(i).filename;
							String f2name = CanceriousMain.getGraphManager().imageSet.get(j).filename;
							String line = String.format("%s %s %d%s", f1name,f2name,rating,linesep);
							bw.write(line);
						}
						bw.close();
						JOptionPane.showMessageDialog(ViewAllMatchings.this, "Export done successfully.");
					}catch (Exception e) {
						CanceriousLogger.error(e);
					}
				}
			}
		});
		buttonPanel.add(exportBtn);
		this.add(buttonPanel, BorderLayout.NORTH);

		revalidate();
		repaint();

	}
}
