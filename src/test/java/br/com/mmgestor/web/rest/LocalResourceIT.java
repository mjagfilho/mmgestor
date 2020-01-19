package br.com.mmgestor.web.rest;

import br.com.mmgestor.MmGestorApp;
import br.com.mmgestor.domain.Local;
import br.com.mmgestor.domain.TipoLocal;
import br.com.mmgestor.domain.Endereco;
import br.com.mmgestor.repository.LocalRepository;
import br.com.mmgestor.repository.search.LocalSearchRepository;
import br.com.mmgestor.service.LocalService;
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
import java.math.BigDecimal;
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
 * Integration tests for the {@link LocalResource} REST controller.
 */
@SpringBootTest(classes = MmGestorApp.class)
public class LocalResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AREA = new BigDecimal(1);
    private static final BigDecimal UPDATED_AREA = new BigDecimal(2);

    private static final Boolean DEFAULT_EH_CONTIGUA = false;
    private static final Boolean UPDATED_EH_CONTIGUA = true;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private LocalService localService;

    /**
     * This repository is mocked in the br.com.mmgestor.repository.search test package.
     *
     * @see br.com.mmgestor.repository.search.LocalSearchRepositoryMockConfiguration
     */
    @Autowired
    private LocalSearchRepository mockLocalSearchRepository;

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

    private MockMvc restLocalMockMvc;

    private Local local;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalResource localResource = new LocalResource(localService);
        this.restLocalMockMvc = MockMvcBuilders.standaloneSetup(localResource)
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
    public static Local createEntity(EntityManager em) {
        Local local = new Local()
            .nome(DEFAULT_NOME)
            .area(DEFAULT_AREA)
            .ehContigua(DEFAULT_EH_CONTIGUA);
        // Add required entity
        TipoLocal tipoLocal;
        if (TestUtil.findAll(em, TipoLocal.class).isEmpty()) {
            tipoLocal = TipoLocalResourceIT.createEntity(em);
            em.persist(tipoLocal);
            em.flush();
        } else {
            tipoLocal = TestUtil.findAll(em, TipoLocal.class).get(0);
        }
        local.setTipo(tipoLocal);
        // Add required entity
        Endereco endereco;
        if (TestUtil.findAll(em, Endereco.class).isEmpty()) {
            endereco = EnderecoResourceIT.createEntity(em);
            em.persist(endereco);
            em.flush();
        } else {
            endereco = TestUtil.findAll(em, Endereco.class).get(0);
        }
        local.setEndereco(endereco);
        return local;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Local createUpdatedEntity(EntityManager em) {
        Local local = new Local()
            .nome(UPDATED_NOME)
            .area(UPDATED_AREA)
            .ehContigua(UPDATED_EH_CONTIGUA);
        // Add required entity
        TipoLocal tipoLocal;
        if (TestUtil.findAll(em, TipoLocal.class).isEmpty()) {
            tipoLocal = TipoLocalResourceIT.createUpdatedEntity(em);
            em.persist(tipoLocal);
            em.flush();
        } else {
            tipoLocal = TestUtil.findAll(em, TipoLocal.class).get(0);
        }
        local.setTipo(tipoLocal);
        // Add required entity
        Endereco endereco;
        if (TestUtil.findAll(em, Endereco.class).isEmpty()) {
            endereco = EnderecoResourceIT.createUpdatedEntity(em);
            em.persist(endereco);
            em.flush();
        } else {
            endereco = TestUtil.findAll(em, Endereco.class).get(0);
        }
        local.setEndereco(endereco);
        return local;
    }

    @BeforeEach
    public void initTest() {
        local = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocal() throws Exception {
        int databaseSizeBeforeCreate = localRepository.findAll().size();

        // Create the Local
        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isCreated());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeCreate + 1);
        Local testLocal = localList.get(localList.size() - 1);
        assertThat(testLocal.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testLocal.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testLocal.isEhContigua()).isEqualTo(DEFAULT_EH_CONTIGUA);

        // Validate the Local in Elasticsearch
        verify(mockLocalSearchRepository, times(1)).save(testLocal);
    }

    @Test
    @Transactional
    public void createLocalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localRepository.findAll().size();

        // Create the Local with an existing ID
        local.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeCreate);

        // Validate the Local in Elasticsearch
        verify(mockLocalSearchRepository, times(0)).save(local);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRepository.findAll().size();
        // set the field null
        local.setNome(null);

        // Create the Local, which fails.

        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRepository.findAll().size();
        // set the field null
        local.setArea(null);

        // Create the Local, which fails.

        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEhContiguaIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRepository.findAll().size();
        // set the field null
        local.setEhContigua(null);

        // Create the Local, which fails.

        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocals() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);

        // Get all the localList
        restLocalMockMvc.perform(get("/api/locals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(local.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.intValue())))
            .andExpect(jsonPath("$.[*].ehContigua").value(hasItem(DEFAULT_EH_CONTIGUA.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLocal() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);

        // Get the local
        restLocalMockMvc.perform(get("/api/locals/{id}", local.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(local.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.intValue()))
            .andExpect(jsonPath("$.ehContigua").value(DEFAULT_EH_CONTIGUA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocal() throws Exception {
        // Get the local
        restLocalMockMvc.perform(get("/api/locals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocal() throws Exception {
        // Initialize the database
        localService.save(local);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockLocalSearchRepository);

        int databaseSizeBeforeUpdate = localRepository.findAll().size();

        // Update the local
        Local updatedLocal = localRepository.findById(local.getId()).get();
        // Disconnect from session so that the updates on updatedLocal are not directly saved in db
        em.detach(updatedLocal);
        updatedLocal
            .nome(UPDATED_NOME)
            .area(UPDATED_AREA)
            .ehContigua(UPDATED_EH_CONTIGUA);

        restLocalMockMvc.perform(put("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocal)))
            .andExpect(status().isOk());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeUpdate);
        Local testLocal = localList.get(localList.size() - 1);
        assertThat(testLocal.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testLocal.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testLocal.isEhContigua()).isEqualTo(UPDATED_EH_CONTIGUA);

        // Validate the Local in Elasticsearch
        verify(mockLocalSearchRepository, times(1)).save(testLocal);
    }

    @Test
    @Transactional
    public void updateNonExistingLocal() throws Exception {
        int databaseSizeBeforeUpdate = localRepository.findAll().size();

        // Create the Local

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalMockMvc.perform(put("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Local in Elasticsearch
        verify(mockLocalSearchRepository, times(0)).save(local);
    }

    @Test
    @Transactional
    public void deleteLocal() throws Exception {
        // Initialize the database
        localService.save(local);

        int databaseSizeBeforeDelete = localRepository.findAll().size();

        // Delete the local
        restLocalMockMvc.perform(delete("/api/locals/{id}", local.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Local in Elasticsearch
        verify(mockLocalSearchRepository, times(1)).deleteById(local.getId());
    }

    @Test
    @Transactional
    public void searchLocal() throws Exception {
        // Initialize the database
        localService.save(local);
        when(mockLocalSearchRepository.search(queryStringQuery("id:" + local.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(local), PageRequest.of(0, 1), 1));
        // Search the local
        restLocalMockMvc.perform(get("/api/_search/locals?query=id:" + local.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(local.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.intValue())))
            .andExpect(jsonPath("$.[*].ehContigua").value(hasItem(DEFAULT_EH_CONTIGUA.booleanValue())));
    }
}
