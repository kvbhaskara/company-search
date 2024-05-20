package com.company.search;


import com.company.search.exceptions.ResourceNotFoundException;
import com.company.search.model.Company;
import com.company.search.model.Officer;
import com.company.search.pojo.SearchRequest;
import com.company.search.repository.CompanyRepository;
import com.company.search.service.CompanyService;
import com.company.search.service.TruProxyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private TruProxyService truProxyService;

    private Company testCompany;

    @BeforeEach
    public void setUp() {
        // Create a test company
        testCompany = new Company();
        testCompany.setCompanyNumber("06500244");
        testCompany.setTitle("BBC LIMITED");
        testCompany.setCompanyStatus("active");

        // Mock repository to return empty for any company number
        Mockito.when(companyRepository.findByCompanyNumber(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        // Mock repository to return the test company for a specific company number
        Mockito.when(companyRepository.findByCompanyNumber("06500244"))
                .thenReturn(Optional.of(testCompany));

        // Mock TruProxyService to return officers
        Officer testOfficer = new Officer();
        testOfficer.setName("Sarah Victoria BOXALL");
        testOfficer.setOccupation("Hr Manager");
        Mockito.when(truProxyService.getCompanyOfficers("06500244"))
                .thenReturn(List.of(testOfficer));
    }

    @Test
    public void testSearchCompany_ByCompanyNumber_Found() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setCompanyNumber("06500244");
        Company result = companyService.searchCompany(searchRequest);
        assertNotNull(result);
        assertEquals("06500244", result.getCompanyNumber());
        assertEquals("BBC LIMITED", result.getTitle());
        assertEquals("active", result.getCompanyStatus());
    }

    @Test
    public void testSearchCompany_ByCompanyNumber_NotFound() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setCompanyNumber("NonExistentNumber");
        Optional<Company> companyOptional = companyRepository.findByCompanyNumber(searchRequest.getCompanyNumber());
        assertFalse(companyOptional.isPresent());
    }


    @Test
    public void testSearchCompany_ByName_NotFound() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setCompanyName("NonExistentCompany");
        assertThrows(ResourceNotFoundException.class, () -> {
            companyService.searchCompany(searchRequest);
        });
    }


    @Test
    public void testSearchCompany_WithEmptyCompanyNameAndNumber() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setCompanyName("");
        searchRequest.setCompanyNumber("");
        assertThrows(ResourceNotFoundException.class, () -> {
            companyService.searchCompany(searchRequest);
        });
    }


        @Test
        public void testSearchCompany_WithNullCompanyNameAndNumber() {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setCompanyName(null);
            searchRequest.setCompanyNumber(null);
            assertThrows(ResourceNotFoundException.class, () -> {
                companyService.searchCompany(searchRequest);
            });
        }

}
