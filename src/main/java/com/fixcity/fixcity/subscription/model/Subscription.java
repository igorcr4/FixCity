package com.fixcity.fixcity.subscription.model;

import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.subscription.enumeration.PlanType;
import com.fixcity.fixcity.subscription.enumeration.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType plan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @Column(nullable = false)
    private String stripeSubscriptionId;

    private LocalDateTime createdAt;
    private LocalDateTime currentPeriodStart;
    private LocalDateTime currentPeriodEnd;
    private LocalDateTime canceledAt;
    private LocalDateTime updatedAt;
    private boolean cancelAtPeriodEnd;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return status == SubscriptionStatus.ACTIVE || status == SubscriptionStatus.PAST_DUE;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id", unique = true)
    private Municipality municipality;
}
