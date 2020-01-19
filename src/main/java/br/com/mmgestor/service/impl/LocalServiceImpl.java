package br.com.mmgestor.service.impl;

import br.com.mmgestor.service.LocalService;
import br.com.mmgestor.domain.Local;
import br.com.mmgestor.repository.LocalRepository;
import br.com.mmgestor.repository.search.LocalSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Local}.
 */
@Service
@Transactional
public class LocalServiceImpl implements LocalService {

    private final Logger log = LoggerFactory.getLogger(LocalServiceImpl.class);

    private final LocalRepository localRepository;

    private final LocalSearchRepository localSearchRepository;

    public LocalServiceImpl(LocalRepository localRepository, LocalSearchRepository localSearchRepository) {
        this.localRepository = localRepository;
        this.localSearchRepository = localSearchRepository;
    }

    /**
     * Save a local.
     *
     * @param local the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Local save(Local local) {
        log.debug("Request to save Local : {}", local);
        Local result = localRepository.save(local);
        localSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the locals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Local> findAll(Pageable pageable) {
        log.debug("Request to get all Locals");
        return localRepository.findAll(pageable);
    }


    /**
     * Get one local by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Local> findOne(Long id) {
        log.debug("Request to get Local : {}", id);
        return localRepository.findById(id);
    }

    /**
     * Delete the local by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Local : {}", id);
        localRepository.deleteById(id);
        localSearchRepository.deleteById(id);
    }

    /**
     * Search for the local corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Local> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Locals for query {}", query);
        return localSearchRepository.search(queryStringQuery(query), pageable);    }
}
