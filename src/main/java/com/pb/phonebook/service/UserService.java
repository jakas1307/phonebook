package com.pb.phonebook.service;

import java.util.List;

import com.pb.phonebook.dto.UserDto;


public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    List<UserDto> searchUsers(String keyword);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}
