package application.controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import application.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePageController implements EventHandler<ActionEvent>
{
	@FXML
	private Button newGameButton;
	
	@FXML
	private Button loadGameButton;
	
	@FXML
	private Text titleBanner;
	
	@FXML
	public void initialize()
	{
		this.loadGameButton.setDisable(true);
	}

	@Override
	public void handle(ActionEvent event) 
	{
		if (event.getSource() == this.newGameButton)
		{
			try 
			{
				SceneManager.removeScene("ConnectFourView");
				SceneManager.switchScene((Stage)this.newGameButton.getScene().getWindow(), "SelectPlayersView");
				this.loadGameButton.setDisable(false);
			} 
			catch (IOException e) 
			{
				Alert error = new Alert(AlertType.ERROR, String.format("Error loading the file SelectPlayersView.fxml: %s", e.getMessage()));
				error.showAndWait();
			}
		}
		else if (event.getSource() == this.loadGameButton)
		{
			try 
			{
				SceneManager.switchScene((Stage)this.loadGameButton.getScene().getWindow(), "ConnectFourView");
			} 
			catch (IOException e) 
			{
				Alert error = new Alert(AlertType.ERROR, String.format("Error loading the file ConnectFourView.fxml: %s", e.getMessage()));
				error.showAndWait();
			}
		}
	}

}
