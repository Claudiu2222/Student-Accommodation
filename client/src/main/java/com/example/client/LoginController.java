package com.example.client;

import com.example.data.Credential;
import com.example.scene_manager.SceneTransitionManager;
import com.example.services.LoginService;
import com.example.services.LoginServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.impl.client.CloseableHttpClient;


import java.io.IOException;

public class LoginController {
    @Getter
    @Setter
    private LoginService loginService;
    private final CloseableHttpClient httpClient;
    private final SceneTransitionManager sceneTransitionManager;

    public LoginController(Integer userID, CloseableHttpClient httpClient, SceneTransitionManager sceneTransitionManager) {
        this.userID = userID;
        this.httpClient = httpClient;
        this.sceneTransitionManager = sceneTransitionManager;
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
            sceneTransitionManager.transitionToPasswordScene(loginService);
        }
    }

    @FXML
    protected void login() throws IOException {
        if (passwordTextField.getText().isEmpty()) {
            passwordTextField.setPromptText("Introdu o parola");
        } else {

            loginService.getCredential().setPassword(passwordTextField.getText());
            try {
                this.userID = loginService.checkCredentials();
            } catch (Exception e) {
                makeInfoAlert("Parola este incorecta!");
                return;
            }

            System.out.println(this.userID);
            String role = loginService.getRole();
            if (role.equals("student")) {
                sceneTransitionManager.transitionToStudentPanelScene(this.userID);
            } else {
                sceneTransitionManager.transitionToAdminPanelScene(this.userID);
            }
        }
    }

    public static void makeInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(message);

        ButtonType buttonTypeOk = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeOk);

        alert.showAndWait().ifPresent(buttonType -> {
        });
    }

    @FXML
    private TextField codeTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button loginButton;
    private Integer userID;

}