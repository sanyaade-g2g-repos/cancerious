package entity;

public class SubImage {

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

}
