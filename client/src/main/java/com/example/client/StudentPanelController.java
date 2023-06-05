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
    private Button resetPreferencesButton;

    @FXML
    private TextField searchBox;
    @FXML
    private Label informationLabel;
    @FXML
    private Button sendPreferencesButton;

    private StudentPanelService studentPanelService;

    private Integer userID;

    private List<Student> students;

    public StudentPanelController(Integer userID) {
        this.userID = userID;
    }

    @FXML
    private void searchForInput() {
        String input = searchBox.getText();
        HashSet<Student> options = new HashSet<>(optionsListView.getItems());
        listView.getItems().clear();
        if (input.isEmpty())
            listView.getItems().addAll(students.stream().filter((student) -> !options.contains(student)).toList());
        else {
            listView.getItems().addAll(students.stream().filter((student) -> student.toString().contains(input) && !options.contains(student)).toList());
        }
    }

    @FXML
    private void resetPreferences() {
        optionsListView.getItems().clear();
        searchBox.clear();
        updateLabel();
        listView.getItems().clear();
        listView.getItems().addAll(students);
    }

    @FXML
    private void sendPreferences() {
        List<Student> options = optionsListView.getItems();

        if (options.size() == 0) {
            informationLabel.setText("Nu ai selectat niciun student!");
            return;
        }
        try {
            studentPanelService.sendPreferences(options);
        } catch (Exception e) {
            informationLabel.setText("A aparut o eroare la trimiterea preferintelor!");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentPanelService = new StudentPanelServiceImpl(userID);
        try {
            students = studentPanelService.getStudents().stream().filter((student) -> !student.getUser_id().equals(userID)).toList();
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
            moveSelectedStudent(listView, optionsListView, selected);

        });

        optionsListView.setOnMouseClicked(mouseEvent -> {
            Student selected = optionsListView.getSelectionModel().getSelectedItem();
            moveSelectedStudent(optionsListView, listView, selected);
        });


        disableOptionsIfUserAlreadySentPreferences();
        disableOptionsIfMatchingsWereGenerated();


    }

    private void disableOptionsIfUserAlreadySentPreferences() {
        try {
            if (studentPanelService.wereUserPreferencesSent()) {
                disableSendOptions("Preferintele au fost trimise deja, asteapta!");
            }
        } catch (IOException e) {
            informationLabel.setText("A aparut o eroare la verificarea existentei preferintelor!");
        }

    }

    private void moveSelectedStudent(ListView<Student> from, ListView<Student> to, Student student) {
        if (student != null) {
            to.getItems().add(student);
            from.getItems().remove(student);
            from.getSelectionModel().clearSelection();
            updateLabel();
        }
    }

    private void disableOptionsIfMatchingsWereGenerated() {
        try {
            if (studentPanelService.wereMatchingsGenerated()) {
                disableSendOptions("Matching-urile au fost generate deja!");
            }
        } catch (IOException e) {
            informationLabel.setText("A aparut o eroare la verificarea existentei matching-urilor!");
        }
    }

    private void disableSendOptions(String informationLabelText) {
        optionsListView.setDisable(true);
        listView.setDisable(true);
        searchBox.setDisable(true);
        sendPreferencesButton.setDisable(true);
        resetPreferencesButton.setDisable(true);
        informationLabel.setText(informationLabelText);
    }

    private void updateLabel() {
        selectedCountLabel.setText(optionsListView.getItems().size() + "/10");
    }
}
