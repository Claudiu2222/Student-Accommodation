package com.projectjava.server.controllers;

import com.projectjava.server.models.entities.Preference;
import com.projectjava.server.models.entities.Student;
import com.projectjava.server.services.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/preferences")
public class PreferenceController {

    private final PreferenceService preferenceService;

    @Autowired
    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @PostMapping(path = "{studentId}/{roommatePreferenceId}/{rank}")
    public void createPreference(@PathVariable("studentId") Integer studentId, @PathVariable("roommatePreferenceId") Integer roommatePreferenceId, @PathVariable("rank") Integer rank) {
        preferenceService.createPreference(studentId, roommatePreferenceId, rank);
    }

    @GetMapping
    public List<Preference> getPreferences() {
        return preferenceService.getPreferences();
    }

    @GetMapping(path = "{studentId}")
    public Set<Student> getPreferencesOfStudent(@PathVariable("studentId") Integer studentId) {
        return preferenceService.getPreferencesOfStudent(studentId);
    }

    @DeleteMapping(path = "{studentId}")
    public void deletePreferencesOfStudent(@PathVariable("studentId") Integer studentId) {
        preferenceService.deletePreferencesOfStudent(studentId);
    }


}
