package com.example.demo.repository;

import com.example.demo.entity.PersonalDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;;

public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, UUID> {
	Optional<PersonalDetails> findByCustomerId(UUID customerId);

}
