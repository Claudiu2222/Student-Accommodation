package com.projectjava.server.controllers;

import com.projectjava.server.models.entities.Credentials;
import com.projectjava.server.services.CredentialsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login")
public class CredentialsController {

    private final CredentialsServiceImpl credentialsService;

    @Autowired
    public CredentialsController(CredentialsServiceImpl credentialsService) {
        this.credentialsService = credentialsService;
    }
    @GetMapping
    public void checkCredentials(@RequestBody Credentials credentials) {
        credentialsService.checkCredentials(credentials);
    }
}
