// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ScrabbleFrame extends JFrame {
	public ScrabbleFrame() {
		super("Scrabble in Java");
		super.setLayout(new BorderLayout());
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createMenu();
		// Adding Game Board
		super.add(new ScrabbleBoard());
		
		super.setMinimumSize(new Dimension(800, 500));
		super.setSize(new Dimension(800, 500));
		super.setVisible(true);
	}
	
	private void createMenu() {
		// Creating Menu Bar and Menus
		JMenuBar menuBar = new JMenuBar();
		JMenu optionsMenu = new JMenu("Options");
		JMenuItem exitItem = new JMenuItem(new AbstractAction("Exit") { 
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JMenuItem infoItem = new JMenuItem(new AbstractAction("Gameplay Instructions") {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				JDialog dialog = new JDialog(frame, "Gameplay Instructions", true);
				dialog.setLayout(new BorderLayout());
				
				JTextArea instructions = new JTextArea("• For each game of Scrabble, there must be a minimum of two players"
						+ "and a maximum of four players.\n• The game begins with each player having seven tiles.\n"
						+ "• Each player lays down tiles on the board to make up words that connect to already played words.\n"
						+ "• Tiles can only be placed from left to right or top to bottom. Diagonal or backwards words are"
						+ "not allowed.\n• After each turn, the tile values are added up and displayed in the score section."
						+ " When the player turn is complete, they will collect tiles equal to the number they played.\n"
						+ "• The person with the highest score at the end of the game wins.\n");
				instructions.setLineWrap(true);
				instructions.setBorder(new EmptyBorder(10, 10, 10, 10));
				
				dialog.add(instructions, BorderLayout.CENTER);
				dialog.add(new JButton(new AbstractAction("Close Instructions") {
					@Override
					public void actionPerformed(ActionEvent e) {
						dialog.setVisible(false);
					}
				}), BorderLayout.SOUTH);
				
				dialog.setSize(400, 400);
				dialog.setResizable(false);
				dialog.setVisible(true);
			}
		});
		optionsMenu.add(infoItem);
		optionsMenu.add(exitItem);
		
		menuBar.add(optionsMenu);
		super.add(menuBar);
		super.setJMenuBar(menuBar);
	}
}