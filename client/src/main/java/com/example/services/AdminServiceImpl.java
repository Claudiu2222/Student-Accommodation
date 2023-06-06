package com.example.services;

import com.example.entities.Student;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    @Getter
    @Setter
    private CloseableHttpClient httpClient;
    private Integer userID;

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
}
