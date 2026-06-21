package com.fixcity.fixcity.report.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fixcity.fixcity.comment.Comment;
import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.report.Category;
import com.fixcity.fixcity.report.Status;
import com.fixcity.fixcity.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reports")
@NoArgsConstructor
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String title;
    private String imageUrl;
    private String description;
    private String address;

    private double latitude;
    private double longitude;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreated() {
        createdAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id")
    private Municipality municipality;

    @OneToMany(mappedBy = "report", orphanRemoval = true)
    @JsonBackReference
    private List<Comment> comments = new ArrayList<>();

}
