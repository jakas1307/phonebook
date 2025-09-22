package com.pb.phonebook.service;

import java.util.List;

import com.pb.phonebook.dto.DepartmentDto;

public interface DepartmentService {
    
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartmentById(Long id);
    List <DepartmentDto> getAllDepartments();
    DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto);
    void deleteDepartment(Long id);
    
}
