package br.com.mmgestor.service;

import br.com.mmgestor.domain.TipoLocal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TipoLocal}.
 */
public interface TipoLocalService {

    /**
     * Save a tipoLocal.
     *
     * @param tipoLocal the entity to save.
     * @return the persisted entity.
     */
    TipoLocal save(TipoLocal tipoLocal);

    /**
     * Get all the tipoLocals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoLocal> findAll(Pageable pageable);


    /**
     * Get the "id" tipoLocal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoLocal> findOne(Long id);

    /**
     * Delete the "id" tipoLocal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the tipoLocal corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoLocal> search(String query, Pageable pageable);
}
