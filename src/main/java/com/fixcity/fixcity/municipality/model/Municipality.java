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
@Table(name = "municipality", uniqueConstraints = @UniqueConstraint(
        name = "uq_municipal_canonical",
        columnNames = {"country_iso2", "state_iso2", "name_key"}
))
@Getter
@Setter
@NoArgsConstructor
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String country;
    private String state;

    @Column(name = "country_iso2")
    private String countryIso2;

    @Column(name = "state_iso2")
    private String stateIso2;

    @Column(name = "name_key")
    private String nameKey;

    private String stripeCustomerId;

    @OneToMany(mappedBy = "municipality")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "municipality")
    private List<User> users = new ArrayList<>();
}
