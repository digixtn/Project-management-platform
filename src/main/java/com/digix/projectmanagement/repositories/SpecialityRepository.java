package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {
}