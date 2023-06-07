package com.projectjava.server.repositories;


import com.projectjava.server.models.entities.Preference;
import com.projectjava.server.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Integer> {

    @Modifying
    @Query(value = "DELETE FROM preferences WHERE student_id = :stud_id OR preference_id = :stud_id", nativeQuery = true)
    void deleteAllByStudentId(@Param("stud_id") Integer studentID);


    @Query(value = "SELECT p.preference FROM Preference p WHERE p.student.user_id = :stud_id ORDER BY p.ranking ASC")
    List<Student> findAllByStudentId(@Param("stud_id") Integer studentId);
}
