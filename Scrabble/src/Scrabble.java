// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

//	Scrabble class (implemented as a Singleton) which contains all information pertaining to the game.
//	Player information is stored in a private player class within Scrabble, handled by Scrabble methods.
public class Scrabble {
	private static Scrabble instance = null;
	private final char[][] boardState;
	private ArrayList<Character> playerWord;
	private ArrayList<Character> letters;
	private final ArrayList<Player> playerList;
	private final ArrayList<Integer> turnList;
	private int turnIndex;
	
	public static void main(String[] args) {
		ScrabbleFrame frame = new ScrabbleFrame();
	}
	
	// CONSTRUCTOR
	public Scrabble() {
		//	boardState maintains the current state of the board
		//	0 = blank, 1 = star, 2 = double letter, 3 = triple letter, 4 = double word, 6 = triple word
		//	Respective letters are stored as their literal character equivalent.
		boardState = new char[][] {{6,0,0,2,0,0,0,6,0,0,0,2,0,0,6},
								   {0,4,0,0,0,3,0,0,0,3,0,0,0,4,0},
								   {0,0,4,0,0,0,2,0,2,0,0,0,4,0,0},
								   {2,0,0,4,0,0,0,2,0,0,0,4,0,0,2},
								   {0,0,0,0,4,0,0,0,0,0,4,0,0,0,0},
								   {0,3,0,0,0,3,0,0,0,3,0,0,0,3,0},
								   {0,0,2,0,0,0,2,0,2,0,0,0,2,0,0},
								   {6,0,0,2,0,0,0,1,0,0,0,2,0,0,6},
								   {0,0,2,0,0,0,2,0,2,0,0,0,2,0,0},
								   {0,3,0,0,0,3,0,0,0,3,0,0,0,3,0},
								   {0,0,0,0,4,0,0,0,0,0,4,0,0,0,0},
								   {2,0,0,4,0,0,0,2,0,0,0,4,0,0,2},
								   {0,0,4,0,0,0,2,0,2,0,0,0,4,0,0},
								   {0,4,0,0,0,3,0,0,0,3,0,0,0,4,0},
								   {6,0,0,2,0,0,0,6,0,0,0,2,0,0,6}};

		// finite array of letters, can be changed in the future
		// letters are removed and placed into players hands
		char[] lettersArray = new char[] {'A','A','A','A','A','A','A','A','A','B','B','C','C','D','D',
							  'D','D','E','E','E','E','E','E','E','E','E','E','E','E','F',
							  'F','G','G','G','H','H','I','I','I','I','I','I','I','I','I',
							  'J','K','L','L','L','L','M','M','N','N','N','N','N','N','O',
							  'O','O','O','O','O','O','O','P','P','Q','R','R','R','R','R',
							  'R','S','S','S','S','T','T','T','T','T','T','U','U','U','U',
							  'V','V','W','W','X','Y','Y', 'Z','*','*'};
		letters = new ArrayList<>();
		for(char i : lettersArray)
			letters.add(i);
		
		// list of players
		playerList = new ArrayList<>();
		// turn order list
		turnList = new ArrayList<>();
		// current player word
		playerWord = new ArrayList<>();
		// index of current player turn
		turnIndex = 0;
	}
	
	// GETTERS
	// gets the singleton instance of Scrabble
	public static Scrabble getInstance() {
		if(instance == null)
			instance = new Scrabble();
		return instance;
	}
	
	// gets the character in the corresponding i, j index positions
	public char getIndex(int i, int j) {
		return boardState[i][j];
	}
	
	// gets the total number of players
	public int getPlayerCount() {
		return playerList.size();
	}
	
	// get the current playerWord
	public ArrayList<Character> getCurrentWord() {
		return playerWord;
	}
	
	//array with all current players tiles, up to 7 tiles
	public ArrayList<Character> getCurrentPlayerHand(){
		return playerList.get(turnList.get(turnIndex)).getHand();
	}

	// gets the name of the current player
	public String getCurrentPlayerName() {
		return playerList.get(turnList.get(turnIndex)).getPlayerName();
	}

	// get a player name based on index, used to show all scores
	public String getPlayerName(int index) {
		return playerList.get(index).getPlayerName();
	}

	// get a player score based on index, used to show all scores
	public int getPlayerScore(int index) {
		return playerList.get(index).getPlayerScore();
	}
	
	// SETTERS
	// add a player with the given name
	public void addPlayer(String name) {
		playerList.add(new Player(name));
	}
	
	// add the designated letter to the current word
	public void addWordLetter(char letter) {
		playerWord.add(letter);
	}
	
	public void resetPlayers() {
		playerList.clear();
	}

	// called when game starts
	public void setUpPlayers(){
		// used to figure out turn order
		// get hand, get first drawn char and get player num
		// treemap is automatically sorted by the letter which determines the turn order
		TreeMap<Character,Integer> turnsInfo = new TreeMap<>();
		int e = 0;
		for(Player i : playerList) {
			i.fillHand();
			turnsInfo.put(i.getFirstLetter(),e);
			e++;
		}
		// populate the turn list
		for(Map.Entry<Character,Integer> i : turnsInfo.entrySet())
			turnList.add(i.getValue());
	}

	// UTILITIES
	// go to next player, called whenever a turn is made
	public void nextPlayer() {
		turnIndex = ++turnIndex % playerList.size();
	}

	// refill the tile of a player after a turn is made, called before changing players
	public void refillCurrentPlayer() {
		playerList.get(turnList.get(turnIndex)).fillHand();
	}
	
	// verifies if the playerWord is legitimate, and does it super quickly
	public boolean verifyWord() {
		try (Scanner textScanner = new Scanner(new File("src/words.txt"))) {
			StringBuilder builder = new StringBuilder(playerWord.size());
			for(Character c : playerWord)
				builder.append(c);
			
			Pattern pattern = Pattern.compile(builder.toString().toLowerCase());
			
			if(textScanner.findWithinHorizon(pattern, 0) != null)
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	//	Private nested class Player which is responsible for per-player information.
	//	Contains information regarding player hand, score, name, and other details.
	private class Player {
		private final String playerName;
		private int playerScore;
		private ArrayList<Character> playerLetters;
		
		// CONSTRUCTOR
		public Player(String name) {
			if(name.length() > 15)
				name = name.substring(0, 15) + "...";
			else if(name.length() == 0)
				name = "Anonymous";
			
			playerName = name;
			playerScore = 0;
			playerLetters = new ArrayList<>();
		}

		// GETTERS
		// only really used for turn order
		public char getFirstLetter() {
			return playerLetters.get(0);
		}

		public ArrayList<Character> getHand() {
			return playerLetters;
		}

		public String getPlayerName() {
			return playerName;
		}

		public int getPlayerScore() {
			return playerScore;
		}
		
		// SETTERS
		// assigns new tiles to player no matter if empty or partly filled
		// stops when 7 tiles is reached or no more tiles available
		public void fillHand() {
			Random r = new Random();
			/*
			for(int i = 0; i < 7; i++){
				if(!letters.isEmpty() && playerLetters.size() < 7){
					int e = r.nextInt(letters.size());
					playerLetters.add(letters.get(e));
					letters.remove(e);
				}
			}
			*/
			
			for(int i = 0; i < 7; i++){
				if(!letters.isEmpty() && playerLetters.size() < 7){
					int e = r.nextInt(letters.size());
					playerLetters.add('*');
					letters.remove(e);
				}
			}
		}
	}
}