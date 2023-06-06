package com.example.client;

import com.example.entities.Student;
import com.example.services.AdminService;
import com.example.services.AdminServiceImpl;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class AdminPanelController implements Initializable {

    private final CloseableHttpClient httpClient;
    private Integer userID;

    // -- COD REFACTORIZAT

    public AdminPanelController(Integer userID, CloseableHttpClient httpClient) {
        this.userID = userID;
        this.httpClient = httpClient;
    }

    @Getter
    @Setter
    private AdminServiceImpl adminService;

    //
    @FXML
    private Label labelAdmin;

    @FXML
    private ListView<Student> listView;

    List<Student> students;

    @FXML
    private Button searchButton;

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
    private void deleteRooms() {
        Text info = new Text("Status Repartizări: ");
        info.setFill(Color.BLACK);
        info.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        info.setTextAlignment(TextAlignment.CENTER);

        Text status = new Text("Invalide");
        status.setFill(Color.RED);
        status.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        status.setTextAlignment(TextAlignment.CENTER);

        TextFlow flowText = new TextFlow(info, status);
        flowText.setTextAlignment(TextAlignment.CENTER);
        flowText.setPadding(new Insets(12));

        statusLabelRooms.setText("");
        statusLabelRooms.setGraphic(flowText);
    }

    @FXML
    private void generateMatching() {
        Text info = new Text("Status Repartizări: ");
        info.setFill(Color.BLACK);
        info.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        info.setTextAlignment(TextAlignment.CENTER);

        Text status = new Text("Generate");
        status.setFill(Color.GREEN);
        status.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        status.setTextAlignment(TextAlignment.CENTER);

        TextFlow flowText = new TextFlow(info, status);
        flowText.setTextAlignment(TextAlignment.CENTER);
        flowText.setPadding(new Insets(12));

        statusLabelRooms.setText("");
        statusLabelRooms.setGraphic(flowText);
    }

    @FXML
    private void showOptionOnSelectedItem(MouseEvent event) {
        Student item = listView.getFocusModel().getFocusedItem();
        changePasswordButton.setVisible(true);
        deleteAccountButton.setVisible(true);

        this.nameLabel.setText(item.getFullName());
        this.nameLabel.setVisible(true);
    }

    @FXML
    private void searchForInput(Event e) {
        String input = searchBox.getText();


        // Changing the list
        listView.getItems().removeAll(students);
        if (input.equals("")) {
            listView.getItems().addAll(students);
        } else {
            listView.getItems().addAll(students.stream().filter(student -> student.getFullName().contains(input)).toArray(Student[]::new));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AdminService adminService = new AdminServiceImpl(userID, httpClient);
        this.students = adminService.getStudents();

        listView.getItems().addAll(students); // IDK REALLY


        changePasswordButton.setVisible(false);
        changePasswordButton.setFocusTraversable(false);
        deleteAccountButton.setFocusTraversable(false);
        deleteAccountButton.setVisible(false);
        generateButton.setFocusTraversable(false);
        deleteRoomsButton.setFocusTraversable(false);
        nameLabel.setVisible(false);

        statusLabelRooms.setText("");
    }
}
