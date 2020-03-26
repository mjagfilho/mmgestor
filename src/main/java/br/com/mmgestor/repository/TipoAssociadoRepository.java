package br.com.mmgestor.repository;

import br.com.mmgestor.domain.TipoAssociado;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoAssociado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoAssociadoRepository extends JpaRepository<TipoAssociado, Long> {

}
