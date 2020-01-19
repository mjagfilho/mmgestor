package br.com.mmgestor.service;

import br.com.mmgestor.domain.ClienteFornecedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ClienteFornecedor}.
 */
public interface ClienteFornecedorService {

    /**
     * Save a clienteFornecedor.
     *
     * @param clienteFornecedor the entity to save.
     * @return the persisted entity.
     */
    ClienteFornecedor save(ClienteFornecedor clienteFornecedor);

    /**
     * Get all the clienteFornecedors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClienteFornecedor> findAll(Pageable pageable);


    /**
     * Get the "id" clienteFornecedor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClienteFornecedor> findOne(Long id);

    /**
     * Delete the "id" clienteFornecedor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the clienteFornecedor corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClienteFornecedor> search(String query, Pageable pageable);
}
