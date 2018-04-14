// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class ScrabbleIntro extends JPanel {
	private final Scrabble scrabble = Scrabble.getInstance();
	
	public ScrabbleIntro() {
		super();
		super.setLayout(new BorderLayout());
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
	}
}