package org.example.project.database.repo;

import jakarta.transaction.Transactional;
import org.example.project.database.model.PersistentSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PersistentSessionRepository extends JpaRepository<PersistentSession, Integer> {
}
