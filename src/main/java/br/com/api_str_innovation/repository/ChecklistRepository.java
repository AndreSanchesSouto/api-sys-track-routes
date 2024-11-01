package br.com.api_str_innovation.repository;

import br.com.api_str_innovation.entities.checklist.ChecklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChecklistRepository extends JpaRepository<ChecklistEntity, UUID> {
}
