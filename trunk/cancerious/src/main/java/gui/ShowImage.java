package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.CanceriousMain;
import util.CanceriousLogger;
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

	private int mode;
	private Image image;
	private SubImage subImage;
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

		//		this.addMouseListener(this);
		//		this.addMouseMotionListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(mode==VIEW_ALL_MODE){
			CanceriousMain.getGuiManager().getViewImages().hideAll();
			CanceriousMain.getGuiManager().getViewImages().add(new ImageRelations(image));
			CanceriousMain.getGuiManager().getViewImages().revalidate();
			CanceriousMain.getGuiManager().getViewImages().repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (mode==SELECT_SUBIMAGE_MODE && this.subImage!=null) {
			this.subImage.setLeftTopX(e.getX());
			this.subImage.setLeftTopY(e.getY());
			this.subImage.setRightBottomX(e.getX());
			this.subImage.setRightBottomY(e.getY());
			drawingMode = true;
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Write method
		if (mode==SELECT_SUBIMAGE_MODE && this.subImage!=null && drawingMode) {
			drawingMode = false;
			this.subImage.setRightBottomX(e.getX());
			this.subImage.setRightBottomY(e.getY());
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Write method

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Write method
		if (mode==SELECT_SUBIMAGE_MODE && this.subImage!=null && drawingMode) {
			drawingMode = false;
			this.subImage.setRightBottomX(e.getX());
			this.subImage.setRightBottomY(e.getY());
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Write method
		if (mode==SELECT_SUBIMAGE_MODE && this.subImage!=null && drawingMode) {
			this.subImage.setRightBottomX(e.getX());
			this.subImage.setRightBottomY(e.getY());
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Write method

	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g2d);
		//g2d = (Graphics2D) g2d.create();
		if(subImage != null){
			CanceriousLogger.debug("showImage.paintComponent(g): "+subImage.toString());
			g.setPaintMode();
			//g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.black);
			//g2d.fill(new Rectangle(subImage.getLeftTopX(), subImage.getLeftTopY(), subImage.getRightBottomX()-subImage.getLeftTopX(), subImage.getRightBottomY()-subImage.getLeftTopY()));
			g2d.drawRect(subImage.getLeftTopX(), subImage.getLeftTopY(), subImage.getRightBottomX()-subImage.getLeftTopX(), subImage.getRightBottomY()-subImage.getLeftTopY());
		}
		//super.paintComponent(g);
		//this.revalidate();
	}


}
