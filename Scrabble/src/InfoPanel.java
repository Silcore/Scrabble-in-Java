// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class InfoPanel extends JPanel {
	public InfoPanel() {
		// Initializing infoBox and scrabbleBox Labels
		JLabel infoBox = new JLabel("<html>Letter Distribution<br/><pre>A-9  J-1  S-4"
				+ "<br/>B-2  K-1  T-6<br/>C-2  L-4  U-4<br/>D-4  M-2  V-2"
				+ "<br/>E-12 N-6  W-2<br/>F-2  O-8  X-1<br/>G-3  P-2  Y-2"
				+ "<br/>H-2  Q-1  Z-1<br/>I-9  R-6<br/>BLANK-2</pre></html>", SwingConstants.CENTER);
		JLabel scrabbleBox = new JLabel("<html>S<sub>1</sub><br/>C<sub>3</sub>"
				+ "<br/>R<sub>1</sub><br/>A<sub>1</sub><br/>B<sub>3</sub>"
				+ "<br/>B<sub>3</sub><br/>L<sub>1</sub><br/>E<sub>1</sub>"
				+ "<br/></html>", SwingConstants.CENTER);
		infoBox.setFont(new Font("Serif", Font.PLAIN, 12));
		scrabbleBox.setFont(new Font("Serif", Font.PLAIN, 12));
		
		// Formatting Panel
		super.setLayout(new BorderLayout());
		super.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 0, 2, Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
		super.add(infoBox, BorderLayout.PAGE_END);
		super.add(scrabbleBox, BorderLayout.PAGE_START);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
	}
}