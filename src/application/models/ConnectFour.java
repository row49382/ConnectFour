package application.models;

import java.util.Observable;
import java.util.Stack;

/**
 * This class creates the gameboard
 * @author Robert Wroblewski
 *
 */
public class ConnectFour extends Observable
{
	// constant variables for the gameboard length and depth 
	private final int GAME_BOARD_DEPTH = 6;
	private final int GAME_BOARD_LENGTH = 7;
	private final int COLUMN_DEPTH = 5;
	
	/** Holds the red token */
	private String redToken;
	
	/** Holds the black token */
	private String blackToken;
	
	/** Holds the empty token */
	private String emptyToken;
	
	/** Holds the gameboard */
	private String[][] gameBoard;
	
	/** Returns true and indicates the first player */
	private boolean isFirstPlayer;
	
	/** Holds the value of the column height of each column */
	private int[] columnSpaces;
	
	/** Holds the previous move tokenValue value*/
	private String previousMove;
	
	/** Holds the column value of where the previous move was */
	private Stack<Integer> previousColumn;
	
	/**
	 * Constructor class
	 */
	public ConnectFour(String newRedToken, String newBlackToken, String newEmptyToken)
	{
		this.gameBoard = new String[GAME_BOARD_DEPTH][GAME_BOARD_LENGTH];
		
		this.columnSpaces = new int[] {COLUMN_DEPTH, COLUMN_DEPTH, COLUMN_DEPTH, COLUMN_DEPTH, COLUMN_DEPTH, COLUMN_DEPTH, COLUMN_DEPTH}; 
		this.setFirstPlayer();
		this.setRedToken(newRedToken);
		this.setBlackToken(newBlackToken);
		this.setEmptyToken(newEmptyToken);
		this.previousMove = this.getEmptyToken();
		this.previousColumn = new Stack<>();
		
		// makes the board
		for (int i = 0; i < this.gameBoard.length; i++)
		{
			for (int j = 0; j < this.gameBoard[i].length; j++)
			{
            	this.gameBoard[i][j] = getEmptyToken();
            }
		}
	}
	
	/**
	 * Sets the red token
	 * @param newRedToken The next red token
	 */
	public void setRedToken(String newRedToken)
	{
		this.redToken = newRedToken;
	}
	
	/**
	 * Sets the black token
	 * @param newBlackToken The next black token
	 */
	public void setBlackToken(String newBlackToken)
	{
		this.blackToken = newBlackToken;
	}
	
	/**
	 * Sets the empty token
	 * @param newEmptyToken The next empty token
	 */
	public void setEmptyToken(String newEmptyToken)
	{
		this.emptyToken = newEmptyToken;
	}
	
	/**
	 * Gets the empty token
	 * @return The next empty token
	 */
	public String getEmptyToken()
	{
		return this.emptyToken;
	}
	
	/**
	 * gets the column spaces remaining
	 * @return The column spaces remaining
	 */
	public int[] getColumnSpaces()
	{
		return this.columnSpaces;
	}
	
	/**
	 * Sets isFirstPlayer to true
	 */
	public void setFirstPlayer()
	{
		this.isFirstPlayer = true;
	}
	
	/**
	 * gets the first player
	 * @return the isFirstPlayer value
	 */
	public boolean isFirstPlayer()
	{
		return this.isFirstPlayer;
	}
	
	/**
	 * gets the gameboard
	 * @return the gameboard
	 */
	public String[][] getGameBoard()
	{
		return this.gameBoard;
	}

	/**
	 * the stack of previous column entries
	 * @return the stack of previous column entries
	 */
	public Stack<Integer> getPreviousColumn() 
	{
		return this.previousColumn;
	}	
	
	/**
	 * Checks to see if the token connects four diagonally
	 * @return checks to see if the token connects four 
	 */
	public boolean isDiagonal()
	{
		return this.isLowerLeftToUpperRightDiagonal() || this.isUpperLeftToLowerRightDiagonal();	
	}
	
	/**
	 * Checks if there if there is a win from the last spot entered that makes
	 * a diagonal, connecting atleast 4 tokens from upper left to lower right
	 * @param column The column that the previous token was sent to
	 * @return if there is a diagonal
	 */
	private boolean isUpperLeftToLowerRightDiagonal()
	{
		int tokenCounter = 1;
		
		// check the upper left
		int upperLeft = this.columnSpaces[this.previousColumn.peek()];
		for (int i = this.previousColumn.peek() - 1; i >= 0 && upperLeft >= 0 && tokenCounter < 4; i--, upperLeft--)
		{
			if (this.previousMove.compareTo(this.gameBoard[upperLeft][i]) != 0)
			{
				break;
			}
			
			tokenCounter++;
		}
		
		// offset of two because the column spaces have already been adjusted after the move was made. This will
		// start the search one token deeper in the stack than the last move		
		int lowerRight = this.columnSpaces[this.previousColumn.peek()] + 2;
		for (int i = this.previousColumn.peek() + 1; i < this.gameBoard[0].length && lowerRight < this.gameBoard.length && tokenCounter < 4; i++, lowerRight++)
		{
			if (this.previousMove.compareTo(this.gameBoard[lowerRight][i]) != 0)
			{
				break;
			}
			
			tokenCounter++;
		}
		
		// if there is an adjacency of atleast 4 in upperLeft to lowerRight, then there is a diagonal of 4 from the last spot
		return tokenCounter >= 4;
	}

	/**
	 * Checks if there if there is a win from the last spot entered that makes
	 * a diagonal, connecting atleast 4 tokens from lower left to upper right
	 * @param column The column that the previous token was sent to
	 * @return if there is a diagonal
	 */
	private boolean isLowerLeftToUpperRightDiagonal()
	{
		int tokenCounter = 1;
		
		int upperRight = this.columnSpaces[this.previousColumn.peek()];
		for (int i = this.previousColumn.peek() + 1; i < this.gameBoard[0].length && upperRight >= 0 && tokenCounter < 4; i++, upperRight--)
		{
			if (this.previousMove.compareTo(this.gameBoard[upperRight][i]) != 0)
			{
				break;
			}
			
		    tokenCounter++;
		}
		
		// offset of two because the column spaces have already been adjusted after the move was made. This will
		// start the search one token deeper in the board than the last move
		int lowerLeft = this.columnSpaces[this.previousColumn.peek()] + 2;
		for (int i = this.previousColumn.peek() - 1; i >= 0 && lowerLeft < this.gameBoard.length && tokenCounter < 4; i--, lowerLeft++)
		{
			if (this.previousMove.compareTo(this.gameBoard[lowerLeft][i]) != 0)
			{
				break;
			}
			
			tokenCounter++;
		}
		
		
		// if there is an adjacency of atleast 4 in upperRight to lowerLeft, then there is a diagonal of 4 from the last spot
		return tokenCounter >= 4;
	}
	
	/**
	 * Checks to see if the token connects four horizontally
	 * @return True if it connects four horizontally 
	 */
	public boolean isHorizontal()
	{
		int tokenCounter = 0;
		
		// check right side for token matches
		for (int i = this.previousColumn.peek() + 1; i < this.gameBoard[0].length && tokenCounter < 4; i++)
		{
			if (this.previousMove.compareTo(this.gameBoard[this.columnSpaces[this.previousColumn.peek()] + 1][i]) != 0)
			{
				break;
			}
			
			tokenCounter++;
		}
		
		// check left side for token matches
		for (int i = this.previousColumn.peek(); i >= 0 && tokenCounter < 4; i--)
		{
			if (this.previousMove.compareTo(this.gameBoard[this.columnSpaces[this.previousColumn.peek()] + 1][i]) != 0)
			{
				break;
			}
			
			tokenCounter++;
		}
		
		return tokenCounter >= 4;
	}
	
	/**
	 * checks to see if the token connects four vertically
	 * @return True if it connects four vertically
	 */
	public boolean isVertical()
	{ 
		int tokenCounter = 0;
		
		for (int i = this.columnSpaces[this.previousColumn.peek()] + 1; i < this.gameBoard.length && tokenCounter < 4; i++)
		{
			if (this.previousMove.compareTo(this.gameBoard[i][this.previousColumn.peek()]) != 0)
			{
				break;
			}
			
			tokenCounter++;
		}
		
		return tokenCounter >= 4;
	}
	
	/**
	 * Returns true if there is a tie
	 * @return True if there is a tie, false if not
	 */
	public boolean isTie()
	{
		for (int i = 0; i < this.gameBoard.length; i++)
		{
			for (int j = 0; j < this.gameBoard[i].length; j++)
			{
				if (this.gameBoard[i][j].equals(this.getEmptyToken()))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks to see if the game is over based on the last move
	 * @param column The last input
	 * @return True if the game is over, false if its not
	 */
	public boolean gameOver()
	{
		return this.isDiagonal() || this.isHorizontal() || this.isVertical() || this.isTie();
	}
	
	/**
	 * Insert a token in the position indicated provided it's valid
	 * @param column The column number of the new token
	 * @throws Exception If the column is full of if the column value is invalid
	 */
	public void makeMove(int column) throws Exception 
	{
		if (column < 0 || column > this.columnSpaces.length)
		{
			throw new Exception("Invalid column value, please try again");
		}
		else if (this.columnSpaces[column] < 0  || this.columnSpaces[column] > this.gameBoard.length)
		{
			throw new Exception("Column is full, please try again.");
		}
		
		if (this.isFirstPlayer)	
		{
			this.gameBoard[this.columnSpaces[column]][column] = this.redToken;
			this.isFirstPlayer = false;
		}
		else
		{
			this.gameBoard[this.columnSpaces[column]][column] = this.blackToken;
			this.isFirstPlayer = true;
		}
		
		this.previousMove = this.gameBoard[this.columnSpaces[column]][column];
		this.previousColumn.push(column);
		this.columnSpaces[column]--;
		
		this.setChanged();
		this.notifyObservers("MakeMove");
	}
	
	/**
	 * Resets the board and other class variables
	 */
	public void reset()
	{
		for (int i = 0; i < this.gameBoard.length; i++)
		{
			for (int j = 0; j < this.gameBoard[i].length; j++)
			{
				this.gameBoard[i][j] = this.getEmptyToken();
			}
		}
		
		for (int i = 0; i < this.columnSpaces.length; i++)
		{
			this.getColumnSpaces()[i] = 5;
		}
		
		this.previousMove = this.getEmptyToken();
		this.previousColumn.clear();
		this.isFirstPlayer = true;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Undos the most recent move
	 */
	public void undoMove()
	{
		int currentColumn = this.previousColumn.peek();
		this.gameBoard[this.getColumnSpaces()[this.previousColumn.peek()] + 1][this.previousColumn.peek()] = this.getEmptyToken();
		this.getColumnSpaces()[this.previousColumn.peek()]++;
		this.previousColumn.pop();
		this.previousMove = this.getEmptyToken();
		this.isFirstPlayer = !this.isFirstPlayer;
		
		this.setChanged();
		this.notifyObservers(currentColumn);
	}
	
	/**
	 * to String method
	 */
	public String toString()
	{
		String s = "";
		for (int i = 0; i < this.gameBoard.length; i++)
		{	
			for (int j = 0; j < this.gameBoard[i].length; j++)
			{
				s += this.gameBoard[i][j] + "\t";
			}
			s += "\n";
		}
		return s;
	}
}
