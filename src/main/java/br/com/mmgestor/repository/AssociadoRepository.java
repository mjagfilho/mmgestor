package br.com.mmgestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mmgestor.domain.Associado;

/**
 * Spring Data  repository for the Associado entity.
 */
@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {
}
