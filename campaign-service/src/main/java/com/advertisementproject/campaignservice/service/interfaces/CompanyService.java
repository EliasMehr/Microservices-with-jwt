package com.advertisementproject.campaignservice.service.interfaces;

import com.advertisementproject.campaignservice.db.entity.Company;

import java.util.UUID;

/**
 * Service for managing companies in the database with CRUD operations.
 */
public interface CompanyService {

    /**
     * Retrieves a specific company
     *
     * @param companyId the id of the company to retrieve
     * @return the company with the supplied company id
     */
    Company getCompanyById(UUID companyId);

    /**
     * Saves or updates the supplied company in the database
     *
     * @param company the company to save/update
     */
    void saveOrUpdateCompany(Company company);

    /**
     * Deletes a specific company
     *
     * @param companyId the id of the company to delete
     */
    void deleteCompanyById(UUID companyId);
}
