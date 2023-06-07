package com.example.scene_manager;

import com.example.client.AdminPanelController;
import com.example.client.LoginController;
import com.example.client.StudentPanelController;
import com.example.services.LoginService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Objects;

public class SceneTransitionManager {

    private Stage stage;
    private CloseableHttpClient httpClient;

    public SceneTransitionManager(Stage stage, CloseableHttpClient httpClient) {
        this.stage = stage;
        this.httpClient = httpClient;
        this.stage.setResizable(false);
        this.stage.show();
    }

    public void transitionToLoginScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/client/Scene1.fxml")));
        loader.setControllerFactory(clazz -> new LoginController(null, this.httpClient, this));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/client/stylesheet/Scene1.css")).toExternalForm());
        stage.setScene(scene);
    }

    public void transitionToPasswordScene(LoginService loginService) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/client/Scene2.fxml")));
        loader.setControllerFactory(clazz -> new LoginController(null, this.httpClient, this));
        Parent root = loader.load();
        LoginController controller2 = loader.getController();
        controller2.setLoginService(loginService);
        Scene scene = new Scene(root);
        root.requestFocus();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/client/stylesheet/Scene2.css")).toExternalForm());
        stage.setScene(scene);
    }

    public void transitionToStudentPanelScene(Integer userID) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/client/student-panel.fxml")));
        loader.setControllerFactory(clazz -> new StudentPanelController(userID, this.httpClient, this));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/client/stylesheet/student-panel.css")).toExternalForm());
        stage.setScene(scene);
    }

    public void transitionToAdminPanelScene(Integer userID) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/com/example/client/admin-panel.fxml")));
        loader.setControllerFactory(clazz -> new AdminPanelController(userID, this.httpClient, this));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/client/stylesheet/admin-panel.css")).toExternalForm());
        stage.setScene(scene);
    }
}