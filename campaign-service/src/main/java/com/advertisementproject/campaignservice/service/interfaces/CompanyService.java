package com.advertisementproject.campaignservice.service.interfaces;

import com.advertisementproject.campaignservice.db.entity.Company;

import java.util.UUID;

public interface CompanyService {

    Company getCompanyById(UUID companyId);
    void saveOrUpdateCompany(Company company);
    void deleteCompanyById(UUID companyId);
}
