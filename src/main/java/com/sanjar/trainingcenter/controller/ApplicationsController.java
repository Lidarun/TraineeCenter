package com.sanjar.trainingcenter.controller;

import com.sanjar.trainingcenter.model.Application;
import com.sanjar.trainingcenter.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationsController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<?> getApplication(@RequestBody Application application) {
        applicationService.create(application);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}
