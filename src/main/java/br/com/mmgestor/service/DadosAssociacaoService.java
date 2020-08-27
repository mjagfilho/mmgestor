package br.com.mmgestor.service;

import br.com.mmgestor.domain.DadosAssociacao;
import br.com.mmgestor.repository.DadosAssociacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link DadosAssociacao}.
 */
@Service
@Transactional
public class DadosAssociacaoService {

    private final Logger log = LoggerFactory.getLogger(DadosAssociacaoService.class);

    private final DadosAssociacaoRepository dadosAssociacaoRepository;

    public DadosAssociacaoService(DadosAssociacaoRepository dadosAssociacaoRepository) {
        this.dadosAssociacaoRepository = dadosAssociacaoRepository;
    }

    /**
     * Save a dadosAssociacao.
     *
     * @param dadosAssociacao the entity to save.
     * @return the persisted entity.
     */
    public DadosAssociacao save(DadosAssociacao dadosAssociacao) {
        log.debug("Request to save DadosAssociacao : {}", dadosAssociacao);
        return dadosAssociacaoRepository.save(dadosAssociacao);
    }

    /**
     * Get all the dadosAssociacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DadosAssociacao> findAll(Pageable pageable) {
        log.debug("Request to get all DadosAssociacaos");
        return dadosAssociacaoRepository.findAll(pageable);
    }



    /**
     *  Get all the dadosAssociacaos where Animal is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<DadosAssociacao> findAllWhereAnimalIsNull() {
        log.debug("Request to get all dadosAssociacaos where Animal is null");
        return StreamSupport
            .stream(dadosAssociacaoRepository.findAll().spliterator(), false)
            .filter(dadosAssociacao -> dadosAssociacao.getAnimal() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one dadosAssociacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DadosAssociacao> findOne(Long id) {
        log.debug("Request to get DadosAssociacao : {}", id);
        return dadosAssociacaoRepository.findById(id);
    }

    /**
     * Delete the dadosAssociacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DadosAssociacao : {}", id);
        dadosAssociacaoRepository.deleteById(id);
    }
}
