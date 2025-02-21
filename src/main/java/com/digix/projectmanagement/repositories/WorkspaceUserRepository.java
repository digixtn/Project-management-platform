package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.User;
import com.digix.projectmanagement.entities.Workspace;
import com.digix.projectmanagement.entities.WorkspaceUser;
import com.digix.projectmanagement.entities.WorkspaceUser.WorkspaceUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, WorkspaceUserId> {
  List<WorkspaceUser> findByWorkspaceWorkspaceId(int workspaceId);
  List<WorkspaceUser> findByUserUserId(int userId);
  void deleteByWorkspaceWorkspaceId(int workspaceId);
  boolean existsByIdWorkspaceIdAndIdUserId(int workspaceId, int userId);
  Optional<WorkspaceUser> findByWorkspaceAndUser(Workspace workspace, User user);
}