package br.com.mmgestor.repository;

import br.com.mmgestor.domain.DadosAssociacao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DadosAssociacao entity.
 */
@Repository
public interface DadosAssociacaoRepository extends JpaRepository<DadosAssociacao, Long> {
}
