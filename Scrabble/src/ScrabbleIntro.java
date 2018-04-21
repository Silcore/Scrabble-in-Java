// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class ScrabbleIntro extends JPanel {
	private final Scrabble scrabble = Scrabble.getInstance();
	
	public ScrabbleIntro() {
		super();
		super.setLayout(new CardLayout());
		JPanel cards = this;
		
		// Organizing Card 1 (Starting Panel)
		JPanel card1 = new JPanel();
		card1.setLayout(new BoxLayout(card1, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel("SCRABBLE");
		title.setFont(new Font("Helvetica", Font.BOLD, 64));
		title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		JButton newGame = new JButton("New Game");
		JButton infoButton = new JButton("How to Play");
		JButton exitGame = new JButton("Exit");
		
		infoButton.addActionListener(new ActionListener() {
			@Override
			// Swap to information card when "How to Play" is pressed
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout) (cards.getLayout());
				layout.show(cards, "infoCard");
			}
		});
		
		exitGame.addActionListener(new ActionListener() {
			@Override
			// Exit when the exit button is pressed
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		card1.add(title);
		card1.add(Box.createVerticalStrut(40));
		card1.add(stylizeButton(newGame));
		card1.add(Box.createVerticalStrut(20));
		card1.add(stylizeButton(infoButton));
		card1.add(Box.createVerticalStrut(20));
		card1.add(stylizeButton(exitGame));
		
		// Organizing Card 2 (Game Information)
		JPanel card2 = new JPanel();
		card2.setLayout(new BorderLayout());
		
		JLabel infoBox = new JLabel("<html><body style='width: 600px'>"
				+ "• There must be at least two players per game. No more than four."
				+ "<br/>• The player who draws the earliest letter in the alphabet plays first."
				+ "<br/>• After turn order is established, each person draws until they have seven tiles."
				+ "<br/>• Each player, in order, lays down tiles connected to already played words to create new words."
				+ "<br/>• Tiles can be placed in vertical columns or horizontal rows. No diagonal or backwards words."
				+ "<br/>• After each turn, the tile values are added and placed in the score sheet."
				+ "<br/>• When all tiles have been used, the game ends. The player with the most points wins.</html>");
		infoBox.setFont(new Font("Helvetica", Font.PLAIN, 16));
		infoBox.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		infoBox.setSize(400, 300);
		infoBox.setBorder(new EmptyBorder(10, 40, 10, 40));
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout) (cards.getLayout());
				layout.show(cards, "startCard");
			}
		});
		
		card2.add(infoBox, BorderLayout.NORTH);
		card2.add(stylizeButton(backButton), BorderLayout.SOUTH);
		
		// Adding cards to card layout
		super.add(card1, "startCard");
		super.add(card2, "infoCard");
	}
	
	// Eliminates repeated code
	// Changes button properties to make them all look the same
	private JButton stylizeButton(JButton jb) {
		jb.setFont(new Font("Helvetica", Font.PLAIN, 20));
		jb.setAlignmentX(JButton.CENTER_ALIGNMENT);
		jb.setFocusPainted(false);
		jb.setContentAreaFilled(false);
		jb.setOpaque(true);
		jb.setBackground(Color.WHITE);
		jb.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
		
		return jb;
	}
}