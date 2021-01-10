package CarRacing;

public class MyCar extends Cars{

	public Board board;
	
	public MyCar(Board board,int x, int y) {
		super("Images/red_Car.png",x, y);
		this.board = board;
	}
	
	@Override
	public void move() {
		// arabanin sol yol kenarindan disari cikmamasi icin
		if (x < 95) {
			x = 95;
			return;
		}
		// arabanin sag yol kenarindan disari cikmamasi icin
		if(x + getWidth() > 555) {
			x = 555 - getWidth();
			return;
		}
		super.move();
	}

	@Override
	public void collisionControls(Cars collision) {
		if(collision instanceof OtherCars) {
			board.setGameOver(true);
		}
	}
	
}
