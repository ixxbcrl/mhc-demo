package com.example.mhcdemo.repository;

import com.example.mhcdemo.repository.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyName(String name);

    @Query("select c from Company c inner join c.companyType e where e.type = ?1")
    List<Company> findByTypeCompanyType(String companyType);
}
