package br.com.mmgestor.service;

import br.com.mmgestor.domain.Responsavel;
import br.com.mmgestor.repository.ResponsavelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Responsavel}.
 */
@Service
@Transactional
public class ResponsavelService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelService.class);

    private final ResponsavelRepository responsavelRepository;

    public ResponsavelService(ResponsavelRepository responsavelRepository) {
        this.responsavelRepository = responsavelRepository;
    }

    /**
     * Save a responsavel.
     *
     * @param responsavel the entity to save.
     * @return the persisted entity.
     */
    public Responsavel save(Responsavel responsavel) {
        log.debug("Request to save Responsavel : {}", responsavel);
        return responsavelRepository.save(responsavel);
    }

    /**
     * Get all the responsavels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete Responsavel : {}", id);
        responsavelRepository.deleteById(id);
    }
}
