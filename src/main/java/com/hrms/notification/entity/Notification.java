package com.hrms.notification.entity;

import java.time.LocalDateTime;

import com.hrms.auth.entity.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(
            nullable = false,
            length = 200
    )
    private String title;

    @Column(
            nullable = false,
            length = 1000
    )
    private String message;

    @Column(
            name = "notification_type",
            nullable = false,
            length = 50
    )
    private String notificationType;

    @Column(
            name = "is_read",
            nullable = false
    )
    @Builder.Default
    private Boolean read = false;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
	
}
