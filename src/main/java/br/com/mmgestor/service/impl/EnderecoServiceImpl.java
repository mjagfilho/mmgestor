package br.com.mmgestor.service.impl;

import br.com.mmgestor.service.EnderecoService;
import br.com.mmgestor.domain.Endereco;
import br.com.mmgestor.repository.EnderecoRepository;
import br.com.mmgestor.repository.search.EnderecoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Endereco}.
 */
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    private final EnderecoRepository enderecoRepository;

    private final EnderecoSearchRepository enderecoSearchRepository;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, EnderecoSearchRepository enderecoSearchRepository) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoSearchRepository = enderecoSearchRepository;
    }

    /**
     * Save a endereco.
     *
     * @param endereco the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Endereco save(Endereco endereco) {
        log.debug("Request to save Endereco : {}", endereco);
        Endereco result = enderecoRepository.save(endereco);
        enderecoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the enderecos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Endereco> findAll(Pageable pageable) {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll(pageable);
    }


    /**
     * Get one endereco by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Endereco> findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        return enderecoRepository.findById(id);
    }

    /**
     * Delete the endereco by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
        enderecoSearchRepository.deleteById(id);
    }

    /**
     * Search for the endereco corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Endereco> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Enderecos for query {}", query);
        return enderecoSearchRepository.search(queryStringQuery(query), pageable);    }
}
