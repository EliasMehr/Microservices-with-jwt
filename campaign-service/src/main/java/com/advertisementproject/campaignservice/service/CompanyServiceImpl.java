package com.advertisementproject.campaignservice.service;

import com.advertisementproject.campaignservice.db.entity.Company;
import com.advertisementproject.campaignservice.db.repository.CompanyRepository;
import com.advertisementproject.campaignservice.exception.EntityNotFoundException;
import com.advertisementproject.campaignservice.service.interfaces.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service implementation for Company Service, managing companies in the database with CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    /**
     * JPA Repository for Companies.
     */
    private final CompanyRepository companyRepository;

    /**
     * Retrieves a specific company
     *
     * @param companyId the id of the company to retrieve
     * @return the requested company
     * @throws EntityNotFoundException if the company is not found
     */
    @Override
    public Company getCompanyById(UUID companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found for companyId: " + companyId));
    }

    /**
     * Saves or updates a company in the database
     *
     * @param company the company to save/update
     */
    @Override
    public void saveOrUpdateCompany(Company company) {
        companyRepository.save(company);
    }

    /**
     * Deletes a company from the database
     *
     * @param companyId the id of the company to delete
     */
    @Override
    public void deleteCompanyById(UUID companyId) {
        companyRepository.deleteById(companyId);
    }
}
