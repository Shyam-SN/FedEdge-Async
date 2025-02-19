package com.fededge.server.persistence.repository;

import com.fededge.server.persistence.entity.TrainingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingEventRepository extends JpaRepository<TrainingEvent, Long> {
}
