package br.com.mmgestor.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import br.com.mmgestor.MmgestorApp;
import br.com.mmgestor.domain.DadosAssociacao;
import br.com.mmgestor.repository.DadosAssociacaoRepository;
import br.com.mmgestor.service.DadosAssociacaoService;

/**
 * Integration tests for the {@link DadosAssociacaoResource} REST controller.
 */
@SpringBootTest(classes = MmgestorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DadosAssociacaoResourceIT {

    private static final String DEFAULT_CRIADOR = "AAAAAAAAAA";
    private static final String UPDATED_CRIADOR = "BBBBBBBBBB";

    private static final String DEFAULT_PROPRIETARIO = "AAAAAAAAAA";
    private static final String UPDATED_PROPRIETARIO = "BBBBBBBBBB";

    private static final String DEFAULT_LIVRO = "AAAAAAAAAA";
    private static final String UPDATED_LIVRO = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRO = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRO = "BBBBBBBBBB";

    private static final String DEFAULT_EXAME_DNA = "AAAAAAAAAA";
    private static final String UPDATED_EXAME_DNA = "BBBBBBBBBB";

    private static final String DEFAULT_CHIP = "AAAAAAAAAA";
    private static final String UPDATED_CHIP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EH_BLOQUEADO = false;
    private static final Boolean UPDATED_EH_BLOQUEADO = true;

    @Autowired
    private DadosAssociacaoRepository dadosAssociacaoRepository;

    @Autowired
    private DadosAssociacaoService dadosAssociacaoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDadosAssociacaoMockMvc;

    private DadosAssociacao dadosAssociacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadosAssociacao createEntity(EntityManager em) {
        DadosAssociacao dadosAssociacao = new DadosAssociacao()
            .criador(DEFAULT_CRIADOR)
            .proprietario(DEFAULT_PROPRIETARIO)
            .livro(DEFAULT_LIVRO)
            .registro(DEFAULT_REGISTRO)
            .exameDNA(DEFAULT_EXAME_DNA)
            .chip(DEFAULT_CHIP)
            .ehBloqueado(DEFAULT_EH_BLOQUEADO);
        return dadosAssociacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadosAssociacao createUpdatedEntity(EntityManager em) {
        DadosAssociacao dadosAssociacao = new DadosAssociacao()
            .criador(UPDATED_CRIADOR)
            .proprietario(UPDATED_PROPRIETARIO)
            .livro(UPDATED_LIVRO)
            .registro(UPDATED_REGISTRO)
            .exameDNA(UPDATED_EXAME_DNA)
            .chip(UPDATED_CHIP)
            .ehBloqueado(UPDATED_EH_BLOQUEADO);
        return dadosAssociacao;
    }

    @BeforeEach
    public void initTest() {
        dadosAssociacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createDadosAssociacao() throws Exception {
        int databaseSizeBeforeCreate = dadosAssociacaoRepository.findAll().size();
        // Create the DadosAssociacao
        restDadosAssociacaoMockMvc.perform(post("/api/dados-associacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dadosAssociacao)))
            .andExpect(status().isCreated());

        // Validate the DadosAssociacao in the database
        List<DadosAssociacao> dadosAssociacaoList = dadosAssociacaoRepository.findAll();
        assertThat(dadosAssociacaoList).hasSize(databaseSizeBeforeCreate + 1);
        DadosAssociacao testDadosAssociacao = dadosAssociacaoList.get(dadosAssociacaoList.size() - 1);
        assertThat(testDadosAssociacao.getCriador()).isEqualTo(DEFAULT_CRIADOR);
        assertThat(testDadosAssociacao.getProprietario()).isEqualTo(DEFAULT_PROPRIETARIO);
        assertThat(testDadosAssociacao.getLivro()).isEqualTo(DEFAULT_LIVRO);
        assertThat(testDadosAssociacao.getRegistro()).isEqualTo(DEFAULT_REGISTRO);
        assertThat(testDadosAssociacao.getExameDNA()).isEqualTo(DEFAULT_EXAME_DNA);
        assertThat(testDadosAssociacao.getChip()).isEqualTo(DEFAULT_CHIP);
        assertThat(testDadosAssociacao.isEhBloqueado()).isEqualTo(DEFAULT_EH_BLOQUEADO);
    }

    @Test
    @Transactional
    public void createDadosAssociacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dadosAssociacaoRepository.findAll().size();

        // Create the DadosAssociacao with an existing ID
        dadosAssociacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDadosAssociacaoMockMvc.perform(post("/api/dados-associacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dadosAssociacao)))
            .andExpect(status().isBadRequest());

        // Validate the DadosAssociacao in the database
        List<DadosAssociacao> dadosAssociacaoList = dadosAssociacaoRepository.findAll();
        assertThat(dadosAssociacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDadosAssociacaos() throws Exception {
        // Initialize the database
        dadosAssociacaoRepository.saveAndFlush(dadosAssociacao);

        // Get all the dadosAssociacaoList
        restDadosAssociacaoMockMvc.perform(get("/api/dados-associacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dadosAssociacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].criador").value(hasItem(DEFAULT_CRIADOR)))
            .andExpect(jsonPath("$.[*].proprietario").value(hasItem(DEFAULT_PROPRIETARIO)))
            .andExpect(jsonPath("$.[*].livro").value(hasItem(DEFAULT_LIVRO)))
            .andExpect(jsonPath("$.[*].registro").value(hasItem(DEFAULT_REGISTRO)))
            .andExpect(jsonPath("$.[*].exameDNA").value(hasItem(DEFAULT_EXAME_DNA)))
            .andExpect(jsonPath("$.[*].chip").value(hasItem(DEFAULT_CHIP)))
            .andExpect(jsonPath("$.[*].ehBloqueado").value(hasItem(DEFAULT_EH_BLOQUEADO.booleanValue())));
    }

    @Test
    @Transactional
    public void getDadosAssociacao() throws Exception {
        // Initialize the database
        dadosAssociacaoRepository.saveAndFlush(dadosAssociacao);

        // Get the dadosAssociacao
        restDadosAssociacaoMockMvc.perform(get("/api/dados-associacaos/{id}", dadosAssociacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dadosAssociacao.getId().intValue()))
            .andExpect(jsonPath("$.criador").value(DEFAULT_CRIADOR))
            .andExpect(jsonPath("$.proprietario").value(DEFAULT_PROPRIETARIO))
            .andExpect(jsonPath("$.livro").value(DEFAULT_LIVRO))
            .andExpect(jsonPath("$.registro").value(DEFAULT_REGISTRO))
            .andExpect(jsonPath("$.exameDNA").value(DEFAULT_EXAME_DNA))
            .andExpect(jsonPath("$.chip").value(DEFAULT_CHIP))
            .andExpect(jsonPath("$.ehBloqueado").value(DEFAULT_EH_BLOQUEADO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDadosAssociacao() throws Exception {
        // Get the dadosAssociacao
        restDadosAssociacaoMockMvc.perform(get("/api/dados-associacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDadosAssociacao() throws Exception {
        // Initialize the database
        dadosAssociacaoService.save(dadosAssociacao);

        int databaseSizeBeforeUpdate = dadosAssociacaoRepository.findAll().size();

        // Update the dadosAssociacao
        DadosAssociacao updatedDadosAssociacao = dadosAssociacaoRepository.findById(dadosAssociacao.getId()).get();
        // Disconnect from session so that the updates on updatedDadosAssociacao are not directly saved in db
        em.detach(updatedDadosAssociacao);
        updatedDadosAssociacao
            .criador(UPDATED_CRIADOR)
            .proprietario(UPDATED_PROPRIETARIO)
            .livro(UPDATED_LIVRO)
            .registro(UPDATED_REGISTRO)
            .exameDNA(UPDATED_EXAME_DNA)
            .chip(UPDATED_CHIP)
            .ehBloqueado(UPDATED_EH_BLOQUEADO);

        restDadosAssociacaoMockMvc.perform(put("/api/dados-associacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDadosAssociacao)))
            .andExpect(status().isOk());

        // Validate the DadosAssociacao in the database
        List<DadosAssociacao> dadosAssociacaoList = dadosAssociacaoRepository.findAll();
        assertThat(dadosAssociacaoList).hasSize(databaseSizeBeforeUpdate);
        DadosAssociacao testDadosAssociacao = dadosAssociacaoList.get(dadosAssociacaoList.size() - 1);
        assertThat(testDadosAssociacao.getCriador()).isEqualTo(UPDATED_CRIADOR);
        assertThat(testDadosAssociacao.getProprietario()).isEqualTo(UPDATED_PROPRIETARIO);
        assertThat(testDadosAssociacao.getLivro()).isEqualTo(UPDATED_LIVRO);
        assertThat(testDadosAssociacao.getRegistro()).isEqualTo(UPDATED_REGISTRO);
        assertThat(testDadosAssociacao.getExameDNA()).isEqualTo(UPDATED_EXAME_DNA);
        assertThat(testDadosAssociacao.getChip()).isEqualTo(UPDATED_CHIP);
        assertThat(testDadosAssociacao.isEhBloqueado()).isEqualTo(UPDATED_EH_BLOQUEADO);
    }

    @Test
    @Transactional
    public void updateNonExistingDadosAssociacao() throws Exception {
        int databaseSizeBeforeUpdate = dadosAssociacaoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDadosAssociacaoMockMvc.perform(put("/api/dados-associacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dadosAssociacao)))
            .andExpect(status().isBadRequest());

        // Validate the DadosAssociacao in the database
        List<DadosAssociacao> dadosAssociacaoList = dadosAssociacaoRepository.findAll();
        assertThat(dadosAssociacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDadosAssociacao() throws Exception {
        // Initialize the database
        dadosAssociacaoService.save(dadosAssociacao);

        int databaseSizeBeforeDelete = dadosAssociacaoRepository.findAll().size();

        // Delete the dadosAssociacao
        restDadosAssociacaoMockMvc.perform(delete("/api/dados-associacaos/{id}", dadosAssociacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DadosAssociacao> dadosAssociacaoList = dadosAssociacaoRepository.findAll();
        assertThat(dadosAssociacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
