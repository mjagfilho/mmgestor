package br.com.mmgestor.service.impl;

import br.com.mmgestor.service.TipoLocalService;
import br.com.mmgestor.domain.TipoLocal;
import br.com.mmgestor.repository.TipoLocalRepository;
import br.com.mmgestor.repository.search.TipoLocalSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link TipoLocal}.
 */
@Service
@Transactional
public class TipoLocalServiceImpl implements TipoLocalService {

    private final Logger log = LoggerFactory.getLogger(TipoLocalServiceImpl.class);

    private final TipoLocalRepository tipoLocalRepository;

    private final TipoLocalSearchRepository tipoLocalSearchRepository;

    public TipoLocalServiceImpl(TipoLocalRepository tipoLocalRepository, TipoLocalSearchRepository tipoLocalSearchRepository) {
        this.tipoLocalRepository = tipoLocalRepository;
        this.tipoLocalSearchRepository = tipoLocalSearchRepository;
    }

    /**
     * Save a tipoLocal.
     *
     * @param tipoLocal the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoLocal save(TipoLocal tipoLocal) {
        log.debug("Request to save TipoLocal : {}", tipoLocal);
        TipoLocal result = tipoLocalRepository.save(tipoLocal);
        tipoLocalSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the tipoLocals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoLocal> findAll(Pageable pageable) {
        log.debug("Request to get all TipoLocals");
        return tipoLocalRepository.findAll(pageable);
    }


    /**
     * Get one tipoLocal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoLocal> findOne(Long id) {
        log.debug("Request to get TipoLocal : {}", id);
        return tipoLocalRepository.findById(id);
    }

    /**
     * Delete the tipoLocal by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoLocal : {}", id);
        tipoLocalRepository.deleteById(id);
        tipoLocalSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoLocal corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoLocal> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoLocals for query {}", query);
        return tipoLocalSearchRepository.search(queryStringQuery(query), pageable);    }
}
