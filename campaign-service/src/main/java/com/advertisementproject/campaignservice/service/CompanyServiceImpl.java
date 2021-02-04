package com.advertisementproject.campaignservice.service;

import com.advertisementproject.campaignservice.db.entity.Company;
import com.advertisementproject.campaignservice.db.repository.CompanyRepository;
import com.advertisementproject.campaignservice.exception.EntityNotFoundException;
import com.advertisementproject.campaignservice.service.interfaces.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company getCompanyById(UUID companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(()-> new EntityNotFoundException("Company not found for companyId: " + companyId));
    }

    @Override
    public void saveOrUpdateCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void deleteCompanyById(UUID companyId) {
        companyRepository.deleteById(companyId);
    }
}
