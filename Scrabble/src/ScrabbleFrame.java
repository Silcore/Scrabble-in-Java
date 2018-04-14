// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

public class ScrabbleFrame extends JFrame {
	public ScrabbleFrame() {
		super("Scrabble in Java");
		super.setLayout(new BorderLayout());
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Adding Game Board
		super.add(new ScrabbleBoard());
		
		super.setMinimumSize(new Dimension(800, 500));
		super.setSize(new Dimension(800, 500));
		super.setVisible(true);
	}
}