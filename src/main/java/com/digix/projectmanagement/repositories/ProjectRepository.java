package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}