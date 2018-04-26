// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
	private final char[][] resetState;
	private final char[][] initialBoard;
	private final Map<Character, Integer> values;
	private ArrayList<Character> letters;
	private final ArrayList<Player> playerList;
	private final ArrayList<Integer> turnList;
	private int turnIndex, currentI, currentJ;
	private boolean firstMove, doubleWord, tripleWord, firstTurn;

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

		// Duplicate boardState into initialBoard for reference
		initialBoard = new char[15][15];
		for(int i = 0; i < boardState.length; i++)
			for(int j = 0; j < boardState.length; j++)
				initialBoard[i][j] = boardState[i][j];

		resetState = new char[15][15];
		for(int i = 0; i < boardState.length; i++)
			for(int j = 0; j < boardState.length; j++)
				resetState[i][j] = boardState[i][j];

		// finite array of letters, can be changed in the future
		// letters are removed and placed into players hands
		char[] lettersArray = new char[] {'A','A','A','A','A','A','A','A','A','B','B','C','C','D','D',
							  'D','D','E','E','E','E','E','E','E','E','E','E','E','E','F',
							  'F','G','G','G','H','H','I','I','I','I','I','I','I','I','I',
							  'J','K','L','L','L','L','M','M','N','N','N','N','N','N','O',
							  'O','O','O','O','O','O','O','P','P','Q','R','R','R','R','R',
							  'R','S','S','S','S','T','T','T','T','T','T','U','U','U','U',
							  'V','V','W','W','X','Y','Y', 'Z','*','*'};
		
		// values of each character
		values = new HashMap<>();
		values.put('A', 1);
		values.put('B', 3);
		values.put('C', 3);
		values.put('D', 2);
		values.put('E', 1);
		values.put('F', 4);
		values.put('G', 2);
		values.put('H', 4);
		values.put('I', 1);
		values.put('J', 8);
		values.put('K', 5);
		values.put('L', 1);
		values.put('M', 3);
		values.put('N', 1);
		values.put('O', 1);
		values.put('P', 3);
		values.put('Q', 10);
		values.put('R', 1);
		values.put('S', 1);
		values.put('T', 1);
		values.put('U', 1);
		values.put('V', 4);
		values.put('W', 4);
		values.put('X', 8);
		values.put('Y', 4);
		values.put('Z', 10);
				
		letters = new ArrayList<>();
		for(char i : lettersArray)
			letters.add(i);
		
		// list of players
		playerList = new ArrayList<>();
		// turn order list
		turnList = new ArrayList<>();
		// index of current player turn
		turnIndex = 0;
		// is it the first move?
		firstMove = true;
		firstTurn = true;
		// indicates the current i and j
		currentI = -1;
		currentJ = -1;
		// is the current word double or triple?
		doubleWord = false;
		tripleWord = false;
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
	
	// add the designated letter to the board
	public void addLetter(char letter, int i, int j) {
		boardState[i][j] = letter;
	}
	
	// calculate score of character in i, j position
	public int calculateScore(char c, int i, int j) {
		switch (initialBoard[i][j]) {
			case 1:
				doubleWord = true;
				return values.get(Character.toUpperCase(c));
			case 2:
				return values.get(Character.toUpperCase(c)) * 2;
			case 3:
				return values.get(Character.toUpperCase(c)) * 3;
			case 4:
				doubleWord = true;
				return values.get(Character.toUpperCase(c));
			case 6:
				tripleWord = true;
				return values.get(Character.toUpperCase(c));
			default:
				return values.get(Character.toUpperCase(c));
		}
	}
	
	// add score to current player
	public void addScore(int score) {
		playerList.get(turnList.get(turnIndex)).playerScore += score;
	}
	
	public void resetPlayers() {
		playerList.clear();
	}

	// called when game starts
	public void setUpPlayers(){
		// used to figure out turn order
		// get hand, get first drawn char and get player num
		// treemap is automatically sorted by the letter which determines the turn order
		TreeMap<String,Integer> turnsInfo = new TreeMap<>();
		int e = 0;
		for(Player i : playerList) {
			i.fillHand();
			turnsInfo.put(i.getFirstLetter()+String.valueOf(e),e);
			e++;
		}
		// populate the turn list
		for(Map.Entry<String,Integer> i : turnsInfo.entrySet())
			turnList.add(i.getValue());
		System.out.println(turnsInfo);
	}
	
	public boolean endTurn() {
		boolean completeTurn = false;
		
		// return false if no changes have been made
		if(currentI == -1 && currentJ == -1)
			return completeTurn;
		
		// check all words that have been made
		int i = currentI;
		int j = currentJ;
		int scoreTotal = 0;
		StringBuilder builder = new StringBuilder();
		
		// move marker horizontally to end of letters
		while(isLetter(i, j))
			i++;
		
		// move backwards to start of word, capturing all letters
		while(isLetter(--i, j)) {
			builder.append(boardState[i][j]);
			scoreTotal += calculateScore(boardState[i][j], i, j);
		}
		
		if(verifyWord(builder.reverse().toString())) {
			completeTurn = true;
			if(doubleWord && tripleWord) {
				addScore(scoreTotal * 3 * 2);
				doubleWord = false;
				tripleWord = false;
			}
			else if(doubleWord) {
				addScore(scoreTotal * 2);
				doubleWord = false;
			}
			else if(tripleWord) {
				addScore(scoreTotal * 3);
				tripleWord = false;
			}
			else
				addScore(scoreTotal);
		}
		
		// clear builder
		builder = new StringBuilder();
		// reset values
		i = currentI;
		j = currentJ;
		scoreTotal = 0;
		
		// move marker vertically to end of letters
		while(isLetter(i, j))
			j++;
		
		// move vertically to start of word, capturing all letters
		while(isLetter(i, --j)) {
			builder.append(boardState[i][j]);
			scoreTotal += calculateScore(boardState[i][j], i, j);
		}
		
		if(verifyWord(builder.reverse().toString())) {
			completeTurn = true;
			if(doubleWord && tripleWord) {
				addScore(scoreTotal * 3 * 2);
				doubleWord = false;
				tripleWord = false;
			}
			else if(doubleWord) {
				addScore(scoreTotal * 2);
				doubleWord = false;
			}
			else if(tripleWord) {
				addScore(scoreTotal * 3);
				tripleWord = false;
			}
			else
				addScore(scoreTotal);
		}
		
		// Reset lingering variables
		doubleWord = false;
		tripleWord = false;
		firstMove = false;
		currentI = -1;
		currentJ = -1;
		
		if(completeTurn)
			updateState();
		
		return completeTurn;
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
	public boolean verifyWord(String word) {
		try (Scanner textScanner = new Scanner(new InputStreamReader(getClass().getResourceAsStream("words.txt")))) {			
			Pattern pattern = Pattern.compile(word.toLowerCase());
			
			if(textScanner.findWithinHorizon(pattern, 0) != null)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// checks if the i, j position is valid
	// handles all edge cases (perimeter pieces, corner pieces, etc)
	public boolean isValid(int i, int j) {
		// if there is currently a letter there, it is not valid
		if(isLetter(i, j))
			return false;
		
		// if it's not the first move
		if(!firstMove) {
			if(isLetter(i+1, j) || isLetter(i-1, j) || isLetter(i, j+1) || isLetter(i, j-1)) {
				currentI = i;
				currentJ = j;
				return true;
			}
		}
		// if it is the first turn
		else {
			// must start in the middle
			if(i == 7 && j == 7) {
				currentI = i;
				currentJ = j;
				firstMove = false;
				return true;
			}
		}
		
		return false;
	}
	
	// checks if the i, j is a letter. if it's out of bounds, catch exception and return false
	// saves unnecessary code in the isValid function
	public boolean isLetter(int i, int j) {
		boolean retValue;
		try {
			retValue = Character.isLetter(boardState[i][j]);
			return retValue;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	// reset board to state before player tile placement
	public void resetState() {
		if(firstTurn) firstMove = true;
		for(int i = 0; i < boardState.length; i++)
			for(int j = 0; j < boardState.length; j++)
				boardState[i][j] = resetState[i][j];
	}

	// update reset state when valid turn is made
	public void updateState() {
		if(firstTurn) firstTurn = false;
		for(int i = 0; i < boardState.length; i++)
			for(int j = 0; j < boardState.length; j++)
				resetState[i][j] = boardState[i][j];
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
			
			for(int i = 0; i < 7; i++){
				if(!letters.isEmpty() && playerLetters.size() < 7){
					int e = r.nextInt(letters.size());
					playerLetters.add(letters.get(e));
					letters.remove(e);
				}
			}
			
			
			/*
			for(int i = 0; i < 7; i++){
				if(!letters.isEmpty() && playerLetters.size() < 7){
					int e = r.nextInt(letters.size());
					playerLetters.add('*');
					letters.remove(e);
				}
			}
			*/
		}
	}
}