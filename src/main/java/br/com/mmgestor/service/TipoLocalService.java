package br.com.mmgestor.service;

import br.com.mmgestor.domain.TipoLocal;
import br.com.mmgestor.repository.TipoLocalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoLocal}.
 */
@Service
@Transactional
public class TipoLocalService {

    private final Logger log = LoggerFactory.getLogger(TipoLocalService.class);

    private final TipoLocalRepository tipoLocalRepository;

    public TipoLocalService(TipoLocalRepository tipoLocalRepository) {
        this.tipoLocalRepository = tipoLocalRepository;
    }

    /**
     * Save a tipoLocal.
     *
     * @param tipoLocal the entity to save.
     * @return the persisted entity.
     */
    public TipoLocal save(TipoLocal tipoLocal) {
        log.debug("Request to save TipoLocal : {}", tipoLocal);
        return tipoLocalRepository.save(tipoLocal);
    }

    /**
     * Get all the tipoLocals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete TipoLocal : {}", id);
        tipoLocalRepository.deleteById(id);
    }
}
