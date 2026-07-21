package com.hrms.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrms.auth.entity.User;
import com.hrms.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByUserOrderByCreatedAtDesc(User user);

	List<Notification> findByUserAndReadFalseOrderByCreatedAtDesc(User user);

	long countByUserAndReadFalse(User user);

}
