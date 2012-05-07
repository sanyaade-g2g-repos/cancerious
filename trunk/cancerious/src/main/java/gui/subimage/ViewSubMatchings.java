package gui.subimage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.CanceriousMain;
import util.CanceriousLogger;
import entity.SubImageMatch;
import gui.ShowImage;

public class ViewSubMatchings extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<String> doneList;

	public ViewSubMatchings() {
		updateView();
	}

	public void updateView() {
		this.removeAll();

		this.setLayout(new BorderLayout());
		JPanel container = new JPanel();
		int imgCount = CanceriousMain.getGraphManager().imageList.size();
		container.setBounds(0, 0, 700, imgCount * 50);

		JScrollPane jScrollPane = new JScrollPane(container);
		GridBagLayout gbl_container = new GridBagLayout();
		gbl_container.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_container.rowHeights = new int[] { 0, 0 };
		gbl_container.columnWeights = new double[] { 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_container.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
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

		JLabel lblRating = new JLabel("Action");
		GridBagConstraints gbc_lblRating = new GridBagConstraints();
		gbc_lblRating.gridx = 2;
		gbc_lblRating.gridy = 0;
		container.add(lblRating, gbc_lblRating);

		doneList = new ArrayList<String>();
		int rowIndex = 1;
		List<SubImageMatch> subImageMatches = CanceriousMain.getGraphManager()
				.getSubImageMatches();
		for (final SubImageMatch subImageMatch : subImageMatches) {
			ShowImage showImage1 = new ShowImage(ShowImage.SHOW_SUBIMAGE_MODE,
					subImageMatch.getMatch1());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(3, 0, 0, 0);
			gbc.gridx = 0;
			gbc.gridy = rowIndex;
			container.add(showImage1, gbc);

			ShowImage showImage2 = new ShowImage(ShowImage.SHOW_SUBIMAGE_MODE,
					subImageMatch.getMatch2());
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(3, 0, 0, 0);
			gbc.gridx = 1;
			gbc.gridy = rowIndex;
			container.add(showImage2, gbc);

			JButton removeButton = new JButton("Remove");
			removeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					CanceriousMain.getGraphManager().getSubImageMatches()
							.remove(subImageMatch);
					CanceriousMain.getGraphManager().writeChoices();
					updateView();
				}
			});
			gbc = new GridBagConstraints();
			gbc.insets = new Insets(3, 0, 0, 0);
			gbc.gridx = 2;
			gbc.gridy = rowIndex;
			container.add(removeButton, gbc);

			rowIndex++;

			doneList.add(String.format("%s[%d,%d,%d,%d] %s[%d,%d,%d,%d]",
					subImageMatch.getMatch1().getImage().filename,
					subImageMatch.getMatch1().getLeftTopX(), 
					subImageMatch.getMatch1().getLeftTopY(), 
					subImageMatch.getMatch1().getRightBottomX(), 
					subImageMatch.getMatch1().getRightBottomY(), 
					subImageMatch.getMatch2().getImage().filename, 
					subImageMatch.getMatch2().getLeftTopX(), 
					subImageMatch.getMatch2().getLeftTopY(), 
					subImageMatch.getMatch2().getRightBottomX(), 
					subImageMatch.getMatch2().getRightBottomY()));
		}

		this.add(jScrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateView();
			}
		});
		buttonPanel.add(refreshBtn);

		JButton exportBtn = new JButton("Export To File");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(ViewSubMatchings.this);
				if (fc.getSelectedFile() != null) {
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(fc.getSelectedFile()));
						String linesep = System.getProperty("line.separator");
						bw.write(new Date().toString() + linesep);
						bw.write(CanceriousMain.getConfigurationManager().getImageStore().getPath() + linesep);
						bw.write("image1[leftTopX,leftTopY,rightBottomX,rightBottomY] image2[leftTopX,leftTopY,rightBottomX,rightBottomY]" + linesep);
						for (String done : doneList) {
							String line = String.format("%s%s", done, linesep);
							bw.write(line);
						}
						bw.close();
						JOptionPane.showMessageDialog(ViewSubMatchings.this, "Export done successfully.");
					} catch (Exception e) {
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
