// Filipe Guerra and Dexter Ketchum
// COP3252 - Java
// Semester Project (Scrabble.java)

public class Scrabble {
	private final char[] boardState;
	
	public Scrabble() {
		// boardState maintains the current state of the board
		// 0 = blank, 1 = star, 2 = double letter, 3 = triple letter, 4 = double word, 6 = triple word
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
	}
	
	public static void main(String[] args) {
		ScrabbleFrame frame = new ScrabbleFrame();
	}
	
	public char getIndex(int index) {
		return boardState[index];
	}
}