package com.hrms.notification.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

	private Long id;

    private Long userId;

    private String title;

    private String message;

    private String notificationType;

    private Boolean read;

    private LocalDateTime createdAt;
	
}
