package com.pb.phonebook.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pb.phonebook.dto.DepartmentDto;
import com.pb.phonebook.entity.Department;
import com.pb.phonebook.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    
    private final DepartmentRepository departmentRepository;

    private DepartmentDto mapToDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

    private Department mapToEntity(DepartmentDto dto) {
        return Department.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        Department department = mapToEntity(departmentDto);
        return mapToDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        return departmentRepository.findById(id)
                .map(existing -> {
                    existing.setName(departmentDto.getName());
                    return mapToDto(departmentRepository.save(existing));
                }).orElse(null);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
