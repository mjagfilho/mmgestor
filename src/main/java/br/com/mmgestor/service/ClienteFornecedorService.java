package br.com.mmgestor.service;

import br.com.mmgestor.domain.ClienteFornecedor;
import br.com.mmgestor.repository.ClienteFornecedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClienteFornecedor}.
 */
@Service
@Transactional
public class ClienteFornecedorService {

    private final Logger log = LoggerFactory.getLogger(ClienteFornecedorService.class);

    private final ClienteFornecedorRepository clienteFornecedorRepository;

    public ClienteFornecedorService(ClienteFornecedorRepository clienteFornecedorRepository) {
        this.clienteFornecedorRepository = clienteFornecedorRepository;
    }

    /**
     * Save a clienteFornecedor.
     *
     * @param clienteFornecedor the entity to save.
     * @return the persisted entity.
     */
    public ClienteFornecedor save(ClienteFornecedor clienteFornecedor) {
        log.debug("Request to save ClienteFornecedor : {}", clienteFornecedor);
        return clienteFornecedorRepository.save(clienteFornecedor);
    }

    /**
     * Get all the clienteFornecedors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClienteFornecedor> findAll(Pageable pageable) {
        log.debug("Request to get all ClienteFornecedors");
        return clienteFornecedorRepository.findAll(pageable);
    }

    /**
     * Get one clienteFornecedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClienteFornecedor> findOne(Long id) {
        log.debug("Request to get ClienteFornecedor : {}", id);
        return clienteFornecedorRepository.findById(id);
    }

    /**
     * Delete the clienteFornecedor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClienteFornecedor : {}", id);
        clienteFornecedorRepository.deleteById(id);
    }
}
