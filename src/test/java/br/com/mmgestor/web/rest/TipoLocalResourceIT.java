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
import br.com.mmgestor.domain.TipoLocal;
import br.com.mmgestor.repository.TipoLocalRepository;
import br.com.mmgestor.service.TipoLocalService;

/**
 * Integration tests for the {@link TipoLocalResource} REST controller.
 */
@SpringBootTest(classes = MmgestorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoLocalResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoLocalRepository tipoLocalRepository;

    @Autowired
    private TipoLocalService tipoLocalService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoLocalMockMvc;

    private TipoLocal tipoLocal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoLocal createEntity(EntityManager em) {
        TipoLocal tipoLocal = new TipoLocal()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO);
        return tipoLocal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoLocal createUpdatedEntity(EntityManager em) {
        TipoLocal tipoLocal = new TipoLocal()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        return tipoLocal;
    }

    @BeforeEach
    public void initTest() {
        tipoLocal = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoLocal() throws Exception {
        int databaseSizeBeforeCreate = tipoLocalRepository.findAll().size();
        // Create the TipoLocal
        restTipoLocalMockMvc.perform(post("/api/tipo-locals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoLocal)))
            .andExpect(status().isCreated());

        // Validate the TipoLocal in the database
        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeCreate + 1);
        TipoLocal testTipoLocal = tipoLocalList.get(tipoLocalList.size() - 1);
        assertThat(testTipoLocal.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipoLocal.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTipoLocalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoLocalRepository.findAll().size();

        // Create the TipoLocal with an existing ID
        tipoLocal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoLocalMockMvc.perform(post("/api/tipo-locals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoLocal)))
            .andExpect(status().isBadRequest());

        // Validate the TipoLocal in the database
        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoLocalRepository.findAll().size();
        // set the field null
        tipoLocal.setNome(null);

        // Create the TipoLocal, which fails.


        restTipoLocalMockMvc.perform(post("/api/tipo-locals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoLocal)))
            .andExpect(status().isBadRequest());

        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoLocals() throws Exception {
        // Initialize the database
        tipoLocalRepository.saveAndFlush(tipoLocal);

        // Get all the tipoLocalList
        restTipoLocalMockMvc.perform(get("/api/tipo-locals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoLocal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    public void getTipoLocal() throws Exception {
        // Initialize the database
        tipoLocalRepository.saveAndFlush(tipoLocal);

        // Get the tipoLocal
        restTipoLocalMockMvc.perform(get("/api/tipo-locals/{id}", tipoLocal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoLocal.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }
    @Test
    @Transactional
    public void getNonExistingTipoLocal() throws Exception {
        // Get the tipoLocal
        restTipoLocalMockMvc.perform(get("/api/tipo-locals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoLocal() throws Exception {
        // Initialize the database
        tipoLocalService.save(tipoLocal);

        int databaseSizeBeforeUpdate = tipoLocalRepository.findAll().size();

        // Update the tipoLocal
        TipoLocal updatedTipoLocal = tipoLocalRepository.findById(tipoLocal.getId()).get();
        // Disconnect from session so that the updates on updatedTipoLocal are not directly saved in db
        em.detach(updatedTipoLocal);
        updatedTipoLocal
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);

        restTipoLocalMockMvc.perform(put("/api/tipo-locals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoLocal)))
            .andExpect(status().isOk());

        // Validate the TipoLocal in the database
        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeUpdate);
        TipoLocal testTipoLocal = tipoLocalList.get(tipoLocalList.size() - 1);
        assertThat(testTipoLocal.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoLocal.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoLocal() throws Exception {
        int databaseSizeBeforeUpdate = tipoLocalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoLocalMockMvc.perform(put("/api/tipo-locals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoLocal)))
            .andExpect(status().isBadRequest());

        // Validate the TipoLocal in the database
        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoLocal() throws Exception {
        // Initialize the database
        tipoLocalService.save(tipoLocal);

        int databaseSizeBeforeDelete = tipoLocalRepository.findAll().size();

        // Delete the tipoLocal
        restTipoLocalMockMvc.perform(delete("/api/tipo-locals/{id}", tipoLocal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
