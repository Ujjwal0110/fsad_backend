package com.sams.repository;

import com.sams.entity.Activity;
import com.sams.entity.Activity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByCategory(Category category);

    List<Activity> findByNameContainingIgnoreCase(String name);

    List<Activity> findByCategoryAndNameContainingIgnoreCase(Category category, String name);

    @Query("SELECT a FROM Activity a " +
           "LEFT JOIN a.registrations r " +
           "GROUP BY a.id " +
           "ORDER BY COUNT(r) DESC")
    List<Activity> findAllOrderByRegistrationCountDesc();
}
