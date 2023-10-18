package com.sanjar.trainingcenter.controller;

import com.sanjar.trainingcenter.model.ApplicationForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class ApplicationsController {

    @PostMapping
    public ResponseEntity<?> getApplication(@RequestBody ApplicationForm application) {
        System.out.println(application);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}
