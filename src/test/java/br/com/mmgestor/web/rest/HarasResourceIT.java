package br.com.mmgestor.web.rest;

import br.com.mmgestor.MmgestorApp;
import br.com.mmgestor.domain.Haras;
import br.com.mmgestor.domain.Associado;
import br.com.mmgestor.repository.HarasRepository;
import br.com.mmgestor.service.HarasService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HarasResource} REST controller.
 */
@SpringBootTest(classes = MmgestorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HarasResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_UF = "AA";
    private static final String UPDATED_UF = "BB";

    @Autowired
    private HarasRepository harasRepository;

    @Autowired
    private HarasService harasService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHarasMockMvc;

    private Haras haras;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Haras createEntity(EntityManager em) {
        Haras haras = new Haras()
            .nome(DEFAULT_NOME)
            .localidade(DEFAULT_LOCALIDADE)
            .uf(DEFAULT_UF);
        // Add required entity
        Associado associado;
        if (TestUtil.findAll(em, Associado.class).isEmpty()) {
            associado = AssociadoResourceIT.createEntity(em);
            em.persist(associado);
            em.flush();
        } else {
            associado = TestUtil.findAll(em, Associado.class).get(0);
        }
        haras.setResponsavel(associado);
        return haras;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Haras createUpdatedEntity(EntityManager em) {
        Haras haras = new Haras()
            .nome(UPDATED_NOME)
            .localidade(UPDATED_LOCALIDADE)
            .uf(UPDATED_UF);
        // Add required entity
        Associado associado;
        if (TestUtil.findAll(em, Associado.class).isEmpty()) {
            associado = AssociadoResourceIT.createUpdatedEntity(em);
            em.persist(associado);
            em.flush();
        } else {
            associado = TestUtil.findAll(em, Associado.class).get(0);
        }
        haras.setResponsavel(associado);
        return haras;
    }

    @BeforeEach
    public void initTest() {
        haras = createEntity(em);
    }

    @Test
    @Transactional
    public void createHaras() throws Exception {
        int databaseSizeBeforeCreate = harasRepository.findAll().size();
        // Create the Haras
        restHarasMockMvc.perform(post("/api/haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(haras)))
            .andExpect(status().isCreated());

        // Validate the Haras in the database
        List<Haras> harasList = harasRepository.findAll();
        assertThat(harasList).hasSize(databaseSizeBeforeCreate + 1);
        Haras testHaras = harasList.get(harasList.size() - 1);
        assertThat(testHaras.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testHaras.getLocalidade()).isEqualTo(DEFAULT_LOCALIDADE);
        assertThat(testHaras.getUf()).isEqualTo(DEFAULT_UF);
    }

    @Test
    @Transactional
    public void createHarasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = harasRepository.findAll().size();

        // Create the Haras with an existing ID
        haras.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHarasMockMvc.perform(post("/api/haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(haras)))
            .andExpect(status().isBadRequest());

        // Validate the Haras in the database
        List<Haras> harasList = harasRepository.findAll();
        assertThat(harasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = harasRepository.findAll().size();
        // set the field null
        haras.setNome(null);

        // Create the Haras, which fails.


        restHarasMockMvc.perform(post("/api/haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(haras)))
            .andExpect(status().isBadRequest());

        List<Haras> harasList = harasRepository.findAll();
        assertThat(harasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = harasRepository.findAll().size();
        // set the field null
        haras.setLocalidade(null);

        // Create the Haras, which fails.


        restHarasMockMvc.perform(post("/api/haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(haras)))
            .andExpect(status().isBadRequest());

        List<Haras> harasList = harasRepository.findAll();
        assertThat(harasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUfIsRequired() throws Exception {
        int databaseSizeBeforeTest = harasRepository.findAll().size();
        // set the field null
        haras.setUf(null);

        // Create the Haras, which fails.


        restHarasMockMvc.perform(post("/api/haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(haras)))
            .andExpect(status().isBadRequest());

        List<Haras> harasList = harasRepository.findAll();
        assertThat(harasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHaras() throws Exception {
        // Initialize the database
        harasRepository.saveAndFlush(haras);

        // Get all the harasList
        restHarasMockMvc.perform(get("/api/haras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(haras.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].localidade").value(hasItem(DEFAULT_LOCALIDADE)))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF)));
    }
    
    @Test
    @Transactional
    public void getHaras() throws Exception {
        // Initialize the database
        harasRepository.saveAndFlush(haras);

        // Get the haras
        restHarasMockMvc.perform(get("/api/haras/{id}", haras.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(haras.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.localidade").value(DEFAULT_LOCALIDADE))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF));
    }
    @Test
    @Transactional
    public void getNonExistingHaras() throws Exception {
        // Get the haras
        restHarasMockMvc.perform(get("/api/haras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHaras() throws Exception {
        // Initialize the database
        harasService.save(haras);

        int databaseSizeBeforeUpdate = harasRepository.findAll().size();

        // Update the haras
        Haras updatedHaras = harasRepository.findById(haras.getId()).get();
        // Disconnect from session so that the updates on updatedHaras are not directly saved in db
        em.detach(updatedHaras);
        updatedHaras
            .nome(UPDATED_NOME)
            .localidade(UPDATED_LOCALIDADE)
            .uf(UPDATED_UF);

        restHarasMockMvc.perform(put("/api/haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHaras)))
            .andExpect(status().isOk());

        // Validate the Haras in the database
        List<Haras> harasList = harasRepository.findAll();
        assertThat(harasList).hasSize(databaseSizeBeforeUpdate);
        Haras testHaras = harasList.get(harasList.size() - 1);
        assertThat(testHaras.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testHaras.getLocalidade()).isEqualTo(UPDATED_LOCALIDADE);
        assertThat(testHaras.getUf()).isEqualTo(UPDATED_UF);
    }

    @Test
    @Transactional
    public void updateNonExistingHaras() throws Exception {
        int databaseSizeBeforeUpdate = harasRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHarasMockMvc.perform(put("/api/haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(haras)))
            .andExpect(status().isBadRequest());

        // Validate the Haras in the database
        List<Haras> harasList = harasRepository.findAll();
        assertThat(harasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHaras() throws Exception {
        // Initialize the database
        harasService.save(haras);

        int databaseSizeBeforeDelete = harasRepository.findAll().size();

        // Delete the haras
        restHarasMockMvc.perform(delete("/api/haras/{id}", haras.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Haras> harasList = harasRepository.findAll();
        assertThat(harasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
