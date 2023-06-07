package com.example.services;

import com.example.data.Credential;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class LoginServiceImpl implements LoginService {

    @Setter
    @Getter
    private Credential credential;

    @Override
    public Integer checkCredentials() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://localhost:8090/login");
            request.setHeader("Content-Type", "application/json");
            String jsonToSend = "{\"username\":\"" + credential.getUsername() + "\",\"password\":\"" + credential.getPassword() + "\"}";
            request.setEntity(new org.apache.http.entity.StringEntity(jsonToSend));
            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == 201) {
                changePasswordBasedOnAlert();
                return checkCredentials();
            }

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("User does not exist!");
            }
            return Integer.parseInt(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            throw new RuntimeException("Password is not correct!");
        }
    }

    private void changePasswordBasedOnAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Change password");
        alert.setHeaderText("You need to change your password!");
        alert.setContentText("Please enter your new password:");

        TextField textField = new TextField();
        textField.setPromptText("New password");

        alert.getDialogPane().setContent(textField);

        alert.showAndWait();

        System.out.println("parola: " + textField.getText());
        changePassword(credential.getUsername(), credential.getPassword(), textField.getText());
    }

    @Override
    public void checkIfUsernameExist() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet("http://localhost:8090/login/" + credential.getUsername());
            HttpResponse response = httpClient.execute(request);

            System.out.println(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("User does not exist!");
            }
        } catch (Exception e) {
            throw new RuntimeException("User does not exist!");
        }
    }

    @Override
    public void changePassword(String username, String oldPassword, String password) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://localhost:8090/login/username=" + username + "&oldpassword=" + oldPassword + "&password=" + password);
            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("User does not exist!");
            }

            this.credential.setPassword(password);
        } catch (Exception e) {
            throw new RuntimeException("User does not exist!");
        }
    }

    @Override
    public String getRole() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet("http://localhost:8090/login/getRole&username=" + credential.getUsername());
            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("User does not exist!");
            }

            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException("User does not exist!");
        }
    }


}
