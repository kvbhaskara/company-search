package com.company.search.controller;

import com.company.search.model.Company;
import com.company.search.pojo.SearchRequest;
import com.company.search.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Company Search API", description = "API for searching companies and their officers")
public class CompanySearchRest {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/search")
    @Operation(summary = "Search for a company", description = "Search for a company by name or registration number")
    public ResponseEntity<Company> searchCompany(@RequestBody SearchRequest searchRequest, @RequestHeader("x-api-key") String apiKey) {
        Company company = companyService.searchCompany(searchRequest);
        return ResponseEntity.ok(company);
    }
}
