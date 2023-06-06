package com.example.client;

import com.example.entities.Student;
import com.example.scene_manager.SceneTransitionManager;
import com.example.services.LoginService;
import com.example.services.LoginServiceImpl;
import com.example.services.StudentPanelService;
import com.example.services.StudentPanelServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class StudentPanelController implements Initializable {

    private CloseableHttpClient httpClient;
    private SceneTransitionManager sceneTransitionManager;
    @FXML
    private ListView<Student> listView;

    @FXML
    private Button logoutButton;
    @FXML
    private Label selectedCountLabel;
    @FXML
    private ListView<Student> optionsListView;
    @FXML
    private Button verifyStatusButton;
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

    private Set<Student> optionsSet;

    private List<Student> students;


    public StudentPanelController(Integer userID, CloseableHttpClient httpClient, SceneTransitionManager sceneTransitionManager) {
        this.userID = userID;
        this.httpClient = httpClient;
        this.sceneTransitionManager = sceneTransitionManager;
    }

    @FXML
    private void searchForInput() {
        String input = searchBox.getText();
        listView.getItems().clear();
        if (input.isEmpty())
            listView.getItems().addAll(students.stream().filter((student) -> !optionsSet.contains(student)).toList());
        else {
            listView.getItems().addAll(students.stream().filter((student) -> student.toString().contains(input) && !optionsSet.contains(student)).toList());
        }
    }

    @FXML
    private void verifyStatus() {
        try {
            if (!studentPanelService.wereMatchingsGenerated()) {
                informationLabel.setText("Nu s-au generat inca repartizarile!");
                return;
            }
            Student matching = studentPanelService.checkUsersMatch();
            if (matching == null) {
                disableSendOptions("Nu ai fost repartizat cu niciun Student!");
                return;
            }
            disableSendOptions("Ai fost repartizat cu " + matching);
        } catch (IOException e) {
            informationLabel.setText("A aparut o eroare la verificarea statusului!");
            e.printStackTrace();
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
            disableSendOptions("Preferintele au fost trimise, asteapta!");
        } catch (Exception e) {
            informationLabel.setText("A aparut o eroare la trimiterea preferintelor!");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentPanelService = new StudentPanelServiceImpl(userID, httpClient);
        optionsSet = new HashSet<>();
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

            if (from == optionsListView)
                optionsSet.remove(student);
            else
                optionsSet.add(student);

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

    @FXML
    private void logout() throws IOException {
        sceneTransitionManager.transitionToLoginScene();
    }

}
