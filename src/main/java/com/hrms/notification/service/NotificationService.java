package com.hrms.notification.service;

import java.util.List;

import com.hrms.notification.dto.NotificationRequest;
import com.hrms.notification.dto.NotificationResponse;

public interface NotificationService {

	NotificationResponse createNotification(NotificationRequest request);

	List<NotificationResponse> getUserNotifications(Long userId);

	List<NotificationResponse> getUnreadNotifications(Long userId);

	long getUnreadNotificationCount(Long userId);

	void markAsRead(Long notificationId, Long userId);

	void markAllAsRead(Long userId);

	void deleteNotification(Long notificationId, Long userId);

}
