package com.digix.projectmanagement.DTO;

import com.digix.projectmanagement.entities.Workspace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspacesListDTO {
    private int workspaceId;
    private String name;
    private String description;
    // ... other fields you need

    // Constructor, getters, and setters
    public WorkspacesListDTO(Workspace workspace) {
        this.workspaceId = workspace.getWorkspaceId();
        this.name = workspace.getName();
        this.description = workspace.getDescription();
        // ... set other fields
    }
}