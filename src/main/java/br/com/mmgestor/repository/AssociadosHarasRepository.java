package br.com.mmgestor.repository;

import br.com.mmgestor.domain.AssociadosHaras;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AssociadosHaras entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssociadosHarasRepository extends JpaRepository<AssociadosHaras, Long> {

}
