package CarRacing;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class CarRacingEx extends JFrame {
	private static final long serialVersionUID = 7573886460127868971L;

	public CarRacingEx() {

		initUI();
	}

	private void initUI() {
		setSize(700, 800);
		Board brd =new Board(getWidth(),getHeight());
		add(brd);
		setTitle("Java 2D Car Racing Game");

		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			CarRacingEx ex = new CarRacingEx();
			ex.setVisible(true);
		});
	}
}
