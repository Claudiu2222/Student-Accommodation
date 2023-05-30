package com.projectjava.server.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50, name= "username")
    private String username;

    @Column(nullable = false, length = 255, name= "password")
    private String password;

    @Column(nullable = false, name= "role")
    private Integer role;
}
