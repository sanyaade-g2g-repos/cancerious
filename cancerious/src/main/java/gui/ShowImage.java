package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.CanceriousMain;
import entity.Image;
import entity.SubImage;

/**
 * view all images kısmında, bir imaj karesine tekabul eden panel
 * @author SEB
 */
public class ShowImage extends JPanel implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;

	public static final int VIEW_ALL_MODE=1;
	public static final int SELECT_SUBIMAGE_MODE=2;
	public static final int SHOW_SUBIMAGE_MODE=3;
	public static final int PLAIN_MODE=4;
	public static final int SELECT_SUBIMAGE_MULTISELECT_MODE=5;

	private int mode;
	private Image image;
	private SubImage subImage;
	private List<SubImage> multiSelects = new ArrayList<SubImage>();
	private SubImage currentSelect;
	private boolean drawingMode = false;

	public ShowImage(int mode, Image image) {
		super();
		this.mode = mode;
		this.image = image;
		updateView();
	}

	public ShowImage(int mode, SubImage subImage) {
		super();
		this.mode = mode;
		this.subImage = subImage;
		this.image = subImage.getImage();
		//		if (mode == SELECT_SUBIMAGE_MULTISELECT_MODE) {
		//			multiSelects = new ArrayList<SubImage>();
		//			currentSelect = null;
		//		}
		updateView();
	}

	public void updateView(){
		this.removeAll();
		setLayout(new BorderLayout(0, 0));

		JLabel icon = new JLabel("");
		icon.addMouseListener(this);
		icon.addMouseMotionListener(this);

		if(image.fileHandler==null){
			image.openHandler();
		}
		try {
			icon.setIcon(new ImageIcon(image.fileHandler.toURI().toURL()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		icon.setToolTipText(image.filename);
		add(icon);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(mode==VIEW_ALL_MODE){
			CanceriousMain.getGuiManager().getViewImages().hideAll();
			CanceriousMain.getGuiManager().getViewImages().add(new ImageRelations(image));
			CanceriousMain.getGuiManager().getViewImages().revalidate();
			CanceriousMain.getGuiManager().getViewImages().repaint();
		}
		else if ((e.getButton()==MouseEvent.BUTTON2 || e.getButton()==MouseEvent.BUTTON3)) {
			if (mode==SELECT_SUBIMAGE_MULTISELECT_MODE) {
				multiSelects.clear();
			}
			else if (mode==SELECT_SUBIMAGE_MODE) {
				subImage.reset();
			}
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (mode==SELECT_SUBIMAGE_MODE && e.getButton()==MouseEvent.BUTTON1) {
			this.subImage.setLeftTopX(e.getX());
			this.subImage.setLeftTopY(e.getY());
			this.subImage.setRightBottomX(e.getX());
			this.subImage.setRightBottomY(e.getY());
			drawingMode = true;
			repaint();
		}
		else if (mode==SELECT_SUBIMAGE_MULTISELECT_MODE && e.getButton()==MouseEvent.BUTTON1) {
			SubImage select = new SubImage(image);
			select.setLeftTopX(e.getX());
			select.setLeftTopY(e.getY());
			select.setRightBottomX(e.getX());
			select.setRightBottomY(e.getY());
			currentSelect = select;
			multiSelects.add(currentSelect);
			drawingMode = true;
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (mode==SELECT_SUBIMAGE_MODE && this.subImage!=null && drawingMode && e.getButton()==MouseEvent.BUTTON1) {
			drawingMode = false;
			this.subImage.setRightBottomX(e.getX());
			this.subImage.setRightBottomY(e.getY());
			repaint();
		}
		else if (mode==SELECT_SUBIMAGE_MULTISELECT_MODE && drawingMode && e.getButton()==MouseEvent.BUTTON1) {
			drawingMode = false;
			currentSelect.setRightBottomX(e.getX());
			currentSelect.setRightBottomY(e.getY());
			currentSelect = null;
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (mode==SELECT_SUBIMAGE_MODE && drawingMode && e.getButton()==MouseEvent.BUTTON1) {
			drawingMode = false;
			this.subImage.setRightBottomX(e.getX());
			this.subImage.setRightBottomY(e.getY());
			repaint();
		}
		else if (mode==SELECT_SUBIMAGE_MULTISELECT_MODE && drawingMode && e.getButton()==MouseEvent.BUTTON1) {
			drawingMode = false;
			currentSelect.setRightBottomX(e.getX());
			currentSelect.setRightBottomY(e.getY());
			currentSelect = null;
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (mode==SELECT_SUBIMAGE_MODE && drawingMode) {
			this.subImage.setRightBottomX(e.getX());
			this.subImage.setRightBottomY(e.getY());
			repaint();
		}
		else if (mode==SELECT_SUBIMAGE_MULTISELECT_MODE && drawingMode){
			currentSelect.setRightBottomX(e.getX());
			currentSelect.setRightBottomY(e.getY());
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g2d);
		//g2d = (Graphics2D) g2d.create();
		if(mode==SELECT_SUBIMAGE_MODE || mode==SHOW_SUBIMAGE_MODE && subImage.isValid()){
			//CanceriousLogger.debug("showImage.paintComponent(g): "+subImage.toString());
			g.setPaintMode();
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.black);
			//g2d.fill(new Rectangle(subImage.getLeftTopX(), subImage.getLeftTopY(), subImage.getRightBottomX()-subImage.getLeftTopX(), subImage.getRightBottomY()-subImage.getLeftTopY()));
			g2d.drawRect(subImage.getLeftTopX(), subImage.getLeftTopY(), subImage.getWidth(), subImage.getHeight());
		}
		if (mode==SELECT_SUBIMAGE_MULTISELECT_MODE) {
			for (SubImage select : multiSelects) {
				if (!select.isValid()) {
					continue;
				}
				g.setPaintMode();
				g2d.setStroke(new BasicStroke(2));
				g2d.setColor(Color.black);
				g2d.drawRect(select.getLeftTopX(), select.getLeftTopY(), select.getWidth(), select.getHeight());
			}
		}
		//super.paintComponent(g);
		//this.revalidate();
	}

	public SubImage getSubImage() {
		return subImage;
	}

	public List<SubImage> getMultiSelects() {
		List<SubImage> returnedList = new ArrayList<SubImage>();
		for (SubImage select : multiSelects) {
			if (select.isValid()) {
				returnedList.add(select);
			}
		}
		return returnedList;
	}

}
