package br.com.mmgestor.web.rest;

import br.com.mmgestor.domain.Haras;
import br.com.mmgestor.service.HarasService;
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
 * REST controller for managing {@link br.com.mmgestor.domain.Haras}.
 */
@RestController
@RequestMapping("/api")
public class HarasResource {

    private final Logger log = LoggerFactory.getLogger(HarasResource.class);

    private static final String ENTITY_NAME = "haras";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HarasService harasService;

    public HarasResource(HarasService harasService) {
        this.harasService = harasService;
    }

    /**
     * {@code POST  /haras} : Create a new haras.
     *
     * @param haras the haras to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new haras, or with status {@code 400 (Bad Request)} if the haras has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/haras")
    public ResponseEntity<Haras> createHaras(@Valid @RequestBody Haras haras) throws URISyntaxException {
        log.debug("REST request to save Haras : {}", haras);
        if (haras.getId() != null) {
            throw new BadRequestAlertException("A new haras cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Haras result = harasService.save(haras);
        return ResponseEntity.created(new URI("/api/haras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /haras} : Updates an existing haras.
     *
     * @param haras the haras to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated haras,
     * or with status {@code 400 (Bad Request)} if the haras is not valid,
     * or with status {@code 500 (Internal Server Error)} if the haras couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/haras")
    public ResponseEntity<Haras> updateHaras(@Valid @RequestBody Haras haras) throws URISyntaxException {
        log.debug("REST request to update Haras : {}", haras);
        if (haras.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Haras result = harasService.save(haras);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, haras.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /haras} : get all the haras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of haras in body.
     */
    @GetMapping("/haras")
    public ResponseEntity<List<Haras>> getAllHaras(Pageable pageable) {
        log.debug("REST request to get a page of Haras");
        Page<Haras> page = harasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /haras/:id} : get the "id" haras.
     *
     * @param id the id of the haras to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the haras, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/haras/{id}")
    public ResponseEntity<Haras> getHaras(@PathVariable Long id) {
        log.debug("REST request to get Haras : {}", id);
        Optional<Haras> haras = harasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(haras);
    }

    /**
     * {@code DELETE  /haras/:id} : delete the "id" haras.
     *
     * @param id the id of the haras to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/haras/{id}")
    public ResponseEntity<Void> deleteHaras(@PathVariable Long id) {
        log.debug("REST request to delete Haras : {}", id);
        harasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
