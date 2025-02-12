package com.digix.projectmanagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.time.Duration;
import java.util.Date;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private int taskId;
    private String name;
    private String description;
    private String status;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    public enum Priority {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    private Priority priority;

    private Duration effortEstimate;
    private Duration timeTracked;

    @ManyToOne
    @JoinColumn(name = "current_stage_id")
    private WorkflowStage currentStageId;
}
