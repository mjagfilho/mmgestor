package br.com.mmgestor.web.rest;

import br.com.mmgestor.domain.TipoLocal;
import br.com.mmgestor.service.TipoLocalService;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.mmgestor.domain.TipoLocal}.
 */
@RestController
@RequestMapping("/api")
public class TipoLocalResource {

    private final Logger log = LoggerFactory.getLogger(TipoLocalResource.class);

    private static final String ENTITY_NAME = "tipoLocal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoLocalService tipoLocalService;

    public TipoLocalResource(TipoLocalService tipoLocalService) {
        this.tipoLocalService = tipoLocalService;
    }

    /**
     * {@code POST  /tipo-locals} : Create a new tipoLocal.
     *
     * @param tipoLocal the tipoLocal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoLocal, or with status {@code 400 (Bad Request)} if the tipoLocal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-locals")
    public ResponseEntity<TipoLocal> createTipoLocal(@Valid @RequestBody TipoLocal tipoLocal) throws URISyntaxException {
        log.debug("REST request to save TipoLocal : {}", tipoLocal);
        if (tipoLocal.getId() != null) {
            throw new BadRequestAlertException("A new tipoLocal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoLocal result = tipoLocalService.save(tipoLocal);
        return ResponseEntity.created(new URI("/api/tipo-locals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-locals} : Updates an existing tipoLocal.
     *
     * @param tipoLocal the tipoLocal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoLocal,
     * or with status {@code 400 (Bad Request)} if the tipoLocal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoLocal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-locals")
    public ResponseEntity<TipoLocal> updateTipoLocal(@Valid @RequestBody TipoLocal tipoLocal) throws URISyntaxException {
        log.debug("REST request to update TipoLocal : {}", tipoLocal);
        if (tipoLocal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoLocal result = tipoLocalService.save(tipoLocal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoLocal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-locals} : get all the tipoLocals.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoLocals in body.
     */
    @GetMapping("/tipo-locals")
    public ResponseEntity<List<TipoLocal>> getAllTipoLocals(Pageable pageable) {
        log.debug("REST request to get a page of TipoLocals");
        Page<TipoLocal> page = tipoLocalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-locals/:id} : get the "id" tipoLocal.
     *
     * @param id the id of the tipoLocal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoLocal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-locals/{id}")
    public ResponseEntity<TipoLocal> getTipoLocal(@PathVariable Long id) {
        log.debug("REST request to get TipoLocal : {}", id);
        Optional<TipoLocal> tipoLocal = tipoLocalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoLocal);
    }

    /**
     * {@code DELETE  /tipo-locals/:id} : delete the "id" tipoLocal.
     *
     * @param id the id of the tipoLocal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-locals/{id}")
    public ResponseEntity<Void> deleteTipoLocal(@PathVariable Long id) {
        log.debug("REST request to delete TipoLocal : {}", id);
        tipoLocalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tipo-locals?query=:query} : search for the tipoLocal corresponding
     * to the query.
     *
     * @param query the query of the tipoLocal search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tipo-locals")
    public ResponseEntity<List<TipoLocal>> searchTipoLocals(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoLocals for query {}", query);
        Page<TipoLocal> page = tipoLocalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
