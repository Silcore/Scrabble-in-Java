// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScrabbleSplash extends JPanel {
	private Image image;
	
	public ScrabbleSplash() {
		super();
		super.setLayout(new BorderLayout());
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(Scrabble.class.getResource("ScrabbleIntroArt.png")));
		super.add(label, BorderLayout.CENTER);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}