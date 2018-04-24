// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.*;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

//	This is the upper layer of the Scrabble game interface, which contains all relevant components within it.
//	It holds a single Scrabble object which maintains the current state of the game, including players and board.
//	Inner classes will reference the Scrabble object periodically as players make modifications to the GUI.
public class ScrabbleBoard extends JPanel {	
	private final Scrabble scrabble = Scrabble.getInstance();

	// made these member data because they need to be directly accessible
	private final HandPanel hPanel;
	private final ButtonPanel bPanel;
	private final InfoPanel iPanel;
	private final GamePanel gPanel;

	public ScrabbleBoard() {
		super();
		
		iPanel = new InfoPanel();
		bPanel = new ButtonPanel();
		gPanel = new GamePanel();
		hPanel = new HandPanel();
		
		super.setLayout(new BorderLayout());
		super.add(bPanel, BorderLayout.NORTH);
		super.add(iPanel, BorderLayout.WEST);
		super.add(gPanel, BorderLayout.CENTER);
		super.add(hPanel, BorderLayout.SOUTH);
		
		// initialize players(name, tiles, score, turn order)
		scrabble.setUpPlayers();
		hPanel.setHand();
		bPanel.showCurrentPlayer();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
	}
	
	// Inner class InfoPanel contains the distribution of letters and current player scores.
	// This panel is displayed on the left-hand side of the ScrabbleBoard panel.
	private class InfoPanel extends JPanel {
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

			updateScores();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setBackground(Color.WHITE);
		}

		// called whenever a turn is made, updates current player scores
		public void updateScores() {
			String info = "<html><b>Scores</b><br/><pre>";
			for(int i = 0; i < scrabble.getPlayerCount(); i++) {
				info += scrabble.getPlayerName(i) + ": " + scrabble.getPlayerScore(i)+"<br/>";
			}
			info += "</pre></html>";
			JLabel scores = new JLabel(info, SwingConstants.CENTER);
			scores.setFont(new Font("Serif", Font.PLAIN, 14));
			super.add(scores, BorderLayout.NORTH);
			repaint();
		}
	}

	// Inner class ButtonPanel contains the end-turn button and current player information.
	// This panel is displayed at the top of the ScrabbleBoard panel.
	private class ButtonPanel extends JPanel {
		public ButtonPanel() {
			super();

			JPanel buttonSection = new JPanel(new FlowLayout(FlowLayout.CENTER));
			buttonSection.setOpaque(false);
			
			// Initializing endTurn Button
			JButton endTurn = new JButton("End Turn");
			endTurn.setFocusPainted(false);
			endTurn.setContentAreaFilled(false);
			endTurn.setOpaque(true);
			endTurn.setBackground(Color.CYAN);
			
			// Initializing resetTurn Button
			JButton resetTurn = new JButton("Reset Turn");
			resetTurn.setFocusPainted(false);
			resetTurn.setContentAreaFilled(false);
			resetTurn.setOpaque(true);
			resetTurn.setBackground(Color.CYAN);
			
			// Adding Buttons to Button Section
			buttonSection.add(resetTurn);
			buttonSection.add(endTurn);

			// Formatting Panel
			super.setLayout(new BorderLayout());
			super.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
			super.add(buttonSection, BorderLayout.LINE_END);

		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setBackground(Color.WHITE);
		}

		// called whenever a turn is made, updates current player at top
		// needs to be called after players have been initialized, not in this panel's constructor
		public void showCurrentPlayer() {
			JLabel playerName = new JLabel(scrabble.getCurrentPlayerName() + "'s Turn" , SwingConstants.CENTER);
			playerName.setFont(new Font("Serif", Font.BOLD, 30));
			super.add(playerName, BorderLayout.LINE_START);
			repaint();
		}
	}

	// Inner class GamePanel contains the grid corresponding to the Scrabble object's boardState.
	// This panel is displayed in the center of the ScrabbleBoard panel.
	private class GamePanel extends JPanel {
		public GamePanel() {
			super();

			// Formatting Panel and Generating Grid
			super.setLayout(new GridLayout(15, 15));
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
				label.setBorder(new MatteBorder(1, 1, 0, 0, Color.BLACK));
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
			this.setBackground(Color.WHITE);
		}
	}
	
	private class HandPanel extends JPanel{

		// create jpanels for all current players tiles, is called whenever turn is ended
		// needs to be function which is explicitly called
		// cannot be done in constructor because players will not have been initialized yet
		public void setHand(){
			ArrayList<Character> hand = scrabble.getCurrentPlayerHand();
			super.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
			for(Character i : hand) {
				JLabel tile = new JLabel(i.toString());
				tile.setOpaque(true);
				tile.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
				tile.setFont(new Font("Serif", Font.BOLD, 34));
				tile.setBackground(new Color(255, 228, 174));
				tile.setPreferredSize(new Dimension(50,50));
				tile.setHorizontalAlignment(SwingConstants.CENTER);
				super.add(tile);
			}
			super.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setBackground(Color.WHITE);
		}
	}
}
