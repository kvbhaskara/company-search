package com.company.search.service;

import com.company.search.exceptions.ResourceNotFoundException;
import com.company.search.model.Company;
import com.company.search.model.Officer;
import com.company.search.pojo.CompanyRoot;
import com.company.search.pojo.SearchRequest;
import com.company.search.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TruProxyService truProxyService;

    public Company searchCompany(SearchRequest searchRequest) {
        Optional<Company> companyOptional = null;
        if (searchRequest.getCompanyNumber() != null && !searchRequest.getCompanyNumber().isEmpty()) {
            companyOptional = getCompany(searchRequest);
            if (companyOptional.isPresent()) {
                return companyOptional.get();
            }
        }

        try {
            String query = searchRequest.getCompanyName();
            CompanyRoot companyRoot = truProxyService.getCompanyDetails(query);
            List<Company> companies = companyRoot.getItems()
                    .stream()
                    .filter(company -> company.getCompanyStatus().equalsIgnoreCase("active"))
                    .collect(Collectors.toList());

            for (Company company : companies) {
                if (company != null) {
                    List<Officer> officers = truProxyService.getCompanyOfficers(company.getCompanyNumber());
                    officers.forEach(officer -> officer.setCompany(company));  // Set the company reference in officers
                    company.setOfficers(officers);
                    companyRepository.save(company);
                }
            }
            companyOptional = getCompany(searchRequest);
            return companyOptional.get();
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while searching for the company.", e);
        }
    }

    private Optional<Company> getCompany(SearchRequest searchRequest) {
        Optional<Company> companyOptional;
        companyOptional = companyRepository.findByCompanyNumber(searchRequest.getCompanyNumber());
        return companyOptional;
    }
}