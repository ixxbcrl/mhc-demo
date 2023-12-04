package com.example.mhcdemo.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company_type")
public class CompanyType {

    @Id
    @GeneratedValue
    private Long id;

    private String type;

    @OneToMany(mappedBy="companyType")
    private Set<Company> companies;
}
