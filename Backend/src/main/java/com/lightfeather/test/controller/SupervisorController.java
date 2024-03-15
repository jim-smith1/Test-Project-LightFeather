package com.lightfeather.test.controller;

import com.lightfeather.test.entity.NotificationRequest;
import com.lightfeather.test.exception.ValidationException;
import com.lightfeather.test.service.SupervisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class SupervisorController {

    private final SupervisorService supervisorService;

    @GetMapping("/supervisors")
    public List<String> getAllSupervisors() throws IOException, URISyntaxException {
        return supervisorService.getAllSupervisors();
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitNotificationRequest(@RequestBody NotificationRequest request) {
        try {
            supervisorService.submitNotificationRequest(request);
            return ResponseEntity.ok("Success");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
