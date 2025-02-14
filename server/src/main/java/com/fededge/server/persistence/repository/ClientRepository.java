package com.fededge.server.persistence.repository;

import com.fededge.server.persistence.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
