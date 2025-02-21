package com.digix.projectmanagement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "workspace_users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceUser {

    @EmbeddedId
    private WorkspaceUserId id;

    @Column(name = "joined_date", nullable = false)
    private LocalDate joinedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workspaceId")
    @JoinColumn(name = "workspace_id")
    @JsonBackReference
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    // ... other methods if needed (but NOT the convenience constructor that sets the ID)

    // Composite key class (unchanged)
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorkspaceUserId implements Serializable {
        private int workspaceId;
        private int userId;
    }
}