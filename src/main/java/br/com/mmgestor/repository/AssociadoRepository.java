package br.com.mmgestor.repository;

import br.com.mmgestor.domain.Associado;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Associado entity.
 */
@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

}
