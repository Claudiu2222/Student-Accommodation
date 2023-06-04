package com.projectjava.server.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "id")
    private Integer id;

    @Column(nullable = false, unique = true, length = 50, name = "username")
    private String username;

    @Column(nullable = false, length = 255, name = "password")
    private String password;

    @Column(nullable = false, name = "role")
    private Integer role;

    @Column(nullable = false, name = "firsttime")
    private Boolean firstTime;
}
