package com.example.services;

import com.example.entities.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    @Getter
    @Setter
    private CloseableHttpClient httpClient;
    private final Integer userID;

    @Getter
    @Setter
    private StudentPanelService studentPanelService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AdminServiceImpl(Integer userID, CloseableHttpClient httpClient) {
        this.userID = userID;
        this.httpClient = httpClient;
        studentPanelService = new StudentPanelServiceImpl(userID, httpClient);
    }

    @Override
    public List<Student> getStudents() {
        try {
            return studentPanelService.getStudents();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteStudent(Student student) throws Exception {
        String url = "http://localhost:8090/users/" + student.getUser_id();
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() >= 400) {
            throw new Exception("Failed to delete student");
        }
    }

    @Override
    public void resetPassword(Student student) throws Exception {
        String url = "http://localhost:8090/users/resetPassword&id=" + student.getUser_id();
        HttpPut request = new HttpPut(url);
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() >= 400) {
            throw new Exception("Failed to delete student");
        }
    }

    @Override
    public void deletePreference(Student student) throws Exception {
        String url = "http://localhost:8090/preferences/" + student.getUser_id();
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() >= 400) {
            throw new Exception("Failed to delete student");
        }
    }

    @Override
    public void deleteMatchings() throws Exception {
        String url = "http://localhost:8090/roommate-matching";
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() >= 400) {
            throw new Exception("Failed to delete matchings");
        }
    }

    @Override
    public void generateMatchings() throws Exception {
        String url = "http://localhost:8090/roommate-matching/generate";
        HttpPost request = new HttpPost(url);
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() >= 400) {
            throw new Exception("Failed to generate matchings");
        }
    }

    @Override
    public Student getPariedStudent(Student selectedStudent) throws IOException {
        studentPanelService.setUserID(selectedStudent.getUser_id());
        Student pairedStudent = studentPanelService.checkUsersMatch();
        System.out.println(pairedStudent);
        return pairedStudent;
    }
}
