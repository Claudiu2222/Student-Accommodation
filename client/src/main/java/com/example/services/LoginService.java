package com.example.services;

import com.example.client.StudentPanelController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public interface LoginService {
    Integer checkCredentials();

    void checkIfUsernameExist();

    void changePassword(String username, String oldPassword, String password);

    String getRole();
    
}
