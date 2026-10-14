package com.fixcity.fixcity.confirmationcode;

import com.fixcity.fixcity.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.mapstruct.ObjectFactory;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "confirmation_codes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConfirmationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codeHash;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Setter
    private boolean used;

    @Setter
    private int failedAttempts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private CodePurpose codePurpose;

    public ConfirmationCode(String codeHash, LocalDateTime sentAt, LocalDateTime expiresAt, User user, CodePurpose codePurpose) {
        this.codeHash = codeHash;
        this.sentAt = sentAt;
        this.expiresAt = expiresAt;
        this.user = user;
        this.codePurpose = codePurpose;
    }
}
