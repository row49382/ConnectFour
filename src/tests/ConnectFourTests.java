package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import application.models.ConnectFour;
import tests.enums.ConnectFourGameboardState;
import tests.support.ConnectFourFactory;
import tests.support.ConnectFourFactoryImpl;

/**
 * Tests the ConnectFour win conditions and the various exceptions
 * that should be thrown with certain input
 * @author Robert Wroblewski
 *
 */
public class ConnectFourTests 
{	
	/** factory instance for the class that's used to create ConnectFour games in different states */
	private static ConnectFourFactory connectFourFactory;
	
	@BeforeAll
	public static void setup()
	{
		connectFourFactory = new ConnectFourFactoryImpl();
	}
	
	@Test
	public void testUpperLeftLowerRightDiagonalWin() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.UpperLeftLowerRightDiagonalWin);
		
		assertTrue(testGame.isDiagonal(), "Diagonal win was not detected");
	}
	
	@Test
	public void testLowerLeftUpperRightDiagonalWin() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.LowerLeftUpperRightDiagonalWin);
		
		assertTrue(testGame.isDiagonal(), "Diagonal win was not detected");
	}
	
	@Test
	public void testVerticalWin() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.VerticalWin);

		assertTrue(testGame.isVertical(), "Vertical win was not detected");
	}
	
	@Test
	public void testHorizontalWin() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.HorizonalWin);
		
		assertTrue(testGame.isHorizontal(), "Horizontal win was not detected");
	}
	
	@Test
	public void testTieGame() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.Full);
		
		assertTrue(testGame.isTie(), "the game did not identify a tie game");
	}
	
	@Test
	public void testResetGame() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.Full);
		
		testGame.reset();
		
		boolean isReset = true;
		
		// iterates through board to see if all spaces were reset
		for (int i = 0; i < testGame.getGameBoard().length; i++)
		{
			for (int j = 0; j < testGame.getGameBoard()[i].length; j++)
			{
				if (testGame.getGameBoard()[i][j] != testGame.getEmptyToken())
				{
					isReset = false;
					break;
				}
			}
			
			if (!isReset)
			{
				break;
			}
		}
		
		assertTrue(isReset && testGame.getPreviousColumn().isEmpty(), "the game did not reset");
	}
	
	@Test
	public void testUndoMoveBoard() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.Default);
		testGame.makeMove(0);
		int currentcolumn = testGame.getPreviousColumn().peek();
		
		testGame.undoMove();
		
		assertTrue(testGame.getGameBoard()[testGame.getColumnSpaces()[currentcolumn]][currentcolumn] == testGame.getEmptyToken(),
					"undo did not remove the last value");
	}
	
	@Test
	public void testInvalidColumnMove() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.Full);
		
		assertThrows(Exception.class, () -> 
		{
			testGame.makeMove(-1);
		}, "game didn't catch illegal move");
		
		assertThrows(Exception.class, () -> 
		{
			testGame.makeMove(testGame.getGameBoard().length);
		},  "game didn't catch illegal move");
	}
	
	@Test
	public void testUndoMoveCheckIsFirstPlayer() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.Full);
		
		boolean currentPlayerState = testGame.isFirstPlayer();
		
		testGame.undoMove();
		boolean previousPlayerState = testGame.isFirstPlayer();
		
		assertFalse(currentPlayerState == previousPlayerState, "game didn't rollback player status on undo");	
	}
	
	@Test
	public void testUndoMoveRevertColumnSpaceDecrement() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.Full);
		
		int currentColumn = testGame.getPreviousColumn().peek();
		int currentPlayerState = testGame.getColumnSpaces()[currentColumn];
		
		testGame.undoMove();
		int previousPlayerState = testGame.getColumnSpaces()[currentColumn];
		
		assertFalse(currentPlayerState == previousPlayerState, "game didn't rollback column depth on undo");
	}
	
	@Test
	public void testUndoAllMoves() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.Full);
		
		while (!testGame.getPreviousColumn().isEmpty())
		{
			testGame.undoMove();
		}
		
		boolean isUndoAll = true;
		// iterates through board to see if all spaces were reset
		for (int i = 0; i < testGame.getGameBoard().length; i++)
		{
			for (int j = 0; j < testGame.getGameBoard()[i].length; j++)
			{
				if (testGame.getGameBoard()[i][j] != testGame.getEmptyToken())
				{
					isUndoAll = false;
					break;
				}
			}

			if (!isUndoAll)
			{
				break;
			}
		}
		
		assertTrue(isUndoAll, "game did not undo all moves");
	}
	
	@Test
	public void testColumnFullMove() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.Full);
		
		assertThrows(Exception.class, () -> 
		{
			testGame.makeMove(0);
		}, "game didn't register that column was full");
	}
}
