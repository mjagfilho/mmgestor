package br.com.mmgestor.service;

import br.com.mmgestor.domain.AssociadosHaras;
import br.com.mmgestor.repository.AssociadosHarasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AssociadosHaras}.
 */
@Service
@Transactional
public class AssociadosHarasService {

    private final Logger log = LoggerFactory.getLogger(AssociadosHarasService.class);

    private final AssociadosHarasRepository associadosHarasRepository;

    public AssociadosHarasService(AssociadosHarasRepository associadosHarasRepository) {
        this.associadosHarasRepository = associadosHarasRepository;
    }

    /**
     * Save a associadosHaras.
     *
     * @param associadosHaras the entity to save.
     * @return the persisted entity.
     */
    public AssociadosHaras save(AssociadosHaras associadosHaras) {
        log.debug("Request to save AssociadosHaras : {}", associadosHaras);
        return associadosHarasRepository.save(associadosHaras);
    }

    /**
     * Get all the associadosHaras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AssociadosHaras> findAll(Pageable pageable) {
        log.debug("Request to get all AssociadosHaras");
        return associadosHarasRepository.findAll(pageable);
    }

    /**
     * Get one associadosHaras by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AssociadosHaras> findOne(Long id) {
        log.debug("Request to get AssociadosHaras : {}", id);
        return associadosHarasRepository.findById(id);
    }

    /**
     * Delete the associadosHaras by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AssociadosHaras : {}", id);
        associadosHarasRepository.deleteById(id);
    }
}
