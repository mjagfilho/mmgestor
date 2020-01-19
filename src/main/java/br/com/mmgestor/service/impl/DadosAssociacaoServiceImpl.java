package br.com.mmgestor.service.impl;

import br.com.mmgestor.service.DadosAssociacaoService;
import br.com.mmgestor.domain.DadosAssociacao;
import br.com.mmgestor.repository.DadosAssociacaoRepository;
import br.com.mmgestor.repository.search.DadosAssociacaoSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DadosAssociacao}.
 */
@Service
@Transactional
public class DadosAssociacaoServiceImpl implements DadosAssociacaoService {

    private final Logger log = LoggerFactory.getLogger(DadosAssociacaoServiceImpl.class);

    private final DadosAssociacaoRepository dadosAssociacaoRepository;

    private final DadosAssociacaoSearchRepository dadosAssociacaoSearchRepository;

    public DadosAssociacaoServiceImpl(DadosAssociacaoRepository dadosAssociacaoRepository, DadosAssociacaoSearchRepository dadosAssociacaoSearchRepository) {
        this.dadosAssociacaoRepository = dadosAssociacaoRepository;
        this.dadosAssociacaoSearchRepository = dadosAssociacaoSearchRepository;
    }

    /**
     * Save a dadosAssociacao.
     *
     * @param dadosAssociacao the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DadosAssociacao save(DadosAssociacao dadosAssociacao) {
        log.debug("Request to save DadosAssociacao : {}", dadosAssociacao);
        DadosAssociacao result = dadosAssociacaoRepository.save(dadosAssociacao);
        dadosAssociacaoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the dadosAssociacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
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
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DadosAssociacao : {}", id);
        dadosAssociacaoRepository.deleteById(id);
        dadosAssociacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the dadosAssociacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DadosAssociacao> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DadosAssociacaos for query {}", query);
        return dadosAssociacaoSearchRepository.search(queryStringQuery(query), pageable);    }
}
