package com.example.client;

import com.example.data.Credential;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class LoginController {

    private static Stage stage;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    private Credential credentials;

    public void setCredentials(Credential credentials) {
        this.credentials = credentials;
    }

    public static void setStage(Stage stage) {
        LoginController.stage = stage;
    }

    @FXML
    protected void onHelloButtonClick() throws IOException {
        if (codeTextField.getText().isEmpty())
            codeTextField.setPromptText("Introdu un numar matricol!");
        else {
            this.credentials = Credential.builder().username(codeTextField.getText()).build();

            // Make a http request to check if this user exist in the first place !
            if (!checkIfUserExist()) {
                codeTextField.setText("");
                codeTextField.setPromptText("Introdu un numar matricol");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Acest username este invalid!");

                // Customize the alert buttons
                ButtonType buttonTypeOk = new ButtonType("OK");
                alert.getButtonTypes().setAll(buttonTypeOk);

                // Handling the result of the alert
                alert.showAndWait().ifPresent(buttonType -> {
                });
                return;
            }

            //Adaugam verificari mai tarziu cand facem backu
            var loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
            Parent root = loader.load();
            LoginController controller2 = loader.getController();
            controller2.setCredentials(this.credentials);
            Scene scene = new Scene(root);
            root.requestFocus();
            scene.getStylesheets().add(getClass().getResource("stylesheet/Scene2.css").toExternalForm());
            stage.setScene(scene);
        }
    }

    @FXML
    protected void login() throws IOException {
        if (passwordTextField.getText().isEmpty()) {
            passwordTextField.setPromptText("Introdu o parola");
        }
        else {

            // Complete the credential
            this.credentials.setPassword(passwordTextField.getText());

            // Check if the credentials are ok
            if (!checkCredentials()) {
                passwordTextField.setText("");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Parola gresita!");

                // Customize the alert buttons
                ButtonType buttonTypeOk = new ButtonType("OK");
                alert.getButtonTypes().setAll(buttonTypeOk);

                // Handling the result of the alert
                alert.showAndWait().ifPresent(buttonType -> {
                });
                return;
            }
            //Adaugam verificari mai tarziu cand facem backu
            Parent root = FXMLLoader.load(getClass().getResource("student-panel.fxml"));
            Scene scene = new Scene(root);
            root.requestFocus();
            scene.getStylesheets().add(getClass().getResource("stylesheet/student-panel.css").toExternalForm());
            stage.setScene(scene);
        }
    }

    private boolean checkIfUserExist() {
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            String url = "http://localhost:8090/login/" + credentials.getUsername();
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    private boolean checkCredentials() {
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            String url = "http://localhost:8090/login";
            HttpPost request = new HttpPost(url);

            // Set the request body as a JSON string
            String requestBody = String.format("{ \"username\": \"%s\", \"password\": \"%s\" }", credentials.getUsername(), credentials.getPassword());
            StringEntity stringEntity = new StringEntity(requestBody);
            request.setEntity(stringEntity);
            request.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            return statusCode == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }
}