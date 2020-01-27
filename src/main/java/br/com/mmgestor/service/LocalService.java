package br.com.mmgestor.service;

import br.com.mmgestor.domain.Local;
import br.com.mmgestor.repository.LocalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Local}.
 */
@Service
@Transactional
public class LocalService {

    private final Logger log = LoggerFactory.getLogger(LocalService.class);

    private final LocalRepository localRepository;

    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    /**
     * Save a local.
     *
     * @param local the entity to save.
     * @return the persisted entity.
     */
    public Local save(Local local) {
        log.debug("Request to save Local : {}", local);
        return localRepository.save(local);
    }

    /**
     * Get all the locals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete Local : {}", id);
        localRepository.deleteById(id);
    }
}
