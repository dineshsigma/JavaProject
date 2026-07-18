package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Collegue;

@Repository
public interface CollegueRepository extends JpaRepository<Collegue, Long> {

	boolean existsByEmail(String email);

	boolean existsByMobileNumber(String mobileNumber);

}
