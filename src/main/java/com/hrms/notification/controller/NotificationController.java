package com.hrms.notification.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hrms.common.response.ApiResponse;
import com.hrms.notification.dto.NotificationRequest;
import com.hrms.notification.dto.NotificationResponse;
import com.hrms.notification.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

	
	private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>>
            createNotification(
                    @Valid @RequestBody
                    NotificationRequest request) {

        NotificationResponse response =
                notificationService
                        .createNotification(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Notification created successfully",
                                response));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>>
            getUserNotifications(
                    @PathVariable Long userId) {

        List<NotificationResponse> response =
                notificationService
                        .getUserNotifications(userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notifications retrieved successfully",
                        response));
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>>
            getUnreadNotifications(
                    @PathVariable Long userId) {

        List<NotificationResponse> response =
                notificationService
                        .getUnreadNotifications(userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Unread notifications retrieved successfully",
                        response));
    }

    @GetMapping("/user/{userId}/unread/count")
    public ResponseEntity<ApiResponse<Long>>
            getUnreadNotificationCount(
                    @PathVariable Long userId) {

        long count =
                notificationService
                        .getUnreadNotificationCount(userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Unread notification count retrieved successfully",
                        count));
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Void>>
            markAsRead(
                    @PathVariable Long notificationId,
                    @RequestParam Long userId) {

        notificationService.markAsRead(
                notificationId,
                userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification marked as read",
                        null));
    }

    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<ApiResponse<Void>>
            markAllAsRead(
                    @PathVariable Long userId) {

        notificationService.markAllAsRead(userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "All notifications marked as read",
                        null));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Void>>
            deleteNotification(
                    @PathVariable Long notificationId,
                    @RequestParam Long userId) {

        notificationService.deleteNotification(
                notificationId,
                userId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification deleted successfully",
                        null));
    }
	
}
