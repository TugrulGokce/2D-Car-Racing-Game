package CarRacing;

//import java.awt.Panel;

public class OtherCars extends Cars{

	public Board board;
	
	// arabalarin hizi
	public int carMoveSpeed = 11;
	
	public OtherCars(Board board, String path, int x, int y) {
		super(path, x, y);
		this.board = board;
		dy = carMoveSpeed;
	}
	
	@Override
	public void move() {
		if(y > board.getHeight()) {
			// araba panelin disina cikinca y yi -750'ye sabitle
			y = - 750;
			
			// arabalar panelin disina cikinca skoru 15 arttir
			this.board.score += 15;
		}
		super.move();
	}

	@Override
	public void collisionControls(Cars collision) {
		
	}

	
	
}
