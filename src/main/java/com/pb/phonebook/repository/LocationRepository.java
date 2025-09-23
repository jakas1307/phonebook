package com.pb.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pb.phonebook.entity.Location;


public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findBySubregion(String subregion);
    
}
