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

import java.time.LocalDate;
import java.time.ZoneId;
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
import br.com.mmgestor.domain.Associado;
import br.com.mmgestor.domain.AssociadosHaras;
import br.com.mmgestor.domain.Haras;
import br.com.mmgestor.repository.AssociadosHarasRepository;
import br.com.mmgestor.service.AssociadosHarasService;

/**
 * Integration tests for the {@link AssociadosHarasResource} REST controller.
 */
@SpringBootTest(classes = MmgestorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AssociadosHarasResourceIT {

    private static final LocalDate DEFAULT_DATA_ASSOCIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ASSOCIACAO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_EH_ATIVO = false;
    private static final Boolean UPDATED_EH_ATIVO = true;

    @Autowired
    private AssociadosHarasRepository associadosHarasRepository;

    @Autowired
    private AssociadosHarasService associadosHarasService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssociadosHarasMockMvc;

    private AssociadosHaras associadosHaras;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssociadosHaras createEntity(EntityManager em) {
        AssociadosHaras associadosHaras = new AssociadosHaras()
            .dataAssociacao(DEFAULT_DATA_ASSOCIACAO)
            .ehAtivo(DEFAULT_EH_ATIVO);
        // Add required entity
        Associado associado;
        if (TestUtil.findAll(em, Associado.class).isEmpty()) {
            associado = AssociadoResourceIT.createEntity(em);
            em.persist(associado);
            em.flush();
        } else {
            associado = TestUtil.findAll(em, Associado.class).get(0);
        }
        associadosHaras.setAssociado(associado);
        // Add required entity
        Haras haras;
        if (TestUtil.findAll(em, Haras.class).isEmpty()) {
            haras = HarasResourceIT.createEntity(em);
            em.persist(haras);
            em.flush();
        } else {
            haras = TestUtil.findAll(em, Haras.class).get(0);
        }
        associadosHaras.setHaras(haras);
        return associadosHaras;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssociadosHaras createUpdatedEntity(EntityManager em) {
        AssociadosHaras associadosHaras = new AssociadosHaras()
            .dataAssociacao(UPDATED_DATA_ASSOCIACAO)
            .ehAtivo(UPDATED_EH_ATIVO);
        // Add required entity
        Associado associado;
        if (TestUtil.findAll(em, Associado.class).isEmpty()) {
            associado = AssociadoResourceIT.createUpdatedEntity(em);
            em.persist(associado);
            em.flush();
        } else {
            associado = TestUtil.findAll(em, Associado.class).get(0);
        }
        associadosHaras.setAssociado(associado);
        // Add required entity
        Haras haras;
        if (TestUtil.findAll(em, Haras.class).isEmpty()) {
            haras = HarasResourceIT.createUpdatedEntity(em);
            em.persist(haras);
            em.flush();
        } else {
            haras = TestUtil.findAll(em, Haras.class).get(0);
        }
        associadosHaras.setHaras(haras);
        return associadosHaras;
    }

    @BeforeEach
    public void initTest() {
        associadosHaras = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssociadosHaras() throws Exception {
        int databaseSizeBeforeCreate = associadosHarasRepository.findAll().size();
        // Create the AssociadosHaras
        restAssociadosHarasMockMvc.perform(post("/api/associados-haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associadosHaras)))
            .andExpect(status().isCreated());

        // Validate the AssociadosHaras in the database
        List<AssociadosHaras> associadosHarasList = associadosHarasRepository.findAll();
        assertThat(associadosHarasList).hasSize(databaseSizeBeforeCreate + 1);
        AssociadosHaras testAssociadosHaras = associadosHarasList.get(associadosHarasList.size() - 1);
        assertThat(testAssociadosHaras.getDataAssociacao()).isEqualTo(DEFAULT_DATA_ASSOCIACAO);
        assertThat(testAssociadosHaras.isEhAtivo()).isEqualTo(DEFAULT_EH_ATIVO);
    }

    @Test
    @Transactional
    public void createAssociadosHarasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = associadosHarasRepository.findAll().size();

        // Create the AssociadosHaras with an existing ID
        associadosHaras.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssociadosHarasMockMvc.perform(post("/api/associados-haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associadosHaras)))
            .andExpect(status().isBadRequest());

        // Validate the AssociadosHaras in the database
        List<AssociadosHaras> associadosHarasList = associadosHarasRepository.findAll();
        assertThat(associadosHarasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataAssociacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = associadosHarasRepository.findAll().size();
        // set the field null
        associadosHaras.setDataAssociacao(null);

        // Create the AssociadosHaras, which fails.


        restAssociadosHarasMockMvc.perform(post("/api/associados-haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associadosHaras)))
            .andExpect(status().isBadRequest());

        List<AssociadosHaras> associadosHarasList = associadosHarasRepository.findAll();
        assertThat(associadosHarasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEhAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = associadosHarasRepository.findAll().size();
        // set the field null
        associadosHaras.setEhAtivo(null);

        // Create the AssociadosHaras, which fails.


        restAssociadosHarasMockMvc.perform(post("/api/associados-haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associadosHaras)))
            .andExpect(status().isBadRequest());

        List<AssociadosHaras> associadosHarasList = associadosHarasRepository.findAll();
        assertThat(associadosHarasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssociadosHaras() throws Exception {
        // Initialize the database
        associadosHarasRepository.saveAndFlush(associadosHaras);

        // Get all the associadosHarasList
        restAssociadosHarasMockMvc.perform(get("/api/associados-haras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(associadosHaras.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAssociacao").value(hasItem(DEFAULT_DATA_ASSOCIACAO.toString())))
            .andExpect(jsonPath("$.[*].ehAtivo").value(hasItem(DEFAULT_EH_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    public void getAssociadosHaras() throws Exception {
        // Initialize the database
        associadosHarasRepository.saveAndFlush(associadosHaras);

        // Get the associadosHaras
        restAssociadosHarasMockMvc.perform(get("/api/associados-haras/{id}", associadosHaras.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(associadosHaras.getId().intValue()))
            .andExpect(jsonPath("$.dataAssociacao").value(DEFAULT_DATA_ASSOCIACAO.toString()))
            .andExpect(jsonPath("$.ehAtivo").value(DEFAULT_EH_ATIVO.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAssociadosHaras() throws Exception {
        // Get the associadosHaras
        restAssociadosHarasMockMvc.perform(get("/api/associados-haras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssociadosHaras() throws Exception {
        // Initialize the database
        associadosHarasService.save(associadosHaras);

        int databaseSizeBeforeUpdate = associadosHarasRepository.findAll().size();

        // Update the associadosHaras
        AssociadosHaras updatedAssociadosHaras = associadosHarasRepository.findById(associadosHaras.getId()).get();
        // Disconnect from session so that the updates on updatedAssociadosHaras are not directly saved in db
        em.detach(updatedAssociadosHaras);
        updatedAssociadosHaras
            .dataAssociacao(UPDATED_DATA_ASSOCIACAO)
            .ehAtivo(UPDATED_EH_ATIVO);

        restAssociadosHarasMockMvc.perform(put("/api/associados-haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssociadosHaras)))
            .andExpect(status().isOk());

        // Validate the AssociadosHaras in the database
        List<AssociadosHaras> associadosHarasList = associadosHarasRepository.findAll();
        assertThat(associadosHarasList).hasSize(databaseSizeBeforeUpdate);
        AssociadosHaras testAssociadosHaras = associadosHarasList.get(associadosHarasList.size() - 1);
        assertThat(testAssociadosHaras.getDataAssociacao()).isEqualTo(UPDATED_DATA_ASSOCIACAO);
        assertThat(testAssociadosHaras.isEhAtivo()).isEqualTo(UPDATED_EH_ATIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingAssociadosHaras() throws Exception {
        int databaseSizeBeforeUpdate = associadosHarasRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssociadosHarasMockMvc.perform(put("/api/associados-haras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(associadosHaras)))
            .andExpect(status().isBadRequest());

        // Validate the AssociadosHaras in the database
        List<AssociadosHaras> associadosHarasList = associadosHarasRepository.findAll();
        assertThat(associadosHarasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssociadosHaras() throws Exception {
        // Initialize the database
        associadosHarasService.save(associadosHaras);

        int databaseSizeBeforeDelete = associadosHarasRepository.findAll().size();

        // Delete the associadosHaras
        restAssociadosHarasMockMvc.perform(delete("/api/associados-haras/{id}", associadosHaras.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssociadosHaras> associadosHarasList = associadosHarasRepository.findAll();
        assertThat(associadosHarasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
