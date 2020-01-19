package br.com.mmgestor.web.rest;

import br.com.mmgestor.domain.DadosAssociacao;
import br.com.mmgestor.service.DadosAssociacaoService;
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
 * REST controller for managing {@link br.com.mmgestor.domain.DadosAssociacao}.
 */
@RestController
@RequestMapping("/api")
public class DadosAssociacaoResource {

    private final Logger log = LoggerFactory.getLogger(DadosAssociacaoResource.class);

    private static final String ENTITY_NAME = "dadosAssociacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DadosAssociacaoService dadosAssociacaoService;

    public DadosAssociacaoResource(DadosAssociacaoService dadosAssociacaoService) {
        this.dadosAssociacaoService = dadosAssociacaoService;
    }

    /**
     * {@code POST  /dados-associacaos} : Create a new dadosAssociacao.
     *
     * @param dadosAssociacao the dadosAssociacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dadosAssociacao, or with status {@code 400 (Bad Request)} if the dadosAssociacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dados-associacaos")
    public ResponseEntity<DadosAssociacao> createDadosAssociacao(@Valid @RequestBody DadosAssociacao dadosAssociacao) throws URISyntaxException {
        log.debug("REST request to save DadosAssociacao : {}", dadosAssociacao);
        if (dadosAssociacao.getId() != null) {
            throw new BadRequestAlertException("A new dadosAssociacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DadosAssociacao result = dadosAssociacaoService.save(dadosAssociacao);
        return ResponseEntity.created(new URI("/api/dados-associacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dados-associacaos} : Updates an existing dadosAssociacao.
     *
     * @param dadosAssociacao the dadosAssociacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dadosAssociacao,
     * or with status {@code 400 (Bad Request)} if the dadosAssociacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dadosAssociacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dados-associacaos")
    public ResponseEntity<DadosAssociacao> updateDadosAssociacao(@Valid @RequestBody DadosAssociacao dadosAssociacao) throws URISyntaxException {
        log.debug("REST request to update DadosAssociacao : {}", dadosAssociacao);
        if (dadosAssociacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DadosAssociacao result = dadosAssociacaoService.save(dadosAssociacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dadosAssociacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dados-associacaos} : get all the dadosAssociacaos.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dadosAssociacaos in body.
     */
    @GetMapping("/dados-associacaos")
    public ResponseEntity<List<DadosAssociacao>> getAllDadosAssociacaos(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("animal-is-null".equals(filter)) {
            log.debug("REST request to get all DadosAssociacaos where animal is null");
            return new ResponseEntity<>(dadosAssociacaoService.findAllWhereAnimalIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of DadosAssociacaos");
        Page<DadosAssociacao> page = dadosAssociacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dados-associacaos/:id} : get the "id" dadosAssociacao.
     *
     * @param id the id of the dadosAssociacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dadosAssociacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dados-associacaos/{id}")
    public ResponseEntity<DadosAssociacao> getDadosAssociacao(@PathVariable Long id) {
        log.debug("REST request to get DadosAssociacao : {}", id);
        Optional<DadosAssociacao> dadosAssociacao = dadosAssociacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dadosAssociacao);
    }

    /**
     * {@code DELETE  /dados-associacaos/:id} : delete the "id" dadosAssociacao.
     *
     * @param id the id of the dadosAssociacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dados-associacaos/{id}")
    public ResponseEntity<Void> deleteDadosAssociacao(@PathVariable Long id) {
        log.debug("REST request to delete DadosAssociacao : {}", id);
        dadosAssociacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/dados-associacaos?query=:query} : search for the dadosAssociacao corresponding
     * to the query.
     *
     * @param query the query of the dadosAssociacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dados-associacaos")
    public ResponseEntity<List<DadosAssociacao>> searchDadosAssociacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DadosAssociacaos for query {}", query);
        Page<DadosAssociacao> page = dadosAssociacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
