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

import br.com.mmgestor.domain.Local;
import br.com.mmgestor.service.LocalService;
import br.com.mmgestor.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.mmgestor.domain.Local}.
 */
@RestController
@RequestMapping("/api")
public class LocalResource {

    private final Logger log = LoggerFactory.getLogger(LocalResource.class);

    private static final String ENTITY_NAME = "local";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalService localService;

    public LocalResource(LocalService localService) {
        this.localService = localService;
    }

    /**
     * {@code POST  /locals} : Create a new local.
     *
     * @param local the local to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new local, or with status {@code 400 (Bad Request)} if the local has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/locals")
    public ResponseEntity<Local> createLocal(@Valid @RequestBody Local local) throws URISyntaxException {
        log.debug("REST request to save Local : {}", local);
        if (local.getId() != null) {
            throw new BadRequestAlertException("A new local cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Local result = localService.save(local);
        return ResponseEntity.created(new URI("/api/locals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /locals} : Updates an existing local.
     *
     * @param local the local to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated local,
     * or with status {@code 400 (Bad Request)} if the local is not valid,
     * or with status {@code 500 (Internal Server Error)} if the local couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/locals")
    public ResponseEntity<Local> updateLocal(@Valid @RequestBody Local local) throws URISyntaxException {
        log.debug("REST request to update Local : {}", local);
        if (local.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Local result = localService.save(local);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, local.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /locals} : get all the locals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locals in body.
     */
    @GetMapping("/locals")
    public ResponseEntity<List<Local>> getAllLocals(Pageable pageable) {
        log.debug("REST request to get a page of Locals");
        Page<Local> page = localService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /locals/:id} : get the "id" local.
     *
     * @param id the id of the local to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the local, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/locals/{id}")
    public ResponseEntity<Local> getLocal(@PathVariable Long id) {
        log.debug("REST request to get Local : {}", id);
        Optional<Local> local = localService.findOne(id);
        return ResponseUtil.wrapOrNotFound(local);
    }

    /**
     * {@code DELETE  /locals/:id} : delete the "id" local.
     *
     * @param id the id of the local to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/locals/{id}")
    public ResponseEntity<Void> deleteLocal(@PathVariable Long id) {
        log.debug("REST request to delete Local : {}", id);
        localService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
