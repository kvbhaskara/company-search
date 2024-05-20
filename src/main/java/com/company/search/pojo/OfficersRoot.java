package com.company.search.pojo;

import com.company.search.model.Officer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OfficersRoot {

    private String kind;
    private int items_per_page;
    private int active_count;
    private int total_results;
    private int resigned_count;

    @JsonProperty("items")
    private List<Officer> items;

}
