package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

/**
 * Creates view of the connect four game
 * @author Robert Wroblewski
 *
 */
public class Main extends Application
{
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/application/views/ConnectFourView.fxml"));
			Scene scene = new Scene(root, 500, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Connect Four");
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			System.out.println(e.getMessage() + "\n" + e.getCause());
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

}	