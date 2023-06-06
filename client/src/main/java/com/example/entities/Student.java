package com.example.entities;

import lombok.Builder;
import lombok.Data;

@Data
public class Student {
    private Integer user_id;

    private String firstName;

    private String lastName;

    private Integer yearIn;

    private String groupIn;


    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
