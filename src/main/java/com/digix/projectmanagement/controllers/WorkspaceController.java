package com.digix.projectmanagement.controllers;

import com.digix.projectmanagement.DTO.WorkspaceDetailsDTO;
import com.digix.projectmanagement.DTO.WorkspacesListDTO;
import com.digix.projectmanagement.entities.User;
import com.digix.projectmanagement.entities.UserType;
import com.digix.projectmanagement.entities.Workspace;
import com.digix.projectmanagement.services.WorkspaceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/workspaces")
public class WorkspaceController {

    private final WorkspaceManagementService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceManagementService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(
            @RequestBody Workspace workspace,
            @AuthenticationPrincipal User currentUser) {
        Workspace created = workspaceService.createWorkspace(workspace, currentUser);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkspacesListDTO>> getWorkspacesForCurrentUser(
            @AuthenticationPrincipal User currentUser) {
        List<Workspace> workspaces = workspaceService.getWorkspacesForUser(currentUser.getUserId());
        List<WorkspacesListDTO> workspacesListDTOS = workspaces.stream()
                .map(WorkspacesListDTO::new)  // Convert to DTOs
                .collect(Collectors.toList());
        return ResponseEntity.ok(workspacesListDTOS);
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceDetailsDTO> getWorkspace(
            @PathVariable int workspaceId,
            @AuthenticationPrincipal User currentUser) {

        WorkspaceDetailsDTO workspaceDTO = workspaceService.getWorkspace(workspaceId);

        // Authorization Check (using data from the DTO)
        if (workspaceDTO == null || (!workspaceDTO.getCreatedBy().equals(currentUser) && !workspaceDTO.getMemberUserIds().contains(currentUser.getUserId()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(workspaceDTO);
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<Workspace> updateWorkspace(
            @PathVariable int workspaceId,
            @RequestBody Workspace workspace,
            @AuthenticationPrincipal User currentUser) {
        Workspace updated = workspaceService.updateWorkspace(workspaceId, workspace, currentUser);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Void> deleteWorkspace(
            @PathVariable int workspaceId,
            @AuthenticationPrincipal User currentUser) {
        workspaceService.deleteWorkspace(workspaceId, currentUser);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/{workspaceId}/users/{userId}")
//    public ResponseEntity<Void> addUserToWorkspace(
//            @PathVariable int workspaceId,
//            @PathVariable int userId,
//            @AuthenticationPrincipal User currentUser) {
//        // First, check if current user is admin or creator of the workspace
//        Workspace workspace = workspaceService.getWorkspace(workspaceId);
//        if (workspace.getCreatedBy().getUserId() != currentUser.getUserId() &&
//                currentUser.getRole() != UserType.ROLE_WORKSPACE_OWNER) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        workspaceService.addUserToWorkspace(workspaceId, userId);
//        return ResponseEntity.ok().build();
//    }

    @DeleteMapping("/{workspaceId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromWorkspace(
            @PathVariable int workspaceId,
            @PathVariable int userId,
            @AuthenticationPrincipal User currentUser) {
        workspaceService.removeUserFromWorkspace(workspaceId, userId, currentUser);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/{workspaceId}/users")
//    public ResponseEntity<List<User>> getUsersInWorkspace(
//            @PathVariable int workspaceId,
//            @AuthenticationPrincipal User currentUser) {
//        // First, check if current user is a member of the workspace
//        Workspace workspace = workspaceService.getWorkspace(workspaceId);
//        boolean isMember = workspace.getWorkspaceUsers().stream()
//                .anyMatch(wu -> wu.getUser().getUserId() == currentUser.getUserId());
//
//        if (!isMember && workspace.getCreatedBy().getUserId() != currentUser.getUserId()) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        List<User> users = workspaceService.getUsersInWorkspace(workspaceId);
//        return ResponseEntity.ok(users);
//    }
}