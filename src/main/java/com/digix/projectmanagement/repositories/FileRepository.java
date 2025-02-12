package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}