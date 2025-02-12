package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}