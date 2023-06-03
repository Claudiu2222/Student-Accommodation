package com.projectjava.server.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    private Integer user_id;

    @Column(nullable = false, unique = true)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, name = "yearin")
    private Integer yearIn;

    @Column(nullable = false, name = "groupin")
    private String groupIn;

    public Student(String firstName, String lastName, Integer yearIn, String groupIn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearIn = yearIn;
        this.groupIn = groupIn;
    }
}