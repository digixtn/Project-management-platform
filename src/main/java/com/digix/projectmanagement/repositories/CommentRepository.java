package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}