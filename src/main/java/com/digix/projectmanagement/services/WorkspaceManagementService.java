package com.digix.projectmanagement.services;

import com.digix.projectmanagement.DTO.WorkspaceDetailsDTO;
import com.digix.projectmanagement.entities.User;
import com.digix.projectmanagement.entities.UserType;
import com.digix.projectmanagement.entities.Workspace;
import com.digix.projectmanagement.entities.WorkspaceUser;
import com.digix.projectmanagement.entities.WorkspaceUser.WorkspaceUserId;
import com.digix.projectmanagement.repositories.WorkspaceRepository;
import com.digix.projectmanagement.repositories.WorkspaceUserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkspaceManagementService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceUserRepository workspaceUserRepository;

    @Autowired
    public WorkspaceManagementService(
            WorkspaceRepository workspaceRepository,
            WorkspaceUserRepository workspaceUserRepository) {
        this.workspaceRepository = workspaceRepository;
        this.workspaceUserRepository = workspaceUserRepository;
    }

    @Transactional
    public Workspace createWorkspace(Workspace workspace, User currentUser) {
        // Check if user has permission (creator or admin)
        if (currentUser.getRole() != UserType.ROLE_WORKSPACE_OWNER) {
            throw new IllegalStateException("User does not have permission to delete this workspace");
        }

        // Set creation date if not already set
        if (workspace.getCreatedDate() == null) {
            workspace.setCreatedDate(LocalDate.now());
        }

        // Set the creator of the workspace
        workspace.setCreatedBy(currentUser);

        // Save the workspace first to get the generated ID
        Workspace savedWorkspace = workspaceRepository.save(workspace);

        // Create the workspace-user association
        WorkspaceUser workspaceUser = new WorkspaceUser();
        WorkspaceUser.WorkspaceUserId compositeId = new WorkspaceUser.WorkspaceUserId(
                savedWorkspace.getWorkspaceId(),
                currentUser.getUserId()
        );

        workspaceUser.setId(compositeId);
        workspaceUser.setWorkspace(savedWorkspace);
        workspaceUser.setUser(currentUser);
        workspaceUser.setJoinedDate(LocalDate.now());

        workspaceUserRepository.save(workspaceUser);

        return savedWorkspace;
    }

    @Transactional
    public void deleteWorkspace(int workspaceId, User requestingUser) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new EntityNotFoundException("Workspace not found"));

        // Check if user has permission (creator or admin)
        if (!(workspace.getCreatedBy().getUserId()==(requestingUser.getUserId())) &&
                !UserType.ROLE_WORKSPACE_OWNER.equals(requestingUser.getRole())) {
            throw new IllegalStateException("User does not have permission to delete this workspace");
        }

        // Delete all workspace user associations first
        workspaceUserRepository.deleteByWorkspace_WorkspaceId(workspaceId);

        // Delete the workspace
        workspaceRepository.delete(workspace);
    }

    @Transactional
    public Workspace updateWorkspace(int workspaceId, Workspace workspaceDetails, User requestingUser) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new EntityNotFoundException("Workspace not found"));

        // Check if user has permission (creator or admin)
        if (workspace.getCreatedBy().getUserId() != requestingUser.getUserId() &&
                requestingUser.getRole() != UserType.ROLE_WORKSPACE_OWNER) {
            throw new IllegalStateException("User does not have permission to modify this workspace");
        }

        // Update properties
        workspace.setName(workspaceDetails.getName());
        workspace.setDescription(workspaceDetails.getDescription());

        return workspaceRepository.save(workspace);
    }

    @Transactional
    public void addUserToWorkspace(Workspace workspace, User user) { // Take Workspace and User entities

        // 1. Check if the association ALREADY exists (This is crucial!)
        Optional<WorkspaceUser> existingAssociation = workspaceUserRepository.findByWorkspaceAndUser(workspace, user);

        if (existingAssociation.isPresent()) {
            return; // Or throw an exception if you want to prevent duplicates
        }

        // 2. Create the association (DO NOT set the ID manually!)
        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setWorkspace(workspace);
        workspaceUser.setUser(user);
        workspaceUser.setJoinedDate(LocalDate.now());

        // IMPORTANT: Let Hibernate handle ID generation.  Do not set it manually.
        workspaceUserRepository.save(workspaceUser); // Persist the WorkspaceUser. The ID is generated by DB.
        // No need to add it to the workspace's collection manually if you have cascade persist set up correctly.
    }

    @Transactional
    public void removeUserFromWorkspace(int workspaceId, int userId, User requestingUser) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new EntityNotFoundException("Workspace not found"));

        // Check if requesting user has permission
        if (workspace.getCreatedBy().getUserId() != requestingUser.getUserId() &&
                requestingUser.getRole() != UserType.ROLE_WORKSPACE_OWNER) {
            throw new IllegalStateException("User does not have permission to remove users from this workspace");
        }

        // Cannot remove the workspace creator
        if (workspace.getCreatedBy().getUserId() == userId) {
            throw new IllegalStateException("Cannot remove the workspace creator");
        }

        WorkspaceUserId id = new WorkspaceUserId(workspaceId, userId);
        if (!workspaceUserRepository.existsById(id)) {
            throw new EntityNotFoundException("User is not a member of this workspace");
        }

        workspaceUserRepository.deleteById(id);
    }

    public List<Workspace> getWorkspacesForUser(int userId) {
        return workspaceRepository.findWorkspacesForUser(userId);
    }

    public List<User> getUsersInWorkspace(int workspaceId) {
        return workspaceUserRepository.findByWorkspaceWorkspaceId(workspaceId)
                .stream()
                .map(WorkspaceUser::getUser)
                .collect(Collectors.toList());
    }


    public WorkspaceDetailsDTO getWorkspace(int workspaceId) {
        log.info("Entering getWorkspace() for workspaceId: {}", workspaceId);

        try {
            Workspace workspace = workspaceRepository.findById(workspaceId).orElse(null);

            if (workspace == null) {
                log.info("Workspace not found for workspaceId: {}", workspaceId); // Log the not found case
                return null; // Return null to indicate not found
            }

            log.info("Workspace retrieved: {}", workspace.getName());

            WorkspaceDetailsDTO dto = new WorkspaceDetailsDTO(workspace); // Create the DTO
            log.info("Exiting getWorkspace() for workspaceId: {}", workspaceId);
            return dto; // Correct return type: WorkspaceDetailsDTO
        } catch (Exception e) {
            log.error("Exception in getWorkspace() for workspaceId: {}", workspaceId, e);
            throw e;
        }
    }
}