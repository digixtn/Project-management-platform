package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}