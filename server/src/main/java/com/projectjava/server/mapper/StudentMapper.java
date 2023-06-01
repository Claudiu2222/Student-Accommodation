package com.projectjava.server.mapper;

import com.projectjava.server.models.dtos.StudentDTO;
import com.projectjava.server.models.dtos.UserStudentDTO;
import com.projectjava.server.models.entities.Student;
import com.projectjava.server.models.entities.User;

public abstract class StudentMapper {
    public static Student toStudent(UserStudentDTO userStudentDTO) {
        return Student.builder()
                .lastName(userStudentDTO.getLastName())
                .firstName(userStudentDTO.getFirstName())
                .yearIn(userStudentDTO.getYearIn())
                .groupIn(userStudentDTO.getGroupIn())
                .user_id(userStudentDTO.getId())
                .build();
    }

    public static User toUser(UserStudentDTO userStudentDTO) {
        return User.builder()
                .username(userStudentDTO.getUsername())
                .password(userStudentDTO.getPassword())
                .role(0) // student
                .build();
    }

    public static StudentDTO toStudentDTO(Student student) {
        return StudentDTO.builder().user_id(student.getUser_id())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .groupIn(student.getGroupIn())
                .yearIn(student.getYearIn())
                .build();
    }
}
