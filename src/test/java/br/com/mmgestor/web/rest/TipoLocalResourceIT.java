package br.com.mmgestor.web.rest;

import br.com.mmgestor.MmGestorApp;
import br.com.mmgestor.domain.TipoLocal;
import br.com.mmgestor.repository.TipoLocalRepository;
import br.com.mmgestor.repository.search.TipoLocalSearchRepository;
import br.com.mmgestor.service.TipoLocalService;
import br.com.mmgestor.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static br.com.mmgestor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoLocalResource} REST controller.
 */
@SpringBootTest(classes = MmGestorApp.class)
public class TipoLocalResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoLocalRepository tipoLocalRepository;

    @Autowired
    private TipoLocalService tipoLocalService;

    /**
     * This repository is mocked in the br.com.mmgestor.repository.search test package.
     *
     * @see br.com.mmgestor.repository.search.TipoLocalSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoLocalSearchRepository mockTipoLocalSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTipoLocalMockMvc;

    private TipoLocal tipoLocal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoLocalResource tipoLocalResource = new TipoLocalResource(tipoLocalService);
        this.restTipoLocalMockMvc = MockMvcBuilders.standaloneSetup(tipoLocalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

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
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoLocal)))
            .andExpect(status().isCreated());

        // Validate the TipoLocal in the database
        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeCreate + 1);
        TipoLocal testTipoLocal = tipoLocalList.get(tipoLocalList.size() - 1);
        assertThat(testTipoLocal.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipoLocal.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);

        // Validate the TipoLocal in Elasticsearch
        verify(mockTipoLocalSearchRepository, times(1)).save(testTipoLocal);
    }

    @Test
    @Transactional
    public void createTipoLocalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoLocalRepository.findAll().size();

        // Create the TipoLocal with an existing ID
        tipoLocal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoLocalMockMvc.perform(post("/api/tipo-locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoLocal)))
            .andExpect(status().isBadRequest());

        // Validate the TipoLocal in the database
        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoLocal in Elasticsearch
        verify(mockTipoLocalSearchRepository, times(0)).save(tipoLocal);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoLocalRepository.findAll().size();
        // set the field null
        tipoLocal.setNome(null);

        // Create the TipoLocal, which fails.

        restTipoLocalMockMvc.perform(post("/api/tipo-locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
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
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTipoLocalSearchRepository);

        int databaseSizeBeforeUpdate = tipoLocalRepository.findAll().size();

        // Update the tipoLocal
        TipoLocal updatedTipoLocal = tipoLocalRepository.findById(tipoLocal.getId()).get();
        // Disconnect from session so that the updates on updatedTipoLocal are not directly saved in db
        em.detach(updatedTipoLocal);
        updatedTipoLocal
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);

        restTipoLocalMockMvc.perform(put("/api/tipo-locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoLocal)))
            .andExpect(status().isOk());

        // Validate the TipoLocal in the database
        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeUpdate);
        TipoLocal testTipoLocal = tipoLocalList.get(tipoLocalList.size() - 1);
        assertThat(testTipoLocal.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoLocal.getDescricao()).isEqualTo(UPDATED_DESCRICAO);

        // Validate the TipoLocal in Elasticsearch
        verify(mockTipoLocalSearchRepository, times(1)).save(testTipoLocal);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoLocal() throws Exception {
        int databaseSizeBeforeUpdate = tipoLocalRepository.findAll().size();

        // Create the TipoLocal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoLocalMockMvc.perform(put("/api/tipo-locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoLocal)))
            .andExpect(status().isBadRequest());

        // Validate the TipoLocal in the database
        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoLocal in Elasticsearch
        verify(mockTipoLocalSearchRepository, times(0)).save(tipoLocal);
    }

    @Test
    @Transactional
    public void deleteTipoLocal() throws Exception {
        // Initialize the database
        tipoLocalService.save(tipoLocal);

        int databaseSizeBeforeDelete = tipoLocalRepository.findAll().size();

        // Delete the tipoLocal
        restTipoLocalMockMvc.perform(delete("/api/tipo-locals/{id}", tipoLocal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoLocal> tipoLocalList = tipoLocalRepository.findAll();
        assertThat(tipoLocalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoLocal in Elasticsearch
        verify(mockTipoLocalSearchRepository, times(1)).deleteById(tipoLocal.getId());
    }

    @Test
    @Transactional
    public void searchTipoLocal() throws Exception {
        // Initialize the database
        tipoLocalService.save(tipoLocal);
        when(mockTipoLocalSearchRepository.search(queryStringQuery("id:" + tipoLocal.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoLocal), PageRequest.of(0, 1), 1));
        // Search the tipoLocal
        restTipoLocalMockMvc.perform(get("/api/_search/tipo-locals?query=id:" + tipoLocal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoLocal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
}
