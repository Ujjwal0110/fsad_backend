package com.sams.repository;

import com.sams.entity.Notification;
import com.sams.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.targetStudent = :student OR n.targetStudent IS NULL ORDER BY n.createdAt DESC")
    List<Notification> findForStudent(@Param("student") User student);

    @Query("SELECT COUNT(n) FROM Notification n WHERE (n.targetStudent = :student OR n.targetStudent IS NULL) AND n.isRead = false")
    long countUnreadForStudent(@Param("student") User student);

    List<Notification> findAllByOrderByCreatedAtDesc();
}
