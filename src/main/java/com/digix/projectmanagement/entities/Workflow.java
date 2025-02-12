package com.digix.projectmanagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "workflows")
public class Workflow {

    @Id
    @GeneratedValue
    private int workflowId;
    private String name;
    private String description;
}
