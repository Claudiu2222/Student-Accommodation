package com.projectjava.server.repositories;

import com.projectjava.server.models.entities.Matching;
import com.projectjava.server.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoommateMatchingRepository extends JpaRepository<Matching, Integer> {
    @Query("SELECT m.matchedStudent FROM Matching m WHERE m.student = :student")
    Student getRoommateMatchingOfStudent(@Param("student") Student student);


    @Query(value = "SELECT COUNT(*) FROM matchings", nativeQuery = true)
    Integer getCount();

    @Modifying
    @Query(value = "DELETE FROM matchings WHERE student_id = :stud_id OR matched_student_id = :stud_id", nativeQuery = true)
    void deleteAllByStudentId(@Param("stud_id") Integer studentID);
}
