package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowRepository extends JpaRepository<Workflow, Integer> {
}