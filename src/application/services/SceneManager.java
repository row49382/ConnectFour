package application.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import application.Main;
import application.controllers.ConnectFourController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager
{
	public final static String BASE_VIEW_DIRECTORY = "/application/views";
	
	
	/** Holds the scenes available */
	private static Map<String, Scene> sceneMap;
	
	public static Map<String, Scene> getSceneMap()
	{
		return sceneMap;
	}
	
	public static void initialize(Scene scene, String file)
	{
		sceneMap = new HashMap<>();
		sceneMap.put(file, scene);
	}
	
	/**
	 * Switches the root scene from the current scene to the one passed
	 * as a parameter
	 * @param primaryStage The primary window
	 * @param file The name of the fxml file that will be loaded
	 * @throws IOException If the file does not exist in the location provided
	 */
	public static void switchScene(Stage primaryStage, String file) throws IOException
	{
		if (!sceneMap.containsKey(file))
		{
			Scene scene = new Scene((Parent)FXMLLoader.load(Main.class.getResource(String.format("%s/%s.fxml", BASE_VIEW_DIRECTORY, file))), 470, 500);
			sceneMap.put(file, scene);
		}
		
		primaryStage.setScene(sceneMap.get(file));
		primaryStage.show();
	}
	
	/**
	 * Switches the root scene from the current scene to the one passed
	 * as a parameter. Used to pass the first and second player to the Connect Four Controller
	 * @param primaryStage The primary window
	 * @param file The name of the fxml file that will be loaded
	 * @param firstPlayer the name of the first player
	 * @param secondPlayer the name of the second player
	 * @throws IOException If the file does not exist in the location provided
	 */
	public static void switchScene(Stage primaryStage, String file, String firstPlayer, String secondPlayer) throws IOException
	{
		// creating instance of loader in order to pass in parameters to the controller
		FXMLLoader loader = new FXMLLoader(Main.class.getResource(String.format("%s/%s.fxml", BASE_VIEW_DIRECTORY, file)));
		
		if (!sceneMap.containsKey(file))
		{
			Scene scene = new Scene((Parent)loader.load(), 470, 500);
			sceneMap.put(file, scene);
		}
		
		primaryStage.setScene(sceneMap.get(file));
		
		ConnectFourController controller = loader.<ConnectFourController>getController();
		controller.populatePlayerNames(firstPlayer, secondPlayer);
		
		primaryStage.show();
	}
	
	/**
	 * Removes a scene from the scenemap. This is used to start a new game
	 * by removing the cached game.
	 * @param file The file that is the key for the scene
	 */
	public static void removeScene(String file)
	{
		if (sceneMap.containsKey(file))
		{
			sceneMap.remove(file);
		}
	}
}
