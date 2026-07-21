package com.hrms.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

	@Positive(message = "User ID must be positive")
	@NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Title is required")
    @Size(
            max = 200,
            message = "Title cannot exceed 200 characters"
    )
    private String title;

    @NotBlank(message = "Message is required")
    @Size(
            max = 1000,
            message = "Message cannot exceed 1000 characters"
    )
    private String message;

    @NotBlank(message = "Notification type is required")
    @Size(
            max = 50,
            message = "Notification type cannot exceed 50 characters"
    )
    private String notificationType;
	
}
