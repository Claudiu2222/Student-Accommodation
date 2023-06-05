package com.projectjava.server.repositories;

import com.projectjava.server.models.entities.Matching;
import com.projectjava.server.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoommateMatchingRepository extends JpaRepository<Matching, Integer> {
    @Query("SELECT m.matchedStudent FROM Matching m WHERE m.student = :student")
    Student getRoommateMatchingOfStudent(@Param("student") Student student);
}
