package br.com.mmgestor.service;

import br.com.mmgestor.domain.TipoAssociado;
import br.com.mmgestor.repository.TipoAssociadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoAssociado}.
 */
@Service
@Transactional
public class TipoAssociadoService {

    private final Logger log = LoggerFactory.getLogger(TipoAssociadoService.class);

    private final TipoAssociadoRepository tipoAssociadoRepository;

    public TipoAssociadoService(TipoAssociadoRepository tipoAssociadoRepository) {
        this.tipoAssociadoRepository = tipoAssociadoRepository;
    }

    /**
     * Save a tipoAssociado.
     *
     * @param tipoAssociado the entity to save.
     * @return the persisted entity.
     */
    public TipoAssociado save(TipoAssociado tipoAssociado) {
        log.debug("Request to save TipoAssociado : {}", tipoAssociado);
        return tipoAssociadoRepository.save(tipoAssociado);
    }

    /**
     * Get all the tipoAssociados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoAssociado> findAll(Pageable pageable) {
        log.debug("Request to get all TipoAssociados");
        return tipoAssociadoRepository.findAll(pageable);
    }


    /**
     * Get one tipoAssociado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoAssociado> findOne(Long id) {
        log.debug("Request to get TipoAssociado : {}", id);
        return tipoAssociadoRepository.findById(id);
    }

    /**
     * Delete the tipoAssociado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoAssociado : {}", id);
        tipoAssociadoRepository.deleteById(id);
    }
}
