package br.com.mmgestor.service.impl;

import br.com.mmgestor.service.HarasService;
import br.com.mmgestor.domain.Haras;
import br.com.mmgestor.repository.HarasRepository;
import br.com.mmgestor.repository.search.HarasSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Haras}.
 */
@Service
@Transactional
public class HarasServiceImpl implements HarasService {

    private final Logger log = LoggerFactory.getLogger(HarasServiceImpl.class);

    private final HarasRepository harasRepository;

    private final HarasSearchRepository harasSearchRepository;

    public HarasServiceImpl(HarasRepository harasRepository, HarasSearchRepository harasSearchRepository) {
        this.harasRepository = harasRepository;
        this.harasSearchRepository = harasSearchRepository;
    }

    /**
     * Save a haras.
     *
     * @param haras the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Haras save(Haras haras) {
        log.debug("Request to save Haras : {}", haras);
        Haras result = harasRepository.save(haras);
        harasSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the haras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Haras> findAll(Pageable pageable) {
        log.debug("Request to get all Haras");
        return harasRepository.findAll(pageable);
    }


    /**
     * Get one haras by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Haras> findOne(Long id) {
        log.debug("Request to get Haras : {}", id);
        return harasRepository.findById(id);
    }

    /**
     * Delete the haras by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Haras : {}", id);
        harasRepository.deleteById(id);
        harasSearchRepository.deleteById(id);
    }

    /**
     * Search for the haras corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Haras> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Haras for query {}", query);
        return harasSearchRepository.search(queryStringQuery(query), pageable);    }
}
