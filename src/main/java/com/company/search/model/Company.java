package com.company.search.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.boot.model.relational.QualifiedTableName;

import java.util.List;


@Entity

@Data
public class Company {


//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @JsonProperty("company_number")
    private String companyNumber;

    @JsonProperty("company_type")
    private String companyType;

    @JsonProperty("title")
    private String title;

    @JsonProperty("company_status")
    private String companyStatus;

    @JsonProperty("date_of_creation")
    private String dateOfCreation;

    @Embedded
    @JsonProperty("address")
    private Address address;


    @JsonProperty("officers")
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Officer> officers;

}


