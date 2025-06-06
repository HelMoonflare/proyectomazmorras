package com.alexander;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneManager {
    private static SceneManager instance;

    private Stage stage;
    private HashMap<SceneID, Scene> scenes;

    private SceneManager() {
        scenes = new HashMap<>();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    @SuppressWarnings("exports")
    public void init(Stage stage) {
        this.stage = stage;
    }

    public void setScene(SceneID sceneID, String fxml) {

        Screen screen = Screen.getPrimary();

        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/alexander/views/" + fxml + ".fxml"));
            if (loader.getLocation() == null) {
                throw new IllegalStateException(fxml + " no se encuentra en la ruta especificada");
            }
            Parent root = loader.load();
            Scene scene = new Scene(root, screenWidth * 0.7, screenHeight * 0.7);
            scenes.put(sceneID, scene);
            System.out.println("Escena " + fxml + " configurada correctamente con ID: " + sceneID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeScene(SceneID sceneID) {
        scenes.remove(sceneID);
    }

    public void loadScene(SceneID sceneID) {
        if (scenes.containsKey(sceneID)) {
            stage.setScene(scenes.get(sceneID));
            stage.show();
        } else {
            System.err.println("La escena seleccionada no existe");
        }
    }

    @SuppressWarnings("exports")
    public Scene getScene(SceneID sceneID) {
        if (scenes.containsKey(sceneID)) {
            return scenes.get(sceneID);
        } else {
            System.err.println("La escena seleccionada no existe");
            return null;
        }
    }
}
