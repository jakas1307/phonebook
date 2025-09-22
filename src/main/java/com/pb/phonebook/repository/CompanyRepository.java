package com.pb.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pb.phonebook.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    
}
