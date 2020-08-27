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

import br.com.mmgestor.domain.AssociadosHaras;
import br.com.mmgestor.service.AssociadosHarasService;
import br.com.mmgestor.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.mmgestor.domain.AssociadosHaras}.
 */
@RestController
@RequestMapping("/api")
public class AssociadosHarasResource {

    private final Logger log = LoggerFactory.getLogger(AssociadosHarasResource.class);

    private static final String ENTITY_NAME = "associadosHaras";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssociadosHarasService associadosHarasService;

    public AssociadosHarasResource(AssociadosHarasService associadosHarasService) {
        this.associadosHarasService = associadosHarasService;
    }

    /**
     * {@code POST  /associados-haras} : Create a new associadosHaras.
     *
     * @param associadosHaras the associadosHaras to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new associadosHaras, or with status
     *         {@code 400 (Bad Request)} if the associadosHaras has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/associados-haras")
    public ResponseEntity<AssociadosHaras> createAssociadosHaras(@Valid @RequestBody AssociadosHaras associadosHaras)
            throws URISyntaxException {
        log.debug("REST request to save AssociadosHaras : {}", associadosHaras);
        if (associadosHaras.getId() != null) {
            throw new BadRequestAlertException("A new associadosHaras cannot already have an ID", ENTITY_NAME,
                    "idexists");
        }
        AssociadosHaras result = associadosHarasService.save(associadosHaras);
        return ResponseEntity
                .created(new URI("/api/associados-haras/" + result.getId())).headers(HeaderUtil
                        .createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /associados-haras} : Updates an existing associadosHaras.
     *
     * @param associadosHaras the associadosHaras to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated associadosHaras, or with status {@code 400 (Bad Request)}
     *         if the associadosHaras is not valid, or with status
     *         {@code 500 (Internal Server Error)} if the associadosHaras couldn't
     *         be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/associados-haras")
    public ResponseEntity<AssociadosHaras> updateAssociadosHaras(@Valid @RequestBody AssociadosHaras associadosHaras)
            throws URISyntaxException {
        log.debug("REST request to update AssociadosHaras : {}", associadosHaras);
        if (associadosHaras.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssociadosHaras result = associadosHarasService.save(associadosHaras);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
                associadosHaras.getId().toString())).body(result);
    }

    /**
     * {@code GET  /associados-haras} : get all the associadosHaras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of associadosHaras in body.
     */
    @GetMapping("/associados-haras")
    public ResponseEntity<List<AssociadosHaras>> getAllAssociadosHaras(Pageable pageable) {
        log.debug("REST request to get a page of AssociadosHaras");
        Page<AssociadosHaras> page = associadosHarasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /associados-haras/:id} : get the "id" associadosHaras.
     *
     * @param id the id of the associadosHaras to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the associadosHaras, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/associados-haras/{id}")
    public ResponseEntity<AssociadosHaras> getAssociadosHaras(@PathVariable Long id) {
        log.debug("REST request to get AssociadosHaras : {}", id);
        Optional<AssociadosHaras> associadosHaras = associadosHarasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(associadosHaras);
    }

    /**
     * {@code DELETE  /associados-haras/:id} : delete the "id" associadosHaras.
     *
     * @param id the id of the associadosHaras to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/associados-haras/{id}")
    public ResponseEntity<Void> deleteAssociadosHaras(@PathVariable Long id) {
        log.debug("REST request to delete AssociadosHaras : {}", id);
        associadosHarasService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
