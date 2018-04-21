// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

//	Frame containing all Scrabble panels. Transitions between panels as necessary.
public class ScrabbleFrame extends JFrame {	
	public ScrabbleFrame() {
		super("Scrabble in Java");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Adding Splash Art
		addScrabbleSplash();
		Timer timer = new Timer(3000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addScrabbleIntro();
			}
		});
		timer.setRepeats(false);
		timer.start();
		
		
	}
	
	private void addScrabbleSplash() {
		super.getContentPane().removeAll();
		super.add(new ScrabbleSplash());
		super.revalidate();
		super.repaint();
		super.setSize(new Dimension(800, 450));
		super.setMinimumSize(super.getSize());
		super.setResizable(false);
		super.setVisible(true);
	}
	
	private void addScrabbleIntro() {
		super.getContentPane().removeAll();
		super.add(new ScrabbleIntro());
		super.revalidate();
		super.repaint();
		super.setSize(new Dimension(800, 450));
		super.setMinimumSize(super.getSize());
		super.setResizable(false);
		super.setVisible(true);
	}
	
	private void addScrabbleBoard() {
		super.getContentPane().removeAll();
		super.add(new ScrabbleBoard());
		super.revalidate();
		super.repaint();
		super.setSize(new Dimension(1000, 700));
		super.setMinimumSize(super.getSize());
		super.setResizable(true);
		super.setVisible(true);
	}
}