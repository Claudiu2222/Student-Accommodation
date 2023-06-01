package com.projectjava.server.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDTO {
    private Integer user_id;
    private String firstName;
    private String lastName;
    private String groupIn;
    private Integer yearIn;
}
