package br.com.mmgestor.web.rest;

import br.com.mmgestor.domain.ClienteFornecedor;
import br.com.mmgestor.service.ClienteFornecedorService;
import br.com.mmgestor.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.mmgestor.domain.ClienteFornecedor}.
 */
@RestController
@RequestMapping("/api")
public class ClienteFornecedorResource {

    private final Logger log = LoggerFactory.getLogger(ClienteFornecedorResource.class);

    private static final String ENTITY_NAME = "clienteFornecedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClienteFornecedorService clienteFornecedorService;

    public ClienteFornecedorResource(ClienteFornecedorService clienteFornecedorService) {
        this.clienteFornecedorService = clienteFornecedorService;
    }

    /**
     * {@code POST  /cliente-fornecedors} : Create a new clienteFornecedor.
     *
     * @param clienteFornecedor the clienteFornecedor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clienteFornecedor, or with status {@code 400 (Bad Request)} if the clienteFornecedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cliente-fornecedors")
    public ResponseEntity<ClienteFornecedor> createClienteFornecedor(@Valid @RequestBody ClienteFornecedor clienteFornecedor) throws URISyntaxException {
        log.debug("REST request to save ClienteFornecedor : {}", clienteFornecedor);
        if (clienteFornecedor.getId() != null) {
            throw new BadRequestAlertException("A new clienteFornecedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClienteFornecedor result = clienteFornecedorService.save(clienteFornecedor);
        return ResponseEntity.created(new URI("/api/cliente-fornecedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cliente-fornecedors} : Updates an existing clienteFornecedor.
     *
     * @param clienteFornecedor the clienteFornecedor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clienteFornecedor,
     * or with status {@code 400 (Bad Request)} if the clienteFornecedor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clienteFornecedor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cliente-fornecedors")
    public ResponseEntity<ClienteFornecedor> updateClienteFornecedor(@Valid @RequestBody ClienteFornecedor clienteFornecedor) throws URISyntaxException {
        log.debug("REST request to update ClienteFornecedor : {}", clienteFornecedor);
        if (clienteFornecedor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClienteFornecedor result = clienteFornecedorService.save(clienteFornecedor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clienteFornecedor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cliente-fornecedors} : get all the clienteFornecedors.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clienteFornecedors in body.
     */
    @GetMapping("/cliente-fornecedors")
    public ResponseEntity<List<ClienteFornecedor>> getAllClienteFornecedors(Pageable pageable) {
        log.debug("REST request to get a page of ClienteFornecedors");
        Page<ClienteFornecedor> page = clienteFornecedorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cliente-fornecedors/:id} : get the "id" clienteFornecedor.
     *
     * @param id the id of the clienteFornecedor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clienteFornecedor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cliente-fornecedors/{id}")
    public ResponseEntity<ClienteFornecedor> getClienteFornecedor(@PathVariable Long id) {
        log.debug("REST request to get ClienteFornecedor : {}", id);
        Optional<ClienteFornecedor> clienteFornecedor = clienteFornecedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clienteFornecedor);
    }

    /**
     * {@code DELETE  /cliente-fornecedors/:id} : delete the "id" clienteFornecedor.
     *
     * @param id the id of the clienteFornecedor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cliente-fornecedors/{id}")
    public ResponseEntity<Void> deleteClienteFornecedor(@PathVariable Long id) {
        log.debug("REST request to delete ClienteFornecedor : {}", id);
        clienteFornecedorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
