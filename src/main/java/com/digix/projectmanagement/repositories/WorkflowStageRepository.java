package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.WorkflowStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowStageRepository extends JpaRepository<WorkflowStage, Integer> {
}