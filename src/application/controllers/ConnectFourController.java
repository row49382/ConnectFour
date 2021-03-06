package application.controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import application.CircleButton;
import application.models.ConnectFour;
import application.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Manages requests from the gui to the model
 * @author Robert Wroblewski
 *
 */
public class ConnectFourController implements EventHandler<ActionEvent>, Observer
{
	private static String RED_TURN_TEXT;
	private static String BLACK_TURN_TEXT;
		
	/** The graphic game board of the gui */
	@FXML
	private CircleButton[][] guiBoard;
	
	/** The gridpane that holds the gui board */
	@FXML
	private GridPane guiBoardGP;
	
	/** Resets the board */
	@FXML
	private Button resetButton;
	
	/** Undos the last turn */
	@FXML
	private Button undoButton;
	
	/** Holds the buttons */
	@FXML
	private HBox buttonBox;
	
	/** Holds the value of the textfield that displays the game status */
	@FXML
	private Text banner;
	
	/** Holds the home button that when clicked loads the home screen */
	@FXML
	private Button homeButton;
	
	/** Holds the pointer to the game board */
	private ConnectFour game;

	/** Holds the integer value of the column last moved that is passed into the game */
	private int currentColumn;
	
	/**
	 * Intializes the fxml attributes for the view
	 */
	@FXML
	private void initialize()
	{
		this.game = new ConnectFour("R","B","-");
		this.game.addObserver(this);
		
		this.currentColumn = -1;
		this.fillBoard();
		
	}
	
	/**
	 * Populates the player names for the game
	 * @param firstPlayer The first player name
	 * @param secondPlayer The second player name
	 */
	public void populatePlayerNames(String firstPlayer, String secondPlayer)
	{
		this.game.setRedTokenPlayer(firstPlayer);
		this.game.setBlackTokenPlayer(secondPlayer);
		
		RED_TURN_TEXT = String.format("It is %s's turn. Click a button to make a move.", this.game.getRedTokenPlayer());
		BLACK_TURN_TEXT = String.format("It is %s's turn. Click a button to make a move.", this.game.getBlackTokenPlayer());
		
		this.banner.setText(RED_TURN_TEXT);
	}
	
	/**
	 * Fills the board for the gui
	 * @param mp The Mainpane that is added to the gui
	 */
	public void fillBoard()
	{	
		this.guiBoard = new CircleButton[6][7];
		
		for (int i = 0; i < this.game.getGameBoard().length; i++)
		{	
			for (int j = 0; j < this.game.getGameBoard()[i].length; j++)
			{
				CircleButton button = new CircleButton(i, j);
				button.setOnAction((x) -> 
				{
					this.currentColumn = button.getColumn();
					
					try 
					{
						this.game.makeMove(currentColumn);	
					}
					catch (Exception e) 
					{
						Alert errorAlert = new Alert(AlertType.ERROR, e.getMessage());
						errorAlert.showAndWait();
					}
					
					this.updateBoardText();
							
					// check if the game is over after every move
					this.checkGameOver();
				});
			
				this.guiBoard[i][j] = button;
				this.guiBoardGP.add(this.guiBoard[i][j], j, i);
			}		
		}	
	}	
	
	/**
	 * Updates the gameboard after the observer is notified
	 */
	private void updateBoard()
	{
		if (this.game.isFirstPlayer())
		{
			this.guiBoard[this.game.getColumnSpaces()[this.currentColumn] + 1][this.currentColumn].setColor("Black");
		}
		else
		{
			this.guiBoard[this.game.getColumnSpaces()[this.currentColumn] + 1][this.currentColumn].setColor("Red");
		}
	}
	
	/**
	 * Sets the board text every move based on if it's the first
	 * players turn 
	 */
	private void updateBoardText()
	{
		if (!this.game.isFirstPlayer()) 
		{	
			this.banner.setText(BLACK_TURN_TEXT);
		}	
		else
		{	
			this.banner.setText(RED_TURN_TEXT);
		}
	}
	

	/**
	 * disables all board buttons when the game is over
	 */
	private void lockBoard()
	{
		for (int i = 0; i < this.guiBoard.length; i++)
		{
			for (int j = 0; j < this.guiBoard[i].length; j++)
			{
				this.guiBoard[i][j].setDisable(true);
			}
		}
		
		this.undoButton.setDisable(true);
	}
	
	/**
	 * Resets the board to make all buttons yellow and clickable
	 */
	private void resetBoard()
	{
		for (int i = 0; i < this.guiBoard.length; i++)
		{
			for (int j = 0; j < this.guiBoard[i].length; j++)
			{
				this.guiBoard[i][j].setDefaultStyle();
				this.guiBoard[i][j].setDisable(false);
			}
		}
	}
	
	/**
	 * Checks if the game is over after making a move
	 */
	private void checkGameOver()
	{
		if (this.game.gameOver())
		{
			StringBuilder endOfGameText = new StringBuilder(20);
			
			if (this.game.isTie())
			{
				endOfGameText.append("The game is a tie. ");
			}
			else if (this.game.isFirstPlayer())
			{
				endOfGameText.append(String.format("The winner is %s! ", this.game.getBlackTokenPlayer()));
			}
			else
			{
				endOfGameText.append(String.format("The winner is %s! ", this.game.getRedTokenPlayer()));
			}
			
			endOfGameText.append("Click restart to play again..");
			this.banner.setText(endOfGameText.toString());
			
			this.lockBoard();
		}
	}
	
	@Override
	public void handle(ActionEvent event) 
	{	
		if (event.getSource() == this.resetButton)
		{
			this.game.reset();
		}		
		else if (event.getSource() == this.undoButton)
		{
			this.game.undoMove();
		}
		else if (event.getSource() == this.homeButton)
		{
			try 
			{
				SceneManager.switchScene((Stage)this.homeButton.getScene().getWindow(), "HomePageView");
			} 
			catch (IOException e) 
			{
				Alert error = new Alert(AlertType.ERROR, String.format("Error loading the file HomePageView.fxml: %s", e.getMessage()));
				error.showAndWait();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		if (arg != null)
		{
			if ((arg.toString()).equals("MakeMove"))
			{
				this.currentColumn = this.game.getPreviousColumn().peek();	
				this.undoButton.setDisable(false);
				this.updateBoard();	
			}
			else if (arg instanceof Integer)
			{
				// passes the column value of the recently made move for undo
				this.currentColumn = (int)arg;
				this.guiBoard[this.game.getColumnSpaces()[this.currentColumn]][this.currentColumn].setDefaultStyle();
				
				if (this.game.getPreviousColumn().isEmpty())
				{
					this.undoButton.setDisable(true);
				}
				
				this.updateBoardText();
			}
		}
		else
		{
			// reset was clicked and the board needs to be reset
			this.resetBoard();
			currentColumn = -1;
			this.banner.setText(RED_TURN_TEXT);
		}
	}
}
