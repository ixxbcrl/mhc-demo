package com.example.mhcdemo.repository;

import com.example.mhcdemo.repository.entity.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {

    @Query("select e from CompanyType e where e.type = ?1")
    CompanyType findByCompanyType(String type);
}
