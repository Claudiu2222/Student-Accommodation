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
public class Preferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name= "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private Student student; // ??? should be student

    @ManyToOne
    @JoinColumn(name = "preference_id", referencedColumnName = "user_id")
    private Student preference; // ??? should be student

    @Column(nullable = false)
    private Integer ranking;
}
