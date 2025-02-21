package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {
  List<Workspace> findByCreatedByUserId(int userId);

  @Query("SELECT w FROM Workspace w JOIN w.workspaceUsers wu WHERE wu.user.userId = :userId")
  List<Workspace> findWorkspacesForUser(@Param("userId") int userId);

  @Query("SELECT wu.id.userId FROM WorkspaceUser wu WHERE wu.id.workspaceId = :workspaceId") // Corrected query
  Set<Integer> findMemberUserIdsByWorkspaceId(int workspaceId);

  boolean existsByName(String name);
}