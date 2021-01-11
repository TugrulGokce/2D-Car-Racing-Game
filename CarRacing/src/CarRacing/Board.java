package CarRacing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = -6121224303972034791L;
	private Timer timer;
	private int width, height;
	public int score;
	private final int DELAY = 40;
	private Cars myCar;
	private Cars otherCar;
	private ArrayList<Cars> cars = new ArrayList<Cars>(); // Arabalarin listesi
	private int myCarSpeed = 11;
	private ArrayList<Strips> strips = new ArrayList<Strips>(); // Seritlerin listesi
																
	private int carsInt = 0;
	private boolean gameover; // oyun bitti mi kontrolu
	private boolean is_Start; // oyun baslangic ekrani kontrolu
	private ImageSourceManangement restart;
	private ImageSourceManangement startLogo;
	private ImageSourceManangement[] key;

	// Ana arabadan bagimsiz, diger araclarin pozisyonlari
	private int[][] positions = { { 125, -100 }, { 125, -750 }, { 125, -1200 }, // 1.yol
			{ 215, -520 }, { 215, -900 }, { 215, -1550 }, // 2.yol
			{ 305, -400 }, { 305, -1400 }, { 305, -1400 }, // 3.yol
			{ 395, -700 }, { 395, -950 }, { 395, -1150 }, // 4.yol
			{ 485, -250 }, { 485, -700 }, { 485, -1200 } // 5.yol
	};

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		initBoard();
		myCarLoadImage();
		otherCarLoad();
		addStrips();
		restartLoad();
		startLogoLoadImage();
		keyImageLoad();
	}

	// sag ve sol ok tuslarin resimlerini oku
	private void keyImageLoad() {
		key = new ImageSourceManangement[3];
		key[0] = ImageSourceStore.get().getImageUpload("Images/p_key.png");
		key[1] = ImageSourceStore.get().getImageUpload("Images/right_arrow_key.png");
		key[2] = ImageSourceStore.get().getImageUpload("Images/left_arrow_key.png");
	}

	// restart logosunu yukleme
	private void restartLoad() {
		this.restart = ImageSourceStore.get().getImageUpload("Images/restart_button.png");
	}

	// baslangic ekranin da ki yazinin resmini yukleme(2d Car Racing)
	private void startLogoLoadImage() {
		this.startLogo = ImageSourceStore.get().getImageUpload("Images/template.png");
	}

	// arabalarin pozisyonlarini alma
	private void otherCarLoad() {
		for (int[] pos : positions) {
			carLoadImage(pos[0], pos[1]);
		}
	}

	// Ana arabadan bagimsiz, diger araclarin resimlerini yukleme
	private void carLoadImage(int x, int y) {
		if (carsInt % 3 == 0)
			otherCar = new OtherCars(this, "Images/yellow_Car.png", x, y);
		else if (carsInt % 3 == 1)
			otherCar = new OtherCars(this, "Images/black_Car.png", x, y);
		else if (carsInt % 3 == 2)
			otherCar = new OtherCars(this, "Images/gray_Car.png", x, y);
		cars.add(otherCar);
		carsInt++;
	}

	private void addStrips() { // Seritleri olusturup diziye atama
		for (int i = 0; i < 10; i++) {
			for (int j = 1; j < 5; j++) {
				Strips strip = new Strips(this, 100 + (j * 90), i * 80);
				strips.add(strip);
			}
		}
	}

	// Ana araba(Kirmizi)
	private void myCarLoadImage() {
		myCar = new MyCar(this, 305, 650); // arabanin koordinatlari
		cars.add(myCar);
	}

	private void initBoard() {
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);

		timer = new Timer(DELAY, this);
		timer.setInitialDelay(500);
		timer.start();

		// bazi degiskenlerin baslangic atamalari
		this.score = 0;
		this.gameover = false;
		is_Start = true;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		roadSides(g2d);
		if (is_Start) {
			// baslangic ekrani
			drawStart(g2d);
		} else {
			if (!gameover) {
				// oyun bitmediyse asagidaki metodlari calistir
				doDrawing(g2d);
				scoreTable(g2d);
			} else {
				// oyun bittiyse asagidaki metodu calistir
				gameover(g2d);
			}
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void drawStart(Graphics2D g2d) {
		BufferedImage buff = new BufferedImage(350, 700, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gbi = buff.createGraphics();

		gbi.drawImage(this.startLogo.getImage(), (buff.getWidth() - startLogo.getWidth()) / 2, 80, null);

		gbi.setColor(Color.white);
		gbi.setFont(new Font("Comic Sans MS", Font.BOLD, 28));

		String str = "Press        to start";
		FontMetrics fm = gbi.getFontMetrics();

		// str ekrana yazdirma
		gbi.drawString(str, (buff.getWidth() - fm.stringWidth(str)) / 2, 80 + startLogo.getHeight() + 90);

		// p tusu
		gbi.drawImage(this.key[0].getImage(), (buff.getWidth() - key[0].getWidth()) / 2 - 15,
				90 + startLogo.getHeight() + (key[0].getHeight() / 2), null);

		// sag ok tusu
		str = "to go to right side";
		gbi.drawImage(this.key[1].getImage(), (buff.getWidth() - key[0].getWidth()) / 2 - 50 - key[1].getWidth(),
				280 + startLogo.getHeight(), null);
		gbi.drawString(str, (buff.getWidth() - key[0].getWidth()) / 2 - 40, 450);

		// sol ok tusu
		str = "to go to left side";
		gbi.drawImage(this.key[2].getImage(), (buff.getWidth() - key[0].getWidth()) / 2 - 50 - key[2].getWidth(),
				450 + startLogo.getHeight(), null);
		gbi.drawString(str, (buff.getWidth() - key[0].getHeight()) / 2 - 40, 620);

		g2d.drawImage(buff, (width - buff.getWidth()) / 2 - 25, (height - buff.getHeight()) / 2, null);
	}

	private void scoreTable(Graphics2D g2d) {
		BufferedImage buff = new BufferedImage(140, 110, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gbi = buff.createGraphics();

		gbi.setColor(Color.red);
		gbi.fillRect(0, 0, buff.getWidth(), buff.getHeight());

		// kalemin ozellikleri
		gbi.setColor(Color.BLACK);
		gbi.setFont(new Font("Consolas", Font.BOLD, 35));

		// yazilar
		String str = "SCORE";
		FontMetrics fm = gbi.getFontMetrics();
		gbi.drawString(str, (buff.getWidth() - fm.stringWidth(str)) / 2, 45);

		// Score
		gbi.drawString("" + this.score, (buff.getWidth() - fm.stringWidth("" + this.score)) / 2, 85);
		g2d.drawImage(buff, width - buff.getWidth(), 0, null);
	}

	private void gameover(Graphics2D g2d) {
		BufferedImage buff = new BufferedImage(350, 400, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gbi = buff.createGraphics();

		// Oyun bitis ekraninda ki kare sekillerinin ayarlanmasi
		gbi.setColor(Color.BLUE);
		gbi.fillRect(0, 0, buff.getWidth(), buff.getHeight());
		gbi.setColor(Color.gray);
		int wh = 10;
		gbi.fillRect(wh, wh, buff.getWidth() - 2 * wh, buff.getHeight() - 2 * wh);
		// yazilar
		gbi.setColor(Color.BLACK);
		gbi.setFont(new Font("Comic Sans MS", Font.BOLD, 35));

		// gameover
		String str = " Game Over !";
		FontMetrics fm = gbi.getFontMetrics();
		gbi.drawString(str, (buff.getWidth() - fm.stringWidth(str)) / 2, 70);

		// restart
		gbi.drawImage(this.restart.getImage(), (buff.getWidth() - this.restart.getWidth()) / 2, 120, null);

		// skor tablosu
		str = "Score : " + this.score;
		gbi.drawString(str, (buff.getWidth() - fm.stringWidth(str)) / 2, 250);

		// Press Enter to Try Again kismi
		str = "Press Enter";
		gbi.drawString(str, (buff.getWidth() - fm.stringWidth(str)) / 2, 310);
		str = "to Try Again";
		gbi.drawString(str, (buff.getWidth() - fm.stringWidth(str)) / 2, 340);

		g2d.drawImage(buff, (width - buff.getWidth()) / 2 - 25, (height - buff.getHeight()) / 2, null);

	}

	private void doDrawing(Graphics2D g2d) {
		for (int i = 0; i < strips.size(); i++) {
			// Seritleri cizdir
			Strips allStrip = (Strips) strips.get(i);
			allStrip.drawLines(g2d);
		}

		for (int i = 0; i < cars.size(); i++) {
			// Arabalari cizdir
			Cars car = (Cars) cars.get(i);
			car.drawCars(g2d);
		}
	}

	public void roadSides(Graphics2D g2d) {
		g2d.setColor(Color.green);
		g2d.fillRect(0, 0, 700, 800);
		// seritler
		g2d.setColor(Color.gray);
		g2d.fillRect(90, 0, 10, 800);
		g2d.fillRect(550, 0, 10, 800);
		// yol kenarlari
		g2d.setColor(Color.black);
		g2d.fillRect(100, 0, 450, 800);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!is_Start) { // baslangic ekraninda degilse
			if (!gameover) { // gameover = false iken if icine gir
				// oyun bitmediyse asagidakileri metodlari calistir
				carMove(); // arabayi hareket ettir
				stripMove();// seritleri hareket ettir
			}
		}
		repaint();
	}

	private void stripMove() {
		for (int i = 0; i < strips.size(); i++) {
			Strips allStrip = (Strips) strips.get(i); // tum seritleri allStrip' e at
			allStrip.moveLines();
		}
	}

	public void setGameOver(boolean isGameOver) {
		myCar.setX(305); // oyun baslangicinda ki x pozisyonu
		for (int i = 1; i < cars.size(); i++) {
			// oyun bittiginde tekrar calistirdigimizda(restart) ilk bastaki pozisyona don
			Cars allCars = (Cars) cars.get(i);
			allCars.setY(positions[i - 1][1]); // oyun baslangicinda ki y pozisyonu
		}
		this.gameover = isGameOver;
	}

	private void carMove() {
		for (int i = 0; i < cars.size(); i++) {
			// ArrayList icinde ki tum arabalari al hareket ettir
			Cars allCars = (Cars) cars.get(i);
			allCars.move();
		}
		checkCollision();
	}

	private void checkCollision() {
		for (int i = 0; i < cars.size(); i++) {
			// j = i+1 cunku i=0 da ana arabamiz(kirmizi) var
			for (int j = i + 1; j < cars.size(); j++) {
				// cars ArrayList'i ni dolas ana arabamiz ve diger arabalari al
				Cars myCar = cars.get(i);
				Cars otherCars = cars.get(j);

				// carpisma kontrolu
				if (myCar.collisionControl(otherCars)) { // arraylist icindeki arabalarla kendi arabam carpisti mi kontrol et
															 
					myCar.collisionControls(otherCars); // abstract metodu calistir (oyun bitirme ekranini goster)
				}
			}
		}
	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			int code = e.getKeyCode();

			if (is_Start) {
				// p ye basildiginda oyunu baslat
				if (code == KeyEvent.VK_P) {
					is_Start = false;
				} else {
					return;
				}
			}

			if (code == KeyEvent.VK_RIGHT) {
				myCar.getHorizontalSpeed(0);
			}

			if (code == KeyEvent.VK_LEFT) {
				myCar.getHorizontalSpeed(0);
			}

			if (code == KeyEvent.VK_ENTER) {
				if (gameover) {// true gir
					gameover = false;
				}
				score = 0; // oyun yeniden basladiginda score'u sifirla
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();

			// sag tarafa hareket
			if (code == KeyEvent.VK_RIGHT) {
				myCar.getHorizontalSpeed(myCarSpeed);
			}

			// sol tarafa hareket
			if (code == KeyEvent.VK_LEFT) {
				myCar.getHorizontalSpeed(-myCarSpeed);
			}
		}
	}

}
