// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import javax.swing.JFrame;

public class Scrabble {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Scrabble in Java");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
			To Do:
			- Add N, S, E, W panels.
			- Add center panel (grid layout).
			- Format panels by direction.
		*/
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
}