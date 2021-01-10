package CarRacing;

import java.awt.Graphics2D;
import java.awt.Image;

public class ImageSourceManangement {
	
	private Image image;

	public ImageSourceManangement(Image image) {
		this.image = image;
	}

	public int getWidth() {
		return this.image.getWidth(null);
	}

	public int getHeight() {
		return this.image.getHeight(null);
	}

	public Image getImage() {
		return image;
	}

	public void draw(Graphics2D g, int x, int y) {
		g.drawImage(image, x, y, null);
	}

}
