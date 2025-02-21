package com.digix.projectmanagement.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "workspaces")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "workspaceUsers")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workspaceId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<WorkspaceUser> workspaceUsers = new HashSet<>();

    @PrePersist
    public void prePersist() {
        if (createdDate == null) {
            createdDate = LocalDate.now();
        }
    }

    // Helper methods
    public void addUser(User user) {
        WorkspaceUser workspaceUser = new WorkspaceUser();
        workspaceUser.setWorkspace(this);
        workspaceUser.setUser(user);
        workspaceUser.setJoinedDate(LocalDate.now());
        workspaceUsers.add(workspaceUser);
    }

    public void removeUser(User user) {
        workspaceUsers.removeIf(workspaceUser -> workspaceUser.getUser().equals(user));
    }
}