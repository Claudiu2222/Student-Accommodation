package com.projectjava.server.controllers;

import com.projectjava.server.models.entities.Credentials;
import com.projectjava.server.services.CredentialsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Integer> checkCredentials(@RequestBody Credentials credentials) {
        return ResponseEntity.ok(credentialsService.checkCredentials(credentials));
    }

    @GetMapping(path = "/{username}")
    public void checkIfUsernameExist(@PathVariable String username) {
        credentialsService.checkIfUsernameExist(username);
    }

    @PostMapping(path = "/username={username}&oldpassword={oldpassword}&password={password}")
    public ResponseEntity<Integer> changePassword(@PathVariable String username, @PathVariable String oldpassword, @PathVariable String password) {
        return ResponseEntity.ok(credentialsService.changePassword(username, oldpassword, password));
    }

    @GetMapping(path = "/getRole&username={username}")
    public String getRole(@PathVariable String username) {
        return credentialsService.getRole(username);
    }
}