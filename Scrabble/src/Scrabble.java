// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.util.ArrayList;

//	Scrabble class (implemented as a Singleton) which contains all information pertaining to the game.
//	Player information is stored in a private player class within Scrabble, handled by Scrabble methods.
public class Scrabble {
	private static Scrabble instance = null;
	private final char[] boardState;
	private final ArrayList<Player> playerList;
	
	public static void main(String[] args) {
		ScrabbleFrame frame = new ScrabbleFrame();
	}
	
	public Scrabble() {
		//	boardState maintains the current state of the board
		//	0 = blank, 1 = star, 2 = double letter, 3 = triple letter, 4 = double word, 6 = triple word
		//	Respective letters are stored as their literal character equivalent.
		boardState = new char[] {6,0,0,2,0,0,0,6,0,0,0,2,0,0,6,
								 0,4,0,0,0,3,0,0,0,3,0,0,0,4,0,
								 0,0,4,0,0,0,2,0,2,0,0,0,4,0,0,
								 2,0,0,4,0,0,0,2,0,0,0,4,0,0,2,
								 0,0,0,0,4,0,0,0,0,0,4,0,0,0,0,
								 0,3,0,0,0,3,0,0,0,3,0,0,0,3,0,
								 0,0,2,0,0,0,2,0,2,0,0,0,2,0,0,
								 6,0,0,2,0,0,0,1,0,0,0,2,0,0,6,
								 0,0,2,0,0,0,2,0,2,0,0,0,2,0,0,
								 0,3,0,0,0,3,0,0,0,3,0,0,0,3,0,
								 0,0,0,0,4,0,0,0,0,0,4,0,0,0,0,
								 2,0,0,4,0,0,0,2,0,0,0,4,0,0,2,
								 0,0,4,0,0,0,2,0,2,0,0,0,4,0,0,
								 0,4,0,0,0,3,0,0,0,3,0,0,0,4,0,
								 6,0,0,2,0,0,0,6,0,0,0,2,0,0,6};
		playerList = new ArrayList<>();
	}
	
	public static Scrabble getInstance() {
		if(instance == null)
			instance = new Scrabble();
		return instance;
	}
	
	public char getIndex(int index) {
		return boardState[index];
	}
	
	public int getPlayerCount() {
		return playerList.size();
	}
	
	public void addPlayer(String name) {
		playerList.add(new Player(name));
	}
	
	public void resetPlayers() {
		playerList.clear();
	}
	
	//	Private nested class Player which is responsible for per-player information.
	//	Contains information regarding player hand, score, name, and other details.
	private class Player {
		private final String playerName;
		
		public Player(String name) {
			if(name.length() > 15)
				name = name.substring(0, 15) + "...";
			
			playerName = name;
		}
	}
}