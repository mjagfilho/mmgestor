package br.com.mmgestor.repository;

import br.com.mmgestor.domain.Responsavel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Responsavel entity.
 */
@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {

}
