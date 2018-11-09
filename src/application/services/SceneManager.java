package application.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager 
{
	public final static String BASE_VIEW_DIRECTORY = "/application/views";
	
	/** Holds the scenes available */
	private static Map<String, Scene> sceneMap;
	
	public static void initialize()
	{
		sceneMap = new HashMap<>();
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

}
