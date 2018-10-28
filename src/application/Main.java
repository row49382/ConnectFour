package application;
	
import java.util.Observable;
import java.util.Observer;

import application.models.ConnectFour;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 * Creates view of the connect four game to play
 * @author Robert Wroblewski
 *
 */
public class Main extends Application implements EventHandler<ActionEvent>, Observer
{
	
	private final static String RED_TURN_TEXT = "It is RED Player's turn. Click a button to make a move.";
	private final static String BLACK_TURN_TEXT = "It is BLACK Player's turn. Click a button to make a move.";
	
	/** Holds the pointer to the game board */
	private ConnectFour game;
	
	/** Prints out the graphic board to the gui */
	private CircleButton[][] guiBoard;
	
	/** Resets the board */
	private Button resetButton;
	
	/** Undos the last turn */
	private Button undoButton;
	
	/** Holds the buttons */
	private HBox buttonBox;
	
	/** Holds the value of the clicks on the makeMove button */
	private int clicksOnMakeMove;
	
	/** Holds the value of the textfield that displays the game status */
	private Label banner;
	
	/** Holds the integer value of the column last moved that is passed into the game */
	private int currentColumn;
	
	@Override
	public void start(Stage primaryStage) 
	{
		this.game = new ConnectFour("R","B","-");
		this.game.addObserver(this);
		
		this.guiBoard = new CircleButton[6][7];
		this.clicksOnMakeMove = 0;
		this.currentColumn = -1;
		
		try 
		{
			GridPane root = createMainPane();
			Scene scene = new Scene(root, 500, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Connect Four");
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.getStackTrace();
		}
	}
	
	/**
	 * Creates the main pane
	 * @return The main pane 
	 */
	public GridPane createMainPane()
	{
		GridPane mp = new GridPane();
		mp.setVgap(20);
		mp.setHgap(20);
		
		this.fillBoard(mp);
		
		this.resetButton = new Button("Restart");
		this.resetButton.setOnAction(this);
		
		this.undoButton = new Button("Undo");
		this.undoButton.setDisable(true);
		this.undoButton.setOnAction(this);
		
		this.buttonBox = new HBox(10, this.resetButton, this.undoButton);
		mp.add(buttonBox, 1, 3);
				
		this.banner = new Label(RED_TURN_TEXT);
		mp.add(this.banner, 1, 1);
		
		return mp;
	}
	
	/**
	 * Fills the board for the gui
	 * @param mp The Mainpane that is added to the gui
	 */
	public void fillBoard(GridPane mp)
	{	
		GridPane boardPane = new GridPane();
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
					
					if (this.clicksOnMakeMove == 0 || this.clicksOnMakeMove % 2 == 0) 
					{	
						this.banner.setText(BLACK_TURN_TEXT);
					}	
					else
					{	
						this.banner.setText(RED_TURN_TEXT);
					}
					
					this.clicksOnMakeMove++;
					
					// check if the game is over after every move
					this.checkGameOver();
							
				});
			
				guiBoard[i][j] = button;
				boardPane.add(guiBoard[i][j], j, i);
			}		
		}	
		mp.add(boardPane, 1, 2);
	}	
	
	/**
	 * Updates the gameboard after the observer is notified
	 */
	private void updateBoard()
	{
		if (game.isFirstPlayer())
		{
			this.guiBoard[this.game.getColumnSpaces()[currentColumn] + 1][currentColumn].setColor("Black");
		}
		else
		{
			this.guiBoard[this.game.getColumnSpaces()[currentColumn] + 1][currentColumn].setColor("Red");
		}
	}
	

	/**
	 * disables all board buttons in the  when the game is over
	 */
	private void lockBoard()
	{
		for (int i = 0; i < guiBoard.length; i++)
		{
			for (int j = 0; j < guiBoard[i].length; j++)
			{
				guiBoard[i][j].setDisable(true);
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
				guiBoard[i][j].setDefaultStyle();
				guiBoard[i][j].setDisable(false);
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
			
			if (game.isTie())
			{
				endOfGameText.append("The game is a tie. ");
			}
			else if (game.isFirstPlayer())
			{
				endOfGameText.append("The winner is BLACK! ");
			}
			else
			{
				endOfGameText.append("The winner is RED! ");
			}
			
			endOfGameText.append("Click restart to play again..");
			banner.setText(endOfGameText.toString());
			
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
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		if (arg != null && arg instanceof Integer)
		{
			// arg is only not null when a move has been made
			// and passes the column value of the recently made move
			this.currentColumn = (int)arg;	
			this.undoButton.setDisable(false);
			this.updateBoard();		
		}
		else if (arg != null && (arg.toString()).equals("Undo"))
		{
			this.currentColumn = this.game.getPreviousColumn();
			this.guiBoard[this.game.getColumnSpaces()[this.currentColumn]][this.currentColumn].setDefaultStyle();
			this.undoButton.setDisable(true);
			
			if (this.clicksOnMakeMove == 0 || this.clicksOnMakeMove % 2 == 0) 
			{	
				this.banner.setText(BLACK_TURN_TEXT);
			}	
			else
			{	
				this.banner.setText(RED_TURN_TEXT);
			}
			
			this.clicksOnMakeMove--;
		}
		else
		{
			// reset was clicked and the board needs to be reset
			this.resetBoard();
			clicksOnMakeMove = 0;
			currentColumn = -1;
			banner.setText(RED_TURN_TEXT);
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}	