package com.hrms.holiday.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hrms.company.entity.Company;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "holidays", uniqueConstraints = {
		@UniqueConstraint(name = "uk_company_holiday_date", columnNames = { "company_id", "holiday_date" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Holiday {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@Column(name = "holiday_name", nullable = false, length = 150)
	private String holidayName;

	@Column(name = "holiday_date", nullable = false)
	private LocalDate holidayDate;

	@Column(length = 500)
	private String description;

	@Column(name = "is_optional", nullable = false)
	@Builder.Default
	private Boolean optional = false;

	@Column(name = "is_active", nullable = false)
	@Builder.Default
	private Boolean active = true;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
