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
		
		assertTrue(testGame.isTie());
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
				if (testGame.getGameBoard()[i][j] != "-")
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
		
		assertTrue(isReset);
	}
	
	@Test
	public void testUndoMoveBoard() throws Exception
	{
		ConnectFour testGame = ConnectFourTests.connectFourFactory.getConnectFourEntity(
									ConnectFourGameboardState.Full);
		
		testGame.undoMove();
		
		assertTrue(testGame.getGameBoard()[testGame.getColumnSpaces()[testGame.getPreviousColumn()]][testGame.getPreviousColumn()] == testGame.getEmptyToken(),
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
		int currentPlayerState = testGame.getColumnSpaces()[testGame.getPreviousColumn()];
		
		testGame.undoMove();
		int previousPlayerState = testGame.getColumnSpaces()[testGame.getPreviousColumn()];
		
		assertFalse(currentPlayerState == previousPlayerState, "game didn't rollback column depth on undo");
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
