package com.example.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Client extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            String css = Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-panel.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}