package application.controllers;

import java.io.IOException;

import application.services.PlayersManager;
import application.services.SceneManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SelectPlayersController implements EventHandler<ActionEvent>
{
	/** text that is displayed when both player textfields are empty */
	private static final String LOAD_GAME_TEXT = "Game cannot be started until both player names are set*";

	@FXML
	private Label firstPlayerLabel;
	
	@FXML
	private Label secondPlayerLabel;
	
	@FXML
	private TextField firstPlayerTF;
	
	@FXML
	private TextField secondPlayerTF;
	
	@FXML
	private Button loadGameButton;
	
	@FXML
	private Label loadGameLabel;
	
	
	@FXML
	private void initialize()
	{
		this.firstPlayerLabel.setText("Player One: ");
		this.secondPlayerLabel.setText("Player Two: ");
		this.loadGameLabel.setText(LOAD_GAME_TEXT);
		
		this.setListeners();
	}

	@Override
	public void handle(ActionEvent event) 
	{
		if (event.getSource() == this.loadGameButton)
		{
			PlayersManager.firstPlayer = this.firstPlayerTF.getText();
			PlayersManager.secondPlayer = this.secondPlayerTF.getText();
			
			try 
			{
				SceneManager.switchScene((Stage)this.loadGameButton.getScene().getWindow(), "ConnectFourView");
			} 
			catch (IOException e) 
			{
				Alert error = new Alert(AlertType.ERROR, String.format("Error loading the file MangePlayersView.fxml: %s", e.getMessage()));
				error.showAndWait();
			}
		}	
	}
	
	/**
	 * Adds the listeners to the controls
	 */
	private void setListeners()
	{
		this.firstPlayerTF.textProperty().addListener((observable, oldValue, newValue) -> 
		{
			if (!this.firstPlayerTF.getText().equals("") && !this.secondPlayerTF.getText().equals(""))
			{
				this.loadGameLabel.setText("");
				this.loadGameButton.setDisable(false);
			}
			else
			{
				this.loadGameLabel.setText(LOAD_GAME_TEXT);
				this.loadGameButton.setDisable(true);
			}
		});
		
	
		this.secondPlayerTF.textProperty().addListener((observable, oldValue, newValue) -> 
		{
			if (!this.firstPlayerTF.getText().equals("") && !this.secondPlayerTF.getText().equals(""))
			{
				this.loadGameLabel.setText("");
				this.loadGameButton.setDisable(false);
			}
			else
			{

				this.loadGameLabel.setText(LOAD_GAME_TEXT);
				this.loadGameButton.setDisable(true);
			}
		});
		
	}
}
