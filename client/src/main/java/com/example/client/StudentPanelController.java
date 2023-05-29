package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

public class StudentPanelController implements Initializable {
    @FXML
    private ListView<String> listView;

    @FXML
    private ListView<String> optionsListView;

    @FXML
    private Button resetOptionsButton;

    @FXML
    private TextField searchBox;
    @FXML
    private Button sendOptionsButton;
    private final String[] students = {
            "Liam Smith",
            "Emma Johnson",
            "Noah Williams",
            "Olivia Brown",
            "William Jones",
            "Ava Miller",
            "James Davis",
            "Isabella Wilson",
            "Benjamin Taylor",
            "Sophia Anderson",
            "Lucas Thomas",
            "Mia Martinez",
            "Henry Jackson",
            "Charlotte White",
            "Alexander Harris",
            "Amelia Thompson",
            "Michael Garcia",
            "Evelyn Robinson",
            "Daniel Clark",
            "Emily Lewis",
            "Matthew Lee",
            "Harper Walker",
            "Joseph Hall",
            "Abigail Green",
            "Samuel Young",
            "Elizabeth Baker",
            "David Hill",
            "Sofia Adams",
            "Josephine Mitchell",
            "Daniel Wright"
    };

    @FXML
    private void searchForInput() {
        String input = searchBox.getText();
        HashSet<String> options = new HashSet<>(optionsListView.getItems());
        listView.getItems().clear();
        if (input.isEmpty())
            listView.getItems().addAll(students);
        else {
            listView.getItems().addAll(Arrays.stream(students).filter((student) -> student.contains(input) && !options.contains(student)).toList());
        }
    }

    @FXML
    private void removeOptions() {
        optionsListView.getItems().clear();
        searchBox.clear();
        listView.getItems().clear();
        listView.getItems().addAll(students);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll(students);

        listView.setOnMouseClicked(mouseEvent -> {
            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                optionsListView.getItems().add(selected);
                listView.getItems().remove(selected);
                listView.getSelectionModel().clearSelection();
            }
        });

        optionsListView.setOnMouseClicked(mouseEvent -> {
            String selected = optionsListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                listView.getItems().add(selected);
                optionsListView.getItems().remove(selected);
                optionsListView.getSelectionModel().clearSelection();
            }
        });
    }
}
