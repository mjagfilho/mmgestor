package br.com.mmgestor.service;

import br.com.mmgestor.domain.Endereco;
import br.com.mmgestor.repository.EnderecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Endereco}.
 */
@Service
@Transactional
public class EnderecoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoService.class);

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    /**
     * Save a endereco.
     *
     * @param endereco the entity to save.
     * @return the persisted entity.
     */
    public Endereco save(Endereco endereco) {
        log.debug("Request to save Endereco : {}", endereco);
        return enderecoRepository.save(endereco);
    }

    /**
     * Get all the enderecos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
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
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
    }
}
