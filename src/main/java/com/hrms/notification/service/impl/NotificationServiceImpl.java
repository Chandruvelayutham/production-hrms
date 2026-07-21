package com.hrms.notification.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hrms.auth.entity.User;
import com.hrms.auth.repository.UserRepository;
import com.hrms.common.exception.AccessDeniedException;
import com.hrms.common.exception.ResourceNotFoundException;
import com.hrms.notification.dto.NotificationRequest;
import com.hrms.notification.dto.NotificationResponse;
import com.hrms.notification.entity.Notification;
import com.hrms.notification.repository.NotificationRepository;
import com.hrms.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;

	private final UserRepository userRepository;

	@Override
	public NotificationResponse createNotification(NotificationRequest request) {

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

		Notification notification = Notification.builder().user(user).title(request.getTitle())
				.message(request.getMessage()).notificationType(request.getNotificationType()).read(false).build();

		Notification savedNotification = notificationRepository.save(notification);

		return mapToResponse(savedNotification);
	}

	@Override
	@Transactional(readOnly = true)
	public List<NotificationResponse> getUserNotifications(Long userId) {

		User user = getUser(userId);

		return notificationRepository.findByUserOrderByCreatedAtDesc(user).stream().map(this::mapToResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<NotificationResponse> getUnreadNotifications(Long userId) {

		User user = getUser(userId);

		return notificationRepository.findByUserAndReadFalseOrderByCreatedAtDesc(user).stream().map(this::mapToResponse)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public long getUnreadNotificationCount(Long userId) {

		User user = getUser(userId);

		return notificationRepository.countByUserAndReadFalse(user);
	}

	@Override
	public void markAsRead(Long notificationId, Long userId) {

		Notification notification = getNotification(notificationId);

		validateNotificationOwner(notification, userId);

		notification.setRead(true);

		notificationRepository.save(notification);
	}

	@Override
	public void markAllAsRead(Long userId) {

		User user = getUser(userId);

		List<Notification> notifications = notificationRepository.findByUserAndReadFalseOrderByCreatedAtDesc(user);

		notifications.forEach(notification -> notification.setRead(true));

		notificationRepository.saveAll(notifications);
	}

	@Override
	public void deleteNotification(Long notificationId, Long userId) {

		Notification notification = getNotification(notificationId);

		validateNotificationOwner(notification, userId);

		notificationRepository.delete(notification);
	}

	private User getUser(Long userId) {

		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
	}

	private Notification getNotification(Long notificationId) {

		return notificationRepository.findById(notificationId)
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));
	}

	private void validateNotificationOwner(Notification notification, Long userId) {

		if (!notification.getUser().getId().equals(userId)) {

			throw new AccessDeniedException(
	                "You are not authorized to access this notification."
	        );
		}
	}

	private NotificationResponse mapToResponse(Notification notification) {

		return NotificationResponse.builder().id(notification.getId()).userId(notification.getUser().getId())
				.title(notification.getTitle()).message(notification.getMessage())
				.notificationType(notification.getNotificationType()).read(notification.getRead())
				.createdAt(notification.getCreatedAt()).build();
	}
}
