package com.fixcity.fixcity.municipalityrequest.model;

import com.fixcity.fixcity.municipalityrequest.status.RequestStatus;
import com.fixcity.fixcity.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "municipality_request")
@NoArgsConstructor
@Getter
@Setter
public class MunicipalityRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String institutionName;
    private String employeePosition;
    private String justification;
    private LocalDateTime createdAt;
    private LocalDateTime reviewedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
