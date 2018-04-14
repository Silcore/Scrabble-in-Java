// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class ScrabbleBoard extends JPanel {
	private final Scrabble scrabble;
	
	public ScrabbleBoard() {
		super();
		
		scrabble = new Scrabble();
		InfoPanel iPanel = new InfoPanel();
		ButtonPanel bPanel = new ButtonPanel();
		GamePanel gPanel = new GamePanel();
		
		super.setLayout(new BorderLayout());
		super.add(bPanel, BorderLayout.NORTH);
		super.add(iPanel, BorderLayout.WEST);
		super.add(gPanel, BorderLayout.CENTER);
	}
	
	class InfoPanel extends JPanel {
	public InfoPanel() {
		super();
		
		// Initializing infoBox and scrabbleBox Labels
		JLabel infoBox = new JLabel("<html>Letter Distribution<br/><pre>A-9  J-1  S-4"
				+ "<br/>B-2  K-1  T-6<br/>C-2  L-4  U-4<br/>D-4  M-2  V-2"
				+ "<br/>E-12 N-6  W-2<br/>F-2  O-8  X-1<br/>G-3  P-2  Y-2"
				+ "<br/>H-2  Q-1  Z-1<br/>I-9  R-6<br/>BLANK-2</pre></html>", SwingConstants.CENTER);
		infoBox.setFont(new Font("Serif", Font.PLAIN, 12));
		
		// Formatting Panel
		super.setLayout(new BorderLayout());
		super.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 0, 2, Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
		super.add(infoBox, BorderLayout.SOUTH);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
	}
}

class ButtonPanel extends JPanel {
	public ButtonPanel() {
		super();
		
		// Initializing endTurn Button
		JButton endTurn = new JButton("End Turn");
		endTurn.setFocusPainted(false);
		endTurn.setContentAreaFilled(false);
		endTurn.setOpaque(true);
		endTurn.setBackground(Color.CYAN);
		
		// Formatting Panel
		super.setLayout(new BorderLayout());
		super.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
		super.add(endTurn, BorderLayout.LINE_END);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
	}
}

class GamePanel extends JPanel {
	public GamePanel() {
		super();
		
		// Formatting Panel
		super.setLayout(new GridLayout(15, 15, -1, -1));
		for(int i = 0; i < (15*15); i++) {
			JLabel label = new JLabel();
			
			switch (scrabble.getIndex(i)) {
				case 1:
					label.setText("Double Word Score");
					label.setBackground(new Color(229, 126, 221));
					break;
				case 2:
					label.setText("Double Letter Score");
					label.setBackground(new Color(0, 255, 255));
					break;
				case 3:
					label.setText("Triple Letter Score");
					label.setBackground(new Color(120, 191, 0));
					break;
				case 4:
					label.setText("Double Word Score");
					label.setBackground(new Color(0, 191, 191));
					break;
				case 6:
					label.setText("Triple Word Score");
					label.setBackground(new Color(80, 127, 0));
					break;
				default:
					label.setText(" ");
					label.setBackground(new Color(255, 228, 174));
					break;
			}
			
			label.setOpaque(true);
			label.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setText("<html><div style='text-align: center;'>" + label.getText() + "</div></html>");
			label.setFont(new Font("Serif", Font.BOLD, 10));
			this.add(label);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
	}
}
}
