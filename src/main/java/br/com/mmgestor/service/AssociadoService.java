package br.com.mmgestor.service;

import br.com.mmgestor.domain.Associado;
import br.com.mmgestor.repository.AssociadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Associado}.
 */
@Service
@Transactional
public class AssociadoService {

    private final Logger log = LoggerFactory.getLogger(AssociadoService.class);

    private final AssociadoRepository associadoRepository;

    public AssociadoService(AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    /**
     * Save a associado.
     *
     * @param associado the entity to save.
     * @return the persisted entity.
     */
    public Associado save(Associado associado) {
        log.debug("Request to save Associado : {}", associado);
        return associadoRepository.save(associado);
    }

    /**
     * Get all the associados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Associado> findAll(Pageable pageable) {
        log.debug("Request to get all Associados");
        return associadoRepository.findAll(pageable);
    }


    /**
     * Get one associado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Associado> findOne(Long id) {
        log.debug("Request to get Associado : {}", id);
        return associadoRepository.findById(id);
    }

    /**
     * Delete the associado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Associado : {}", id);
        associadoRepository.deleteById(id);
    }
}
