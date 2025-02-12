package com.digix.projectmanagement.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue
    private int userId;
    private String name;
    private String email;
    private String contactInfo;
}
