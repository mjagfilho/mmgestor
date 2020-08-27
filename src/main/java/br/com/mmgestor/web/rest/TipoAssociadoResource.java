package br.com.mmgestor.web.rest;

import br.com.mmgestor.domain.TipoAssociado;
import br.com.mmgestor.service.TipoAssociadoService;
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
 * REST controller for managing {@link br.com.mmgestor.domain.TipoAssociado}.
 */
@RestController
@RequestMapping("/api")
public class TipoAssociadoResource {

    private final Logger log = LoggerFactory.getLogger(TipoAssociadoResource.class);

    private static final String ENTITY_NAME = "tipoAssociado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoAssociadoService tipoAssociadoService;

    public TipoAssociadoResource(TipoAssociadoService tipoAssociadoService) {
        this.tipoAssociadoService = tipoAssociadoService;
    }

    /**
     * {@code POST  /tipo-associados} : Create a new tipoAssociado.
     *
     * @param tipoAssociado the tipoAssociado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoAssociado, or with status {@code 400 (Bad Request)} if the tipoAssociado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-associados")
    public ResponseEntity<TipoAssociado> createTipoAssociado(@Valid @RequestBody TipoAssociado tipoAssociado) throws URISyntaxException {
        log.debug("REST request to save TipoAssociado : {}", tipoAssociado);
        if (tipoAssociado.getId() != null) {
            throw new BadRequestAlertException("A new tipoAssociado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoAssociado result = tipoAssociadoService.save(tipoAssociado);
        return ResponseEntity.created(new URI("/api/tipo-associados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-associados} : Updates an existing tipoAssociado.
     *
     * @param tipoAssociado the tipoAssociado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoAssociado,
     * or with status {@code 400 (Bad Request)} if the tipoAssociado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoAssociado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-associados")
    public ResponseEntity<TipoAssociado> updateTipoAssociado(@Valid @RequestBody TipoAssociado tipoAssociado) throws URISyntaxException {
        log.debug("REST request to update TipoAssociado : {}", tipoAssociado);
        if (tipoAssociado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoAssociado result = tipoAssociadoService.save(tipoAssociado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoAssociado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-associados} : get all the tipoAssociados.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoAssociados in body.
     */
    @GetMapping("/tipo-associados")
    public ResponseEntity<List<TipoAssociado>> getAllTipoAssociados(Pageable pageable) {
        log.debug("REST request to get a page of TipoAssociados");
        Page<TipoAssociado> page = tipoAssociadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-associados/:id} : get the "id" tipoAssociado.
     *
     * @param id the id of the tipoAssociado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoAssociado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-associados/{id}")
    public ResponseEntity<TipoAssociado> getTipoAssociado(@PathVariable Long id) {
        log.debug("REST request to get TipoAssociado : {}", id);
        Optional<TipoAssociado> tipoAssociado = tipoAssociadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoAssociado);
    }

    /**
     * {@code DELETE  /tipo-associados/:id} : delete the "id" tipoAssociado.
     *
     * @param id the id of the tipoAssociado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-associados/{id}")
    public ResponseEntity<Void> deleteTipoAssociado(@PathVariable Long id) {
        log.debug("REST request to delete TipoAssociado : {}", id);
        tipoAssociadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
