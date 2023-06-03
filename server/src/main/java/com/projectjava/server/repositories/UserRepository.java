package com.projectjava.server.repositories;

import com.projectjava.server.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository  extends JpaRepository<User, Integer> {
  @Query(value = "SELECT * FROM users  WHERE username = :username", nativeQuery = true)
  public User findUserByUsername(String username);
}
