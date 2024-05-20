package com.company.search.service;

import com.company.search.exceptions.TruProxyNotFoundException;
import com.company.search.exceptions.TruProxyServiceException;
import com.company.search.model.Officer;
import com.company.search.pojo.CompanyRoot;
import com.company.search.pojo.OfficersRoot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruProxyService extends RuntimeException {

    @Value("${truproxy.api.url}")
    private String apiUrl;

    @Value("${truproxy.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public TruProxyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CompanyRoot getCompanyDetails(String query) {
        try {
            String url = apiUrl + "/Search?Query=" + query;
            System.out.println("url:" + url);
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<CompanyRoot> companyResponse = restTemplate.exchange(url, HttpMethod.GET, entity, CompanyRoot.class);

            return companyResponse.getBody();

        } catch (HttpClientErrorException.NotFound ex) {
            throw new TruProxyNotFoundException("No companies found for search term: " + query);
        } catch (RestClientException ex) {
            throw new TruProxyServiceException("Error calling TruProxy API", ex);
        }
    }

    public List<Officer> getCompanyOfficers(String companyNumber) {
        try {
            String url = apiUrl + "/Officers?CompanyNumber=" + companyNumber;
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<OfficersRoot> response = restTemplate.exchange(url, HttpMethod.GET, entity, OfficersRoot.class);
            List<Officer> officers = response.getBody().getItems()
                    .stream()
                    .filter(officer -> officer.getResigned_on() == null || officer.getResigned_on().isBlank())
                    .collect(Collectors.toList());

            return officers;
        } catch (HttpClientErrorException.NotFound ex) {
            throw new TruProxyNotFoundException("No officers found for company number: " + companyNumber);
        } catch (RestClientException ex) {
            throw new TruProxyServiceException("Error calling TruProxy API", ex);
        }
    }
}

