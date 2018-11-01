package application;
	
import java.util.Observable;
import java.util.Observer;

import application.models.ConnectFour;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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