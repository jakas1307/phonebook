package com.pb.phonebook.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pb.phonebook.dto.UserDto;
import com.pb.phonebook.entity.Company;
import com.pb.phonebook.entity.Department;
import com.pb.phonebook.entity.Location;
import com.pb.phonebook.entity.User;
import com.pb.phonebook.repository.CompanyRepository;
import com.pb.phonebook.repository.DepartmentRepository;
import com.pb.phonebook.repository.LocationRepository;
import com.pb.phonebook.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final LocationRepository locationRepository;
    
    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .extension(user.getExtension())
                .email(user.getEmail())
                .companyId(user.getCompany() != null ? user.getCompany().getId() : null)
                .companyName(user.getCompany() != null ? user.getCompany().getName() : null)
                .departmentId(user.getDepartment() != null ? user.getDepartment().getId() : null)
                .departmentName(user.getDepartment() != null ? user.getDepartment().getName() : null)
                .locationId(user.getLocation() != null ? user.getLocation().getId() : null)
                .locationName(user.getLocation() != null ? user.getLocation().getName() : null)
                .locationRegion(user.getLocation() != null? user.getLocation().getRegion() : null)
                .build();
    }

    private User mapToEntity(UserDto dto) {
        Company company = dto.getCompanyId() != null ?
                companyRepository.findById(dto.getCompanyId()).orElse(null) : null;

        Department department = dto.getDepartmentId() != null ?
                departmentRepository.findById(dto.getDepartmentId()).orElse(null) : null;

        Location location = dto.getLocationId() != null ?
                locationRepository.findById(dto.getLocationId()).orElse(null) : null;
        
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .extension(dto.getExtension())
                .email(dto.getEmail())
                .company(company)
                .department(department)
                .location(location)
                .build();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        return mapToDto(userRepository.save(mapToEntity(userDto)));
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setName(userDto.getName());
                    existing.setExtension(userDto.getExtension());
                    existing.setEmail(userDto.getEmail());

                    if (userDto.getCompanyId() != null) {
                        existing.setCompany(companyRepository.findById(userDto.getCompanyId()).orElse(null));
                    }

                    if (userDto.getDepartmentId() != null) {
                        existing.setDepartment(departmentRepository.findById(userDto.getDepartmentId()).orElse(null));
                    }

                    if (userDto.getLocationId() != null) {
                        existing.setLocation(locationRepository.findById(userDto.getLocationId()).orElse(null));
                    }

                    return mapToDto(userRepository.save(existing));
                })
                
                .orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
