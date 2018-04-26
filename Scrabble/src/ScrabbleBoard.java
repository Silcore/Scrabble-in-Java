// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
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

		private JLabel scores;
		private JLabel infoBox;

		public InfoPanel() {
			super();

			// Initializing infoBox and scrabbleBox Labels
			infoBox = new JLabel("<html>Letter Distribution<br/><pre>A-9  J-1  S-4"
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
			removeAll();
			super.add(infoBox, BorderLayout.SOUTH);
			String info = "<html><b>Scores</b><br/><pre>";
			for(int i = 0; i < scrabble.getPlayerCount(); i++) {
				info += scrabble.getPlayerName(i) + ": " + scrabble.getPlayerScore(i)+"<br/>";
			}
			info += "</pre></html>";
			scores = new JLabel(info, SwingConstants.CENTER);
			scores.setFont(new Font("Serif", Font.PLAIN, 14));
			super.add(scores, BorderLayout.NORTH);
			repaint();
		}
	}

	// Inner class ButtonPanel contains the end-turn button and current player information.
	// This panel is displayed at the top of the ScrabbleBoard panel.
	private class ButtonPanel extends JPanel {

		private JLabel playerName;

		public ButtonPanel() {
			super();
			JPanel buttonSection = new JPanel(new FlowLayout(FlowLayout.CENTER));
			buttonSection.setOpaque(false);

			// Initializing resetTurn Button
			JButton resetTurn = new JButton("Reset Turn");
			resetTurn.setFocusPainted(false);
			resetTurn.setContentAreaFilled(false);
			resetTurn.setOpaque(true);
			resetTurn.setBackground(Color.GREEN);
			resetTurn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					scrabble.resetState();
					scrabble.resetPlayerHand();
					gPanel.reset();
					hPanel.setHand();
				}
			});

			// Initializing endTurn Button
			JButton endTurn = new JButton("End Turn");
			endTurn.setFocusPainted(false);
			endTurn.setContentAreaFilled(false);
			endTurn.setOpaque(true);
			endTurn.setBackground(Color.CYAN);
			endTurn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//update current player score
					//fill current player hand
					//update boardstate
					//update board
					//change players
					//update change hand panel
					//update players scores label
					//update player turn label

					if(scrabble.endTurn()) {
						scrabble.refillCurrentPlayer();
						scrabble.nextPlayer();
						gPanel.reset();
						hPanel.setHand();
						bPanel.showCurrentPlayer();
						iPanel.updateScores();
					}
					else {
						scrabble.resetState();
						gPanel.reset();
						hPanel.setHand();
					}
				}
			});
			
			// Initializing endGame Button
			JButton endGame = new JButton("End Game");
			endGame.setFocusPainted(false);
			endGame.setContentAreaFilled(false);
			endGame.setOpaque(true);
			endGame.setBackground(Color.RED);
			endGame.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(scrabble.getTurn() > scrabble.getPlayerCount()) {
						JDialog dialog = new JDialog();
						JPanel dialogPanel = new JPanel();
						dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));

						JLabel info = new JLabel();
						info.setFont(new Font("Helvetica", Font.PLAIN, 24));
						info.setAlignmentX(JLabel.CENTER_ALIGNMENT);
						info.setSize(100, 50);
						info.setBorder(new EmptyBorder(10, 40, 10, 40));

						//multiple winners
						if(scrabble.getWinners().size() == 1) info.setText("<html>Congratulations<br/>"+scrabble.getPlayerName(scrabble.getWinners().get(0))+"</html>");
						else {
							String text = "<html>Congratulations<br/>";
							for(int i : scrabble.getWinners()) {
								text+= scrabble.getPlayerName(scrabble.getWinners().get(i))+"<br/>";
							}
							text +="</html>";
							info.setText(text);
						}

						dialogPanel.add(info);
						JButton exitBtn = new JButton("Exit");
						exitBtn.setFocusPainted(false);
						exitBtn.setContentAreaFilled(false);
						exitBtn.setOpaque(true);
						exitBtn.setBackground(Color.PINK);
						exitBtn.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								System.exit(0);
							}
						});
						dialogPanel.add(exitBtn);
						dialog.add(dialogPanel);
						dialog.setSize(300, 150);
						dialog.setUndecorated(true);
						dialog.setLocationRelativeTo(null);
						dialog.setVisible(true);
					}
				}
			});
			
			// Adding Buttons to Button Section
			buttonSection.add(resetTurn);
			buttonSection.add(Box.createHorizontalStrut(5));
			buttonSection.add(endTurn);
			buttonSection.add(Box.createHorizontalStrut(40));
			buttonSection.add(endGame);


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
			if(playerName == null) {
				playerName = new JLabel(scrabble.getCurrentPlayerName() + "'s Turn" , SwingConstants.CENTER);
				playerName.setFont(new Font("Serif", Font.BOLD, 30));
				super.add(playerName, BorderLayout.LINE_START);
			}
			else playerName.setText(scrabble.getCurrentPlayerName() + "'s Turn" );
			repaint();
		}
	}

	// Inner class GamePanel contains the grid corresponding to the Scrabble object's boardState.
	// This panel is displayed in the center of the ScrabbleBoard panel.
	private class GamePanel extends JPanel {

		private JLabel[][] board;

		public GamePanel() {
			super();
			board = new JLabel[15][15];
			buildPanel();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setBackground(Color.WHITE);
		}
		
		public void reset() {
			for(int i = 0; i < 15; i++) {
				for(int j = 0; j < 15; j++) {
					switch (scrabble.getIndex(i, j)) {
						case 1:
							board[i][j].setText("Double Word Score");
							board[i][j].setFont(new Font("Serif", Font.BOLD, 10));
							break;
						case 2:
							board[i][j].setText("Double Letter Score");
							board[i][j].setFont(new Font("Serif", Font.BOLD, 10));
							break;
						case 3:
							board[i][j].setText("Triple Letter Score");
							board[i][j].setFont(new Font("Serif", Font.BOLD, 10));
							break;
						case 4:
							board[i][j].setText("Double Word Score");
							board[i][j].setFont(new Font("Serif", Font.BOLD, 10));
							break;
						case 6:
							board[i][j].setText("Triple Word Score");
							board[i][j].setFont(new Font("Serif", Font.BOLD, 10));
							break;
						default:
							board[i][j].setText(Character.toString(scrabble.getIndex(i, j)));
							break;
					}
					board[i][j].setText("<html><div style='text-align: center;'>" + board[i][j].getText() + "</div></html>");
				}
			}
		}
		
		private void buildPanel() {
			// Formatting Panel and Generating Grid
			super.setLayout(new GridLayout(15, 15));
			for(int i = 0; i < 15; i++) {
				for(int j = 0; j < 15; j++) {
					final int index1 = i, index2 = j;
					board[i][j] = new JLabel();
					board[i][j].addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(hPanel.getCurrentPiece() != null) {
								// Functionality for Blank Pieces
								if(hPanel.getCurrentPiece().getText().charAt(0) == '*') {
									JDialog dialog = new JDialog();
									JPanel dialogPanel = new JPanel();
									dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
									
									JLabel info = new JLabel("<html>Select the letter you would like to replace the "
											+ "blank piece with, then press ENTER.</html>");
									info.setFont(new Font("Helvetica", Font.PLAIN, 12));
									info.setAlignmentX(JLabel.CENTER_ALIGNMENT);
									info.setSize(100, 50);
									info.setBorder(new EmptyBorder(10, 40, 10, 40));

									JTextField letter = new JTextField("");
									letter.setSize(new Dimension(100, 50));
									letter.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											dialog.setVisible(false);
											// Add the letter to the grid if it is valid
											if (scrabble.isValid(index1, index2)) {
												hPanel.getCurrentPiece().setText(letter.getText().substring(0, 1).toUpperCase());
												board[index1][index2].setText(hPanel.getCurrentPiece().getText());
												board[index1][index2].setFont(new Font("Serif", Font.BOLD, 34));
												scrabble.removePlayerLetter(hPanel.getCurrentPiece().getText().charAt(0));
												hPanel.getCurrentPiece().removeAll();
												hPanel.resetCurrentPiece();
												scrabble.addLetter(board[index1][index2].getText().charAt(0), index1, index2);
											}
										}
									});
									dialogPanel.add(info);
									dialogPanel.add(letter);
									dialog.add(dialogPanel);
									dialog.setSize(300, 150);
									dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
									dialog.setUndecorated(true);
									dialog.setLocationRelativeTo(null);
									dialog.setVisible(true);
								}
								// If it isn't a blank piece, and isn't null
								else {									
									// Add the letter to the grid if it is valid
									if(scrabble.isValid(index1, index2)) {
										board[index1][index2].setText(hPanel.getCurrentPiece().getText());
										board[index1][index2].setFont(new Font("Serif", Font.BOLD, 34));
										hPanel.getCurrentPiece().setVisible(false);
										scrabble.removePlayerLetter(hPanel.getCurrentPiece().getText().charAt(0));
										hPanel.getCurrentPiece().removeAll();
										hPanel.resetCurrentPiece();
										scrabble.addLetter(board[index1][index2].getText().charAt(0), index1, index2);
									}
								}
							}
						}
					});

					switch (scrabble.getIndex(i, j)) {
						case 1:
							board[i][j].setText("Double Word Score");
							board[i][j].setBackground(new Color(229, 126, 221));
							break;
						case 2:
							board[i][j].setText("Double Letter Score");
							board[i][j].setBackground(new Color(0, 255, 255));
							break;
						case 3:
							board[i][j].setText("Triple Letter Score");
							board[i][j].setBackground(new Color(120, 191, 0));
							break;
						case 4:
							board[i][j].setText("Double Word Score");
							board[i][j].setBackground(new Color(0, 191, 191));
							break;
						case 6:
							board[i][j].setText("Triple Word Score");
							board[i][j].setBackground(new Color(80, 127, 0));
							break;
						default:
							board[i][j].setText(" ");
							board[i][j].setBackground(new Color(255, 228, 174));
							break;
					}

					board[i][j].setOpaque(true);
					board[i][j].setBorder(new MatteBorder(1, 1, 0, 0, Color.BLACK));
					board[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					board[i][j].setVerticalAlignment(SwingConstants.CENTER);
					board[i][j].setText("<html><div style='text-align: center;'>" + board[i][j].getText() + "</div></html>");
					board[i][j].setFont(new Font("Serif", Font.BOLD, 10));
					this.add(board[i][j]);
				}
			}
		}
	}
	
	private class HandPanel extends JPanel{
		private JLabel currentPiece;
		private ArrayList<Character> hand;
		
		// create jpanels for all current players tiles, is called whenever turn is ended
		// needs to be function which is explicitly called
		// cannot be done in constructor because players will not have been initialized yet
		public void setHand(){
			removeAll();
			hand = scrabble.getCurrentPlayerHand();
			super.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
			for(Character i : hand) {
				JLabel tile = new JLabel(i.toString());
				tile.setOpaque(true);
				tile.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
				tile.setFont(new Font("Serif", Font.BOLD, 34));
				tile.setBackground(new Color(255, 228, 174));
				tile.setPreferredSize(new Dimension(50,50));
				tile.setHorizontalAlignment(SwingConstants.CENTER);
				tile.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(currentPiece != null)
							currentPiece.setBackground(new Color(255, 228, 174));
						
						tile.setBackground(new Color(67, 191, 112));
						currentPiece = tile;
					}
				});
				
				super.add(tile);
			}
			super.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(2, 0, 0, 0, Color.BLACK), new EmptyBorder(10, 10, 10, 10)));
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			this.setBackground(Color.WHITE);
		}
		
		public JLabel getCurrentPiece() {
			return currentPiece;
		}
		
		public void resetCurrentPiece() {
			currentPiece = null;
		}
	}
}
