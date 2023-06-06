package com.example.client;

import com.example.scene_manager.SceneTransitionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class Client extends Application {

    private CloseableHttpClient httpClient;

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        httpClient = HttpClients.createDefault();
        SceneTransitionManager sceneTransitionManager = new SceneTransitionManager(primaryStage, httpClient);
        sceneTransitionManager.transitionToLoginScene();
    }

    @Override
    public void stop() throws Exception {
        httpClient.close();
    }

    public static void main(String[] args) {
        launch();
    }
}