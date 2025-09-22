package com.pb.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pb.phonebook.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
}
