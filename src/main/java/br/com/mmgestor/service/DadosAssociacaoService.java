package br.com.mmgestor.service;

import br.com.mmgestor.domain.DadosAssociacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DadosAssociacao}.
 */
public interface DadosAssociacaoService {

    /**
     * Save a dadosAssociacao.
     *
     * @param dadosAssociacao the entity to save.
     * @return the persisted entity.
     */
    DadosAssociacao save(DadosAssociacao dadosAssociacao);

    /**
     * Get all the dadosAssociacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DadosAssociacao> findAll(Pageable pageable);
    /**
     * Get all the DadosAssociacaoDTO where Animal is {@code null}.
     *
     * @return the list of entities.
     */
    List<DadosAssociacao> findAllWhereAnimalIsNull();


    /**
     * Get the "id" dadosAssociacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DadosAssociacao> findOne(Long id);

    /**
     * Delete the "id" dadosAssociacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the dadosAssociacao corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DadosAssociacao> search(String query, Pageable pageable);
}
