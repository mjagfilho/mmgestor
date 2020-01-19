package br.com.mmgestor.service.impl;

import br.com.mmgestor.service.ClienteFornecedorService;
import br.com.mmgestor.domain.ClienteFornecedor;
import br.com.mmgestor.repository.ClienteFornecedorRepository;
import br.com.mmgestor.repository.search.ClienteFornecedorSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ClienteFornecedor}.
 */
@Service
@Transactional
public class ClienteFornecedorServiceImpl implements ClienteFornecedorService {

    private final Logger log = LoggerFactory.getLogger(ClienteFornecedorServiceImpl.class);

    private final ClienteFornecedorRepository clienteFornecedorRepository;

    private final ClienteFornecedorSearchRepository clienteFornecedorSearchRepository;

    public ClienteFornecedorServiceImpl(ClienteFornecedorRepository clienteFornecedorRepository, ClienteFornecedorSearchRepository clienteFornecedorSearchRepository) {
        this.clienteFornecedorRepository = clienteFornecedorRepository;
        this.clienteFornecedorSearchRepository = clienteFornecedorSearchRepository;
    }

    /**
     * Save a clienteFornecedor.
     *
     * @param clienteFornecedor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClienteFornecedor save(ClienteFornecedor clienteFornecedor) {
        log.debug("Request to save ClienteFornecedor : {}", clienteFornecedor);
        ClienteFornecedor result = clienteFornecedorRepository.save(clienteFornecedor);
        clienteFornecedorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the clienteFornecedors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteFornecedor> findAll(Pageable pageable) {
        log.debug("Request to get all ClienteFornecedors");
        return clienteFornecedorRepository.findAll(pageable);
    }


    /**
     * Get one clienteFornecedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteFornecedor> findOne(Long id) {
        log.debug("Request to get ClienteFornecedor : {}", id);
        return clienteFornecedorRepository.findById(id);
    }

    /**
     * Delete the clienteFornecedor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClienteFornecedor : {}", id);
        clienteFornecedorRepository.deleteById(id);
        clienteFornecedorSearchRepository.deleteById(id);
    }

    /**
     * Search for the clienteFornecedor corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClienteFornecedor> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ClienteFornecedors for query {}", query);
        return clienteFornecedorSearchRepository.search(queryStringQuery(query), pageable);    }
}
