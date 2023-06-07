package com.example.client;

import com.example.entities.Student;
import com.example.scene_manager.SceneTransitionManager;
import com.example.services.AdminService;
import com.example.services.AdminServiceImpl;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class AdminPanelController implements Initializable {

    private final CloseableHttpClient httpClient;
    private Integer userID;
    private final SceneTransitionManager sceneTransitionManager;

    // -- COD REFACTORIZAT

    public AdminPanelController(Integer userID, CloseableHttpClient httpClient, SceneTransitionManager sceneTransitionManager) {
        this.userID = userID;
        this.httpClient = httpClient;
        this.sceneTransitionManager = sceneTransitionManager;
    }

    @Getter
    @Setter
    private AdminService adminService;
    @FXML
    private Label labelAdmin;
    @FXML
    private ListView<Student> listView;
    List<Student> students;
    @FXML
    private TextField searchBox;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button deleteAccountButton;
    @FXML
    private Label statusLabelRooms;
    @FXML
    private Button generateButton;
    @FXML
    private Button deleteRoomsButton;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label nameLabel;
    @FXML
    private Button deletePrefferences;
    Student selectedStudent;
    @FXML
    private Label pariedLabel;
    @FXML
    private void deleteRooms() {
//        Text info = new Text("Status Repartizări: ");
//        info.setFill(Color.BLACK);
//        info.setFont(Font.font("Arial", FontWeight.BOLD, 17));
//        info.setTextAlignment(TextAlignment.CENTER);
//
//        Text status = new Text("Invalide");
//        status.setFill(Color.RED);
//        status.setFont(Font.font("Arial", FontWeight.BOLD, 17));
//        status.setTextAlignment(TextAlignment.CENTER);
//
//        TextFlow flowText = new TextFlow(info, status);
//        flowText.setTextAlignment(TextAlignment.CENTER);
//        flowText.setPadding(new Insets(12));
//
//        statusLabelRooms.setText("");
//        statusLabelRooms.setGraphic(flowText);
        try {
            adminService.deleteMatchings();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success deleting matchings");
            alert.setContentText("Matchings deleted successfully");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting matchings");
            alert.setContentText("Error while deleting matchings");
            alert.showAndWait();
        }
    }

    @FXML
    private void generateMatching() {
//        Text info = new Text("Status Repartizări: ");
//        info.setFill(Color.BLACK);
//        info.setFont(Font.font("Arial", FontWeight.BOLD, 17));
//        info.setTextAlignment(TextAlignment.CENTER);
//
//        Text status = new Text("Generate");
//        status.setFill(Color.GREEN);
//        status.setFont(Font.font("Arial", FontWeight.BOLD, 17));
//        status.setTextAlignment(TextAlignment.CENTER);
//
//        TextFlow flowText = new TextFlow(info, status);
//        flowText.setTextAlignment(TextAlignment.CENTER);
//        flowText.setPadding(new Insets(12));
//
//        statusLabelRooms.setText("");
//        statusLabelRooms.setGraphic(flowText);

        try {
            adminService.generateMatchings();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success generating matchings");
            alert.setContentText("Matchings generated successfully");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error generating matchings");
            alert.setContentText("Error while generating matchings");
            alert.showAndWait();
        }
    }

    @FXML
    private void resetPassword() {
        try {
            adminService.resetPassword(selectedStudent);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Password reset successfully");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Error while resetting password");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteStudent() {
        try {
            adminService.deleteStudent(selectedStudent);

            students.removeAll(Collections.singleton(selectedStudent));
            this.listView.getItems().removeAll(selectedStudent);

            changePasswordButton.setVisible(false);
            deleteAccountButton.setVisible(false);
            deletePrefferences.setVisible(false);
            this.nameLabel.setVisible(false);
            this.pariedLabel.setVisible(false);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Student deleted successfully");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Error while deleting student");
            alert.showAndWait();
        }
    }

    @FXML
    private void deletePrefferences() {
        try {
            adminService.deletePreference(selectedStudent);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Prefferences deleted successfully");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Error while deleting prefferences");
            alert.showAndWait();

        }
    }



    @FXML
    private void showOptionOnSelectedItem(MouseEvent event) {
        this.selectedStudent = listView.getFocusModel().getFocusedItem();
        changePasswordButton.setVisible(true);
        deleteAccountButton.setVisible(true);
        deletePrefferences.setVisible(true);


        this.nameLabel.setText(selectedStudent.getFullName());
        this.nameLabel.setVisible(true);

        // Check for parring
        try {
            Student studentMatched = adminService.getPariedStudent(selectedStudent);

            this.pariedLabel.setText("A fost repartizat cu: " + studentMatched.getFullName());
        } catch (Exception e) {
            this.pariedLabel.setText("Nu a fost repartizat");
        }
        this.pariedLabel.setVisible(true);
    }

    @FXML
    private void searchForInput(Event e) {
        String input = searchBox.getText();

        listView.getItems().removeAll(students);
        if (input.equals("")) {
            listView.getItems().addAll(students);
        } else {
            listView.getItems().addAll(students.stream().filter(student -> student.getFullName().contains(input)).toArray(Student[]::new));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.adminService = new AdminServiceImpl(userID, httpClient);
        this.students = adminService.getStudents();

        listView.getItems().addAll(students);


        changePasswordButton.setVisible(false);
        changePasswordButton.setFocusTraversable(false);
        deleteAccountButton.setFocusTraversable(false);
        deleteAccountButton.setVisible(false);
        deletePrefferences.setVisible(false);
        deletePrefferences.setFocusTraversable(false);
        generateButton.setFocusTraversable(false);
        deleteRoomsButton.setFocusTraversable(false);
        nameLabel.setVisible(false);
        pariedLabel.setVisible(false);

        statusLabelRooms.setText("");
    }

    @FXML
    private void logout() throws IOException {
        sceneTransitionManager.transitionToLoginScene();
    }
}
