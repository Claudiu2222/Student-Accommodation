package com.projectjava.server.controllers;

import com.projectjava.server.models.entities.Credentials;
import com.projectjava.server.services.CredentialsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/login")
public class CredentialsController {

    private final CredentialsServiceImpl credentialsService;

    @Autowired
    public CredentialsController(CredentialsServiceImpl credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping
    public void checkCredentials(@RequestBody Credentials credentials) {
        credentialsService.checkCredentials(credentials);
    }

    @GetMapping(path = "/{username}")
    public void checkIfUsernameExist(@PathVariable String username) {
        credentialsService.checkIfUsernameExist(username);
    }
}