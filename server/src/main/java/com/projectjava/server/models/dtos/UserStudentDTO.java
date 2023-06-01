package com.projectjava.server.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStudentDTO {
    private Integer     id;
    private String      username;
    private String      password;
    private String      firstName;
    private String      lastName;
    private String      groupIn;
    private Integer     yearIn;
}
