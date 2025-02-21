package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.User;
import com.digix.projectmanagement.entities.Workspace;
import com.digix.projectmanagement.entities.WorkspaceUser;
import com.digix.projectmanagement.entities.WorkspaceUser.WorkspaceUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, WorkspaceUserId> {
  List<WorkspaceUser> findByWorkspaceWorkspaceId(int workspaceId);
  Optional<WorkspaceUser> findByWorkspaceAndUser(Workspace workspace, User user);
  void deleteByWorkspace_WorkspaceId(int workspaceId);
  void deleteByWorkspace(Workspace workspace);

  @Modifying
  @Query("DELETE FROM WorkspaceUser wu WHERE wu.workspace.workspaceId = :workspaceId")
  void deleteByWorkspaceId(@Param("workspaceId") int workspaceId);
}
