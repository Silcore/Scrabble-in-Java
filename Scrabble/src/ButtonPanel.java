// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class ButtonPanel extends JPanel {
	public ButtonPanel() {
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