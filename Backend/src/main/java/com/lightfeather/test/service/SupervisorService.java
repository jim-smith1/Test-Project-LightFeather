package com.lightfeather.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightfeather.test.entity.NotificationRequest;
import com.lightfeather.test.entity.Supervisor;
import com.lightfeather.test.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SupervisorService {

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public List<String> getAllSupervisors() throws IOException, URISyntaxException {
        HttpGet request = new HttpGet("https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new IOException("Failed to retrieve supervisors. Status code: " + response.getStatusLine().getStatusCode());
            }

            Supervisor[] supervisors = objectMapper.readValue(EntityUtils.toString(response.getEntity()), Supervisor[].class);

            return Arrays.stream(supervisors)
                    .map(this::formatSupervisor)
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    private String formatSupervisor(Supervisor supervisor) {
        String jurisdiction = supervisor.getJurisdiction();
        String firstName = supervisor.getFirstName();
        String lastName = supervisor.getLastName();

        jurisdiction = jurisdiction.replaceAll("\\d", "").trim();

        return jurisdiction + " - " + lastName + ", " + firstName;
    }

    public void submitNotificationRequest(NotificationRequest request) throws ValidationException {
        try {
            validateNotificationRequest(request);
            System.out.println("Notification request submitted: " + request.toString());
        } catch (ValidationException e) {
            System.err.println("Validation failed: " + e.getMessage());
            throw e;
        }
    }

    private void validateNotificationRequest(NotificationRequest request) throws ValidationException {
        if (request.getFirstName() == null || request.getFirstName().isEmpty() ||
                request.getLastName() == null || request.getLastName().isEmpty() ||
                request.getSupervisor() == null || request.getSupervisor().isEmpty()) {
            throw new ValidationException("First name, last name, and supervisor are required fields.");
        }

        if (!Pattern.matches("[a-zA-Z]+", request.getFirstName()) || !Pattern.matches("[a-zA-Z]+", request.getLastName())) {
            throw new ValidationException("First name and last name must contain only letters.");
        }

        if (request.getEmail() != null && !Pattern.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b", request.getEmail())) {
            throw new ValidationException("Invalid email format.");
        }

        if (request.getPhoneNumber() != null && !Pattern.matches("\\d{10}", request.getPhoneNumber())) {
            throw new ValidationException("Invalid phone number format.");
        }
    }
}