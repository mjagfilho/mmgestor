package br.com.mmgestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mmgestor.domain.Haras;

/**
 * Spring Data repository for the Haras entity.
 */
@Repository
public interface HarasRepository extends JpaRepository<Haras, Long> {
}
