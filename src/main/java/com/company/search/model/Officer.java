package com.company.search.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String officerRole;

    private String appointedOn;

    private String occupation;
    private String country_of_residence;
    private String nationality;
    private String resigned_on;

    @Embedded
    private Address address;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_number")
    private Company company;

}

