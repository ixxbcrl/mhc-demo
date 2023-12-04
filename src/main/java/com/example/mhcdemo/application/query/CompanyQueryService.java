package com.example.mhcdemo.application.query;

import com.example.mhcdemo.repository.CompanyRepository;
import com.example.mhcdemo.repository.CompanyTypeRepository;
import com.example.mhcdemo.repository.entity.Company;
import com.example.mhcdemo.repository.entity.CompanyType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyQueryService {

    CompanyRepository companyRepository;
    CompanyTypeRepository companyTypeRepository;

    public CompanyQueryService(CompanyRepository companyRepository, CompanyTypeRepository companyTypeRepository) {
        this.companyRepository = companyRepository;
        this.companyTypeRepository = companyTypeRepository;
    }

    public Company getCompanyByName(String companyName) {
        return companyRepository.findByCompanyName(companyName).orElseThrow(() ->
                new IllegalArgumentException(String.format("Company with name %s not found", companyName)));
    }

    public List<Company> getCompanyByType(String companyType) {
        List<Company> companyTypes = companyRepository.findByTypeCompanyType(companyType);

        return companyTypes;
    }
}
