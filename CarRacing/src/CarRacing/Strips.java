package CarRacing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Strips {
	
	private int dy = 10;
	private Rectangle line;
	private Board brd;
	
	public Strips(Board brd,int x, int y) {
		this.brd = brd;
		// constructor her calistiginda parametrelere gore rectangle(serit) ciz
		line = new Rectangle(x, y, 10,40);
	}
	
	public void drawLines(Graphics2D g2d) {
		
		// seritlerin rengi
		g2d.setColor(Color.white);
		
		// seritleri ici dolu bir sekilde ciz
		g2d.fillRect((int) line.x, (int)line.y, (int)line.width, (int)line.height);	
	}
	
	public void moveLines() {	
		if(line.y > brd.getHeight()) {
			// cizgiler panelin disina cikinca cizgileri -50 den baslat
			line.y = -50;
			return;
		}
		
		// cizgilerin hizi
		line.y += dy;
	}
	
	
	
	
}
