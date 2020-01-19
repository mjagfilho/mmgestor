package br.com.mmgestor.service;

import br.com.mmgestor.domain.Haras;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Haras}.
 */
public interface HarasService {

    /**
     * Save a haras.
     *
     * @param haras the entity to save.
     * @return the persisted entity.
     */
    Haras save(Haras haras);

    /**
     * Get all the haras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Haras> findAll(Pageable pageable);


    /**
     * Get the "id" haras.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Haras> findOne(Long id);

    /**
     * Delete the "id" haras.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the haras corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Haras> search(String query, Pageable pageable);
}
