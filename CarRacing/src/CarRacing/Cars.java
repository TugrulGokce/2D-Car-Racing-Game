package CarRacing;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Cars {

	public int x;
	public int y;
	public int dy;
	public int dx;
	private ImageSourceManangement imageSourceMng;

	private Rectangle myCar = new Rectangle();
	private Rectangle otherCars = new Rectangle();

	public Cars(String path, int x, int y) {
		this.imageSourceMng = ImageSourceStore.get().getImageUpload(path);
		this.x = x;
		this.y = y;
	}

	public void move() {
		x += dx;
		y += dy;
	}

	public boolean collisionControl(Cars collision) {
		
		// kendi arabamin bound'unu al
		myCar.setBounds((int) x, (int) y, (int) imageSourceMng.getWidth(), (int) imageSourceMng.getHeight());
		
		// diger arabalarin bound'unu al
		otherCars.setBounds((int) collision.x, (int) collision.y, (int) collision.imageSourceMng.getWidth(),
				(int) collision.imageSourceMng.getHeight());
		return myCar.intersects(otherCars); // iki arac carpisiyorsa true carpismiyorsa false
	}
	
	public abstract void collisionControls(Cars collision);
	
	public void drawCars(Graphics2D g2d) {
		this.imageSourceMng.draw(g2d, x, y); // arabayi ekrana cizdir
	}

	public void getHorizontalSpeed(int dx) {
		this.dx = dx;
	}

	public int getWidth() {
		return imageSourceMng.getWidth();
	}

	public int getHeight() {
		return imageSourceMng.getHeight();
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
