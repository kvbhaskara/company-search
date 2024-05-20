package com.company.search.repository;

import com.company.search.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    Optional<Company> findByCompanyNumber(String companyNumber);
}
