package com.example.services;

import com.example.entities.Student;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class StudentPanelServiceImpl implements StudentPanelService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CloseableHttpClient httpClient;
    private Integer userID;

    public StudentPanelServiceImpl(Integer userID, CloseableHttpClient httpClient) {
        this.userID = userID;
        this.httpClient = httpClient;
    }

    public List<Student> getStudents() throws IOException {
        String url = "http://localhost:8090/students";
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return objectMapper.readValue(response.getEntity().getContent(), new TypeReference<List<Student>>() {
            });
        }
    }

    public void sendPreferences(List<Student> preferences) throws IOException {
        String url = "http://localhost:8090/preferences/" + userID;
        HttpPost request = new HttpPost(url);

        String jsonToSend = objectMapper.writeValueAsString(preferences);
        request.setEntity(new StringEntity(jsonToSend));
        request.setHeader("Content-Type", "application/json");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RuntimeException("Failed to send preferences");
            }
        }
    }

    public boolean wereMatchingsGenerated() throws IOException {
        String url = "http://localhost:8090/roommate-matching/count";
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return objectMapper.readValue(response.getEntity().getContent(), Integer.class) > 0;
        }
    }

    public boolean wereUserPreferencesSent() throws IOException {
        String url = "http://localhost:8090/preferences/" + userID;
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return objectMapper.readValue(response.getEntity().getContent(), new TypeReference<Set<Student>>() {
            }).size() > 0;
        }
    }

    public Student checkUsersMatch() throws IOException {
        String url = "http://localhost:8090/roommate-matching/" + userID;
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return objectMapper.readValue(response.getEntity().getContent(), Student.class);
        } catch (MismatchedInputException e) {
            return null;
        }
    }


}
