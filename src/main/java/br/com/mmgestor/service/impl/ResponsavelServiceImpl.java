package br.com.mmgestor.service.impl;

import br.com.mmgestor.service.ResponsavelService;
import br.com.mmgestor.domain.Responsavel;
import br.com.mmgestor.repository.ResponsavelRepository;
import br.com.mmgestor.repository.search.ResponsavelSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Responsavel}.
 */
@Service
@Transactional
public class ResponsavelServiceImpl implements ResponsavelService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelServiceImpl.class);

    private final ResponsavelRepository responsavelRepository;

    private final ResponsavelSearchRepository responsavelSearchRepository;

    public ResponsavelServiceImpl(ResponsavelRepository responsavelRepository, ResponsavelSearchRepository responsavelSearchRepository) {
        this.responsavelRepository = responsavelRepository;
        this.responsavelSearchRepository = responsavelSearchRepository;
    }

    /**
     * Save a responsavel.
     *
     * @param responsavel the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Responsavel save(Responsavel responsavel) {
        log.debug("Request to save Responsavel : {}", responsavel);
        Responsavel result = responsavelRepository.save(responsavel);
        responsavelSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the responsavels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Responsavel> findAll(Pageable pageable) {
        log.debug("Request to get all Responsavels");
        return responsavelRepository.findAll(pageable);
    }


    /**
     * Get one responsavel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Responsavel> findOne(Long id) {
        log.debug("Request to get Responsavel : {}", id);
        return responsavelRepository.findById(id);
    }

    /**
     * Delete the responsavel by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Responsavel : {}", id);
        responsavelRepository.deleteById(id);
        responsavelSearchRepository.deleteById(id);
    }

    /**
     * Search for the responsavel corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Responsavel> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Responsavels for query {}", query);
        return responsavelSearchRepository.search(queryStringQuery(query), pageable);    }
}
