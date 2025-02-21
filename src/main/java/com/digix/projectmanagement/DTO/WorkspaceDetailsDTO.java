package com.digix.projectmanagement.DTO;
import com.digix.projectmanagement.entities.User;
import com.digix.projectmanagement.entities.Workspace;
import com.digix.projectmanagement.entities.WorkspaceUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class WorkspaceDetailsDTO {
    private int workspaceId;
    private String name;
    private String description;
    private User createdBy;
    // ... other workspace details

    private Set<Integer> memberUserIds; // Store user IDs instead of WorkspaceUser objects

    // ... getters and setters

    public WorkspaceDetailsDTO(Workspace workspace) {
        this.workspaceId = workspace.getWorkspaceId();
        this.name = workspace.getName();
        this.description = workspace.getDescription();
        this.createdBy = workspace.getCreatedBy();
        // ... set other workspace details
//
//        this.memberUserIds = workspace.getWorkspaceUsers().stream()
//                .map(wu -> wu.getUser().getUserId())
//                .collect(Collectors.toSet());
    }
}