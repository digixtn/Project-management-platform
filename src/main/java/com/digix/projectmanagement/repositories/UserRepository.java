package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}