package com.digix.projectmanagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue
    private int notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    private String notificationType;
    private String notificationMessage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private boolean isRead;
}
