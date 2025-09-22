package com.pb.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pb.phonebook.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u " +
           "LEFT JOIN u.company c " +
           "LEFT JOIN u.department d " +
           "LEFT JOIN u.location l " +
           "WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR CAST(u.extension AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(l.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> searchUsers(@Param("keyword") String keyword);
}
