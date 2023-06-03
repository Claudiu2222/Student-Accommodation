package com.projectjava.server.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preferences")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "preference_id", referencedColumnName = "user_id")
    private Student preference;

    @Column(nullable = false)
    private Integer ranking;

    public Preference(Student student, Student preference, Integer ranking) {
        this.student = student;
        this.preference = preference;
        this.ranking = ranking;
    }
}
