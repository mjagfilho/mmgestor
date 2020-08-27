package br.com.mmgestor.service;

import br.com.mmgestor.domain.Haras;
import br.com.mmgestor.repository.HarasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Haras}.
 */
@Service
@Transactional
public class HarasService {

    private final Logger log = LoggerFactory.getLogger(HarasService.class);

    private final HarasRepository harasRepository;

    public HarasService(HarasRepository harasRepository) {
        this.harasRepository = harasRepository;
    }

    /**
     * Save a haras.
     *
     * @param haras the entity to save.
     * @return the persisted entity.
     */
    public Haras save(Haras haras) {
        log.debug("Request to save Haras : {}", haras);
        return harasRepository.save(haras);
    }

    /**
     * Get all the haras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete Haras : {}", id);
        harasRepository.deleteById(id);
    }
}
