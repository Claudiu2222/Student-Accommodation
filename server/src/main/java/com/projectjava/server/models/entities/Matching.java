package com.projectjava.server.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "matchings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "matched_student_id", referencedColumnName = "user_id")
    private Student matchedStudent;

    public Matching(Student student, Student matchedStudent) {
        this.student = student;
        this.matchedStudent = matchedStudent;
    }


}
