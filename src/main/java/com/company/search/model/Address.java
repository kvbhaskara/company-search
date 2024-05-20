package com.company.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
class Address {

    @JsonProperty("locality")
    private String locality;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("premises")
    private String premises;

    @JsonProperty("address_line_1")
    private String addressLine1;

    @JsonProperty("country")
    private String country;

}
