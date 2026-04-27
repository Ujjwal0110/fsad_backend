package com.sams.repository;

import com.sams.entity.Registration;
import com.sams.entity.User;
import com.sams.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByStudent(User student);

    List<Registration> findByActivity(Activity activity);

    Optional<Registration> findByStudentAndActivity(User student, Activity activity);

    boolean existsByStudentAndActivity(User student, Activity activity);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.activity = :activity")
    long countByActivity(@Param("activity") Activity activity);

    @Query("SELECT COUNT(DISTINCT r.student) FROM Registration r")
    long countDistinctStudents();
}
