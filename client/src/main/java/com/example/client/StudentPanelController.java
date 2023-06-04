package com.example.client;

import com.example.entities.Student;
import com.example.services.StudentPanelService;
import com.example.services.StudentPanelServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Data
public class StudentPanelController implements Initializable {
    @FXML
    private ListView<Student> listView;

    @FXML
    private Label selectedCountLabel;
    @FXML
    private ListView<Student> optionsListView;

    @FXML
    private Button resetOptionsButton;

    @FXML
    private TextField searchBox;
    @FXML
    private Button sendOptionsButton;

    private StudentPanelService studentPanelService;

    private Integer userID;

    private List<Student> students;

    public StudentPanelController(Integer userID) {
        this.userID = userID;
    }

    @FXML
    private void searchForInput() {
        String input = searchBox.getText();
        System.out.println("USSSer ID: " + userID);
        HashSet<Student> options = new HashSet<>(optionsListView.getItems());
        listView.getItems().clear();
        if (input.isEmpty())
            listView.getItems().addAll(students.stream().filter((student) -> !options.contains(student)).toList());
        else {
            listView.getItems().addAll(students.stream().filter((student) -> student.toString().contains(input) && !options.contains(student)).toList());
        }
    }

    @FXML
    private void removeOptions() {
        optionsListView.getItems().clear();
        searchBox.clear();
        updateLabel();
        listView.getItems().clear();
        listView.getItems().addAll(students);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StudentPanelService studentPanelService = new StudentPanelServiceImpl();
        try {
            System.out.println("User IsadsadasdsaD: " + userID);
            students = studentPanelService.getStudents();
            listView.getItems().addAll(students);
        } catch (IOException e) {
            e.printStackTrace();
        }

        listView.setOnMouseClicked(mouseEvent -> {
            Student selected = listView.getSelectionModel().getSelectedItem();
            if (optionsListView.getItems().size() == 10) {
                listView.getSelectionModel().clearSelection();
                return;
            }
            if (selected != null) {
                optionsListView.getItems().add(selected);
                listView.getItems().remove(selected);
                listView.getSelectionModel().clearSelection();
                updateLabel();
            }
        });

        optionsListView.setOnMouseClicked(mouseEvent -> {
            Student selected = optionsListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                listView.getItems().add(selected);
                optionsListView.getItems().remove(selected);
                optionsListView.getSelectionModel().clearSelection();
                updateLabel();
            }
        });
    }

    private void updateLabel() {
        selectedCountLabel.setText(optionsListView.getItems().size() + "/10");
    }
}
