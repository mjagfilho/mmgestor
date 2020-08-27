package br.com.mmgestor.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.mmgestor.domain.Associado;
import br.com.mmgestor.service.AssociadoService;
import br.com.mmgestor.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.mmgestor.domain.Associado}.
 */
@RestController
@RequestMapping("/api")
public class AssociadoResource {

    private final Logger log = LoggerFactory.getLogger(AssociadoResource.class);

    private static final String ENTITY_NAME = "associado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssociadoService associadoService;

    public AssociadoResource(AssociadoService associadoService) {
        this.associadoService = associadoService;
    }

    /**
     * {@code POST  /associados} : Create a new associado.
     *
     * @param associado the associado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new associado, or with status {@code 400 (Bad Request)} if
     *         the associado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/associados")
    public ResponseEntity<Associado> createAssociado(@Valid @RequestBody Associado associado)
            throws URISyntaxException {
        log.debug("REST request to save Associado : {}", associado);
        if (associado.getId() != null) {
            throw new BadRequestAlertException("A new associado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Associado result = associadoService.save(associado);
        return ResponseEntity
                .created(new URI("/api/associados/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /associados} : Updates an existing associado.
     *
     * @param associado the associado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated associado, or with status {@code 400 (Bad Request)} if
     *         the associado is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the associado couldn't be
     *         updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/associados")
    public ResponseEntity<Associado> updateAssociado(@Valid @RequestBody Associado associado)
            throws URISyntaxException {
        log.debug("REST request to update Associado : {}", associado);
        if (associado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Associado result = associadoService.save(associado);
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, associado.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /associados} : get all the associados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of associados in body.
     */
    @GetMapping("/associados")
    public ResponseEntity<List<Associado>> getAllAssociados(Pageable pageable) {
        log.debug("REST request to get a page of Associados");
        Page<Associado> page = associadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /associados/:id} : get the "id" associado.
     *
     * @param id the id of the associado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the associado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/associados/{id}")
    public ResponseEntity<Associado> getAssociado(@PathVariable Long id) {
        log.debug("REST request to get Associado : {}", id);
        Optional<Associado> associado = associadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(associado);
    }

    /**
     * {@code DELETE  /associados/:id} : delete the "id" associado.
     *
     * @param id the id of the associado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/associados/{id}")
    public ResponseEntity<Void> deleteAssociado(@PathVariable Long id) {
        log.debug("REST request to delete Associado : {}", id);
        associadoService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
