package com.company.search.pojo;

import com.company.search.model.Company;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyRoot {
    @JsonProperty("total_results")
    public int total_results;

    @JsonProperty("items")
    public List<Company> items;

}

