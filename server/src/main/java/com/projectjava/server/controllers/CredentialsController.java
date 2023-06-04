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
    public Integer checkCredentials(@RequestBody Credentials credentials) {
        return credentialsService.checkCredentials(credentials);
    }

    @GetMapping(path = "/{username}")
    public void checkIfUsernameExist(@PathVariable String username) {
        credentialsService.checkIfUsernameExist(username);
    }

    @PostMapping(path = "/username={username}&oldpassword={oldpassword}&password={password}")
    public void changePassword(@PathVariable String username, @PathVariable String oldpassword, @PathVariable String password) {
        credentialsService.changePassword(username, oldpassword, password);
    }

    @GetMapping(path = "/getRole&username={username}")
    public String getRole(@PathVariable String username) {
        return credentialsService.getRole(username);
    }
}