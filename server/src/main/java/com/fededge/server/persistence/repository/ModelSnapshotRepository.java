package com.fededge.server.persistence.repository;

import com.fededge.server.persistence.entity.ModelSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface ModelSnapshotRepository extends JpaRepository<ModelSnapshot, Long> {
    
    @Query("SELECT m FROM ModelSnapshot m ORDER BY m.version DESC LIMIT 1")
    Optional<ModelSnapshot> findLatestModel();
}
