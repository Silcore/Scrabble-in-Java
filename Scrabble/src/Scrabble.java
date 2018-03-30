// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Scrabble {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Scrabble in Java");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		InfoPanel iPanel = new InfoPanel();
		ButtonPanel bPanel = new ButtonPanel();
		
		/*
			To Do:
			- Add N, S, E, W panels.
			- Add center panel (grid layout).
			- Format panels by direction.
		*/
		
		// ButtonPanel (NORTH) Initialization
		frame.add(bPanel, BorderLayout.NORTH);
		
		// InfoPanel (WEST) Initialization
		frame.add(iPanel, BorderLayout.WEST);

		// Temporary Center Panel
		frame.add(new JPanel(), BorderLayout.CENTER);
		
		frame.setMinimumSize(new Dimension(800, 500));
		frame.setSize(new Dimension(800, 500));
		frame.setVisible(true);
	}
}