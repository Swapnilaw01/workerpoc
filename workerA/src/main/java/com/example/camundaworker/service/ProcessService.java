package com.example.camundaworker.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProcessService {
    private  final RestTemplate restTemplate;


    public ProcessService() {
        this.restTemplate = new RestTemplate();
    }

    public String startMyWorkerProcess(String myWorkerId) {
        String camundaUrl = "http://localhost:8080/engine-rest/process-definition/key/MyWorkerProcess/start";

        // Build process variables
        Map<String, Object> variables = new HashMap<>();
        Map<String, Object> emailIdVar = new HashMap<>();
        emailIdVar.put("value", "test@gmail.com");
        emailIdVar.put("type", "String");
        variables.put("email", emailIdVar);

        Map<String, Object> subjectVar = new HashMap<>();
        subjectVar.put("value", "Test Worker");
        subjectVar.put("type", "String");
        variables.put("subject", subjectVar);

        Map<String, Object> contentVar = new HashMap<>();
        contentVar.put("value", "Hello....");
        contentVar.put("type", "String");
        variables.put("content", contentVar);

        // Request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("variables", variables);
        requestBody.put("businessKey", "BK-" + myWorkerId);

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("demo", "demo"); // Optional: set if authentication needed

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // Send REST POST request to Camunda REST API
        ResponseEntity<String> response = restTemplate.postForEntity(camundaUrl, request, String.class);

        return response.getBody();
    }
}
