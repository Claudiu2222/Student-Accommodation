package com.projectjava.server.repositories;

import com.projectjava.server.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {
}
