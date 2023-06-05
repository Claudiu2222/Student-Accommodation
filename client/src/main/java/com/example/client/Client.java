package com.example.client;

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

    @Override
    public void start(Stage primaryStage) throws IOException {
        httpClient = HttpClients.createDefault();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
        loader.setControllerFactory(clazz -> new LoginController(null, this.httpClient));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        LoginController.setStage(primaryStage);

        scene.getStylesheets().add(getClass().getResource("stylesheet/Scene1.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        httpClient.close();
    }

    public static void main(String[] args) {
        launch();
    }
}