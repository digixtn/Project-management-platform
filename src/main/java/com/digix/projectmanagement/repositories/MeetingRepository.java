package com.digix.projectmanagement.repositories;

import com.digix.projectmanagement.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
}