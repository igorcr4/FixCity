package com.fixcity.fixcity.municipality.model;

import com.fixcity.fixcity.report.model.Report;
import com.fixcity.fixcity.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "municipality")
@Getter
@Setter
@NoArgsConstructor
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private String country;
    private String state;

    private String stripeCustomerId;

    @OneToMany(mappedBy = "municipality")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "municipality")
    private List<User> users = new ArrayList<>();
}
