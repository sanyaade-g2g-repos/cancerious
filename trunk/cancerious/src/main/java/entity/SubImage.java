package entity;

import java.io.IOException;
import java.io.Serializable;

import main.CanceriousMain;


public class SubImage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	int leftTopX, leftTopY, rightBottomX, rightBottomY;
	Image image;

	public SubImage(int leftTopX, int leftTopY, int rightBottomX, int rightBottomY, Image image) {
		super();
		this.leftTopX = leftTopX;
		this.leftTopY = leftTopY;
		this.rightBottomX = rightBottomX;
		this.rightBottomY = rightBottomY;
		this.image = image;
	}

	public SubImage(Image image) {
		super();
		this.image = image;
		this.leftTopX = 0;
		this.leftTopY = 0;
		this.rightBottomX = 0;
		this.rightBottomY = 0;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getLeftTopX() {
		return leftTopX;
	}

	public void setLeftTopX(int leftTopX) {
		this.leftTopX = leftTopX;
	}

	public int getLeftTopY() {
		return leftTopY;
	}

	public void setLeftTopY(int leftTopY) {
		this.leftTopY = leftTopY;
	}

	public int getRightBottomX() {
		return rightBottomX;
	}

	public void setRightBottomX(int rightBottomX) {
		this.rightBottomX = rightBottomX;
	}

	public int getRightBottomY() {
		return rightBottomY;
	}

	public void setRightBottomY(int rightBottomY) {
		this.rightBottomY = rightBottomY;
	}

	@Override
	public String toString() {
		return "SubImage [leftTopX=" + leftTopX + ", leftTopY=" + leftTopY + ", rightBottomX="
				+ rightBottomX + ", rightBottomY=" + rightBottomY + ", image=" + image + "]";
	}

	public void reset() {
		this.leftTopX = 0;
		this.leftTopY = 0;
		this.rightBottomX = 0;
		this.rightBottomY = 0;
	}

	public int getWidth(){
		return rightBottomX - leftTopX;
	}

	public int getHeight(){
		return rightBottomY - leftTopY;
	}

	public boolean isValid(){
		boolean valid=true;
		if (leftTopX >= rightBottomX || leftTopY >= rightBottomY) {
			valid = false;
		}
		else if (getWidth() * getHeight() < 10) {
			//the area should not be smaller than 10 px2
			valid = false;
		}
		return valid;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeInt(leftTopY);
		out.writeInt(leftTopY);
		out.writeInt(rightBottomX);
		out.writeInt(rightBottomY);
		out.writeInt(image.id);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		leftTopX = in.readInt();
		leftTopY = in.readInt();
		rightBottomX = in.readInt();
		rightBottomY = in.readInt();
		int id = in.readInt();
		image = CanceriousMain.getGraphManager().imageList.get(id);
	}

}
