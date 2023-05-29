package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private static Stage stage;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;


    public static void setStage(Stage stage) {
        LoginController.stage = stage;
    }

    @FXML
    protected void onHelloButtonClick() throws IOException {
        if (codeTextField.getText().isEmpty())
            codeTextField.setPromptText("Introdu un numar matricol!");
        else {
            //Adaugam verificari mai tarziu cand facem backu
            Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
            Scene scene = new Scene(root);
            root.requestFocus();
            scene.getStylesheets().add(getClass().getResource("stylesheet/Scene2.css").toExternalForm());
            stage.setScene(scene);
        }
    }

    @FXML
    protected void login() throws IOException {
        if (passwordTextField.getText().isEmpty())
            passwordTextField.setPromptText("Introdu o parola");
        else {
            //Adaugam verificari mai tarziu cand facem backu
            Parent root = FXMLLoader.load(getClass().getResource("student-panel.fxml"));
            Scene scene = new Scene(root);
            root.requestFocus();
            scene.getStylesheets().add(getClass().getResource("stylesheet/student-panel.css").toExternalForm());
            stage.setScene(scene);
        }
    }
}