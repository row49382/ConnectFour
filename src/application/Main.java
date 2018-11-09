package application;
	
import application.services.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * Creates view of the connect four game
 * @author Robert Wroblewski
 *
 */
public class Main extends Application
{
	/** The title of the game that will be displayed */
	public static final String GAME_TITLE_NAME = "Connect Four";
	
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			SceneManager.initialize();
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("/application/views/ManagePlayersView.fxml"));
		
			Scene scene = new Scene(root, 470, 500);
			primaryStage.setTitle(GAME_TITLE_NAME);
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			Alert error = new Alert(AlertType.ERROR, e.getMessage() + "\n" + e.getCause() + "\n" + e.getStackTrace());
			error.showAndWait();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}	