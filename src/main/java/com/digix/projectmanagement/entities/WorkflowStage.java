package com.digix.projectmanagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "workflow_stages")
public class WorkflowStage {

    @Id
    @GeneratedValue
    private int workflowStageId;
    private String name;
    private int stageOrder;
}
