package tests.support;

import application.models.ConnectFour;
import tests.enums.ConnectFourGameboardState;

/**
 * Creates ConnectFour objects based on the state requested
 * @author Robert Wroblewski
 *
 */
public class ConnectFourFactoryImpl implements ConnectFourFactory 
{
	@Override
	public ConnectFour getConnectFourEntity(ConnectFourGameboardState gameBoardType) throws Exception
	{
		ConnectFour game = new ConnectFour("R", "B", "-");
		switch (gameBoardType)
		{
			case HorizonalWin:
				game = this.getHorizontalWinGameBoard(game);
				break;
			case VerticalWin:
				game = this.getVerticalWinGameBoard(game);
				break;
			case UpperLeftLowerRightDiagonalWin:
				game = this.getUpperLeftLowerRightDiagonalWinGameBoard(game);
				break;
			case LowerLeftUpperRightDiagonalWin:
				game = this.getLowerLeftUpperRightDiagonalWinGameBoard(game);
				break;
			case Full:
				game = this.getTieGame(game);
				break;
			default:
				break;
		}

		return game;	
	}

	/**
	 * Sets the state of the game to have a win with UL LR diagonal
	 * @param game The game
	 * @return The game that is having the board changed
	 * @throws Exception If the move made is out of bounds
	 */
	private ConnectFour getUpperLeftLowerRightDiagonalWinGameBoard(ConnectFour game) throws Exception
	{
		game.makeMove(0);
		game.makeMove(0);
		game.makeMove(0);
		game.makeMove(0);
		game.makeMove(1);
		game.makeMove(1);
		game.makeMove(2);
		game.makeMove(1);
		game.makeMove(5);
		game.makeMove(2);
		game.makeMove(4);
		game.makeMove(3);
	
		return game;
	}

	/**
	 * Sets the state of the game to have a win with LL UR diagonal
	 * @param game The game
	 * @return The game that is having the board changed
	 * @throws Exception If the move made is out of bounds
	 */
	private ConnectFour getLowerLeftUpperRightDiagonalWinGameBoard(ConnectFour game) throws Exception
	{
		game.makeMove(0);
		game.makeMove(0);
		game.makeMove(2);
		game.makeMove(1);
		game.makeMove(1);
		game.makeMove(2);
		game.makeMove(2);
		game.makeMove(3);
		game.makeMove(3);
		game.makeMove(3);
		game.makeMove(3);
		
		return game;
	}

	/**
	 * Sets the state of the game to have a win by horizontal alignment
	 * @param game The game
	 * @return The game that is having the board changed
	 * @throws Exception If the move made is out of bounds
	 */
	private ConnectFour getHorizontalWinGameBoard(ConnectFour game) throws Exception
	{
		game.makeMove(0);
		game.makeMove(0);
		game.makeMove(1);
		game.makeMove(1);
		game.makeMove(2);
		game.makeMove(2);
		game.makeMove(3);
		
		return game;
	}

	/**
	 * Sets the state of the game to have a win by vertical alignment
	 * @param game The game
	 * @return The game that is having the board changed
	 * @throws Exception If the move made is out of bounds
	 */
	private ConnectFour getVerticalWinGameBoard(ConnectFour game) throws Exception
	{
		game.makeMove(0);
		game.makeMove(1);
		game.makeMove(0);
		game.makeMove(1);
		game.makeMove(0);
		game.makeMove(1);
		game.makeMove(0);
	
		return game;
	}

	/**
	 * Sets the state of the game to have all gameBoard spaces filled
	 * @param game The game
	 * @return The game that is having the board changed
	 * @throws Exception If the move made is out of bounds
	 */
	private ConnectFour getTieGame(ConnectFour game) throws Exception
	{
		for (int i = 0; i < game.getGameBoard().length; i++)
		{
			for (int j = 0; j < game.getGameBoard()[i].length; j++)
			{
				game.makeMove(j);
			}
		}
		
		return game;
	}
}
