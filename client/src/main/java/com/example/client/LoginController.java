package com.example.client;

import com.example.data.Credential;
import com.example.services.LoginServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.impl.client.CloseableHttpClient;


import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @Getter
    @Setter
    private LoginServiceImpl loginService;
    private CloseableHttpClient httpClient;

    public LoginController(Integer userID, CloseableHttpClient httpClient) {
        this.userID = userID;
        this.httpClient = httpClient;
        if (this.loginService == null) {
            this.loginService = new LoginServiceImpl();
        }
    }

    @FXML
    protected void onHelloButtonClick() throws IOException {
        if (codeTextField.getText().isEmpty()) {
            codeTextField.setPromptText("Introdu un numar matricol!");
        } else {
            Credential credential = Credential.builder().username(codeTextField.getText()).build();
            loginService.setCredential(credential);

            try {
                loginService.checkIfUsernameExist();
            } catch (Exception e) {
                makeInfoAlert("Acest username este invalid!");
                return;
            }

            // Change the scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
            loader.setControllerFactory(clazz -> new LoginController(null, this.httpClient));
            Parent root = loader.load();
            LoginController controller2 = loader.getController();
            controller2.setLoginService(loginService);
            Scene scene = new Scene(root);
            root.requestFocus();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet/Scene2.css")).toExternalForm());
            stage.setScene(scene);

        }
    }

    @FXML
    protected void login() throws IOException {
        if (passwordTextField.getText().isEmpty()) {
            passwordTextField.setPromptText("Introdu o parola");
        } else {
            // Add the password to the credential
            loginService.getCredential().setPassword(passwordTextField.getText());

            // Check if the password is correct
            try {
                this.userID = loginService.checkCredentials();
            } catch (Exception e) {
                makeInfoAlert("Parola este incorecta!");
                return;
            }


            System.out.println(this.userID);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student-panel.fxml"));
            loader.setControllerFactory(clazz -> new StudentPanelController(this.userID, this.httpClient));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            root.requestFocus();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet/student-panel.css")).toExternalForm());
            stage.setScene(scene);
        }
    }

    public static void makeInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(message);


        ButtonType buttonTypeOk = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeOk);

        // Waiting for the user to press the OK button
        alert.showAndWait().ifPresent(buttonType -> {
        });
    }

    // FXML Elements
    private static Stage stage;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    private Integer userID;
    public static void setStage(Stage stage) {
        LoginController.stage = stage;
    }
}