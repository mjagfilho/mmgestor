package br.com.mmgestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mmgestor.domain.Local;

/**
 * Spring Data  repository for the Local entity.
 */
@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
}
