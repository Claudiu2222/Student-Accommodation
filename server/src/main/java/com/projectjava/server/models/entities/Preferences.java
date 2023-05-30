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
public class Preferences extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id") 
    private User student; // ??? should be student

    @ManyToOne
    @JoinColumn(name = "preference_id", referencedColumnName = "id")
    private User preference; // ??? should be student

    @Column(nullable = false)
    private Integer ranking;

}
