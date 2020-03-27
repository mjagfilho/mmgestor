package br.com.mmgestor.web.rest;

import static br.com.mmgestor.web.rest.TestUtil.createFormattingConversionService;
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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import br.com.mmgestor.MmgestorApp;
import br.com.mmgestor.domain.TipoAssociado;
import br.com.mmgestor.repository.TipoAssociadoRepository;
import br.com.mmgestor.service.TipoAssociadoService;
import br.com.mmgestor.web.rest.errors.ExceptionTranslator;

/**
 * Integration tests for the {@link TipoAssociadoResource} REST controller.
 */
@SpringBootTest(classes = MmgestorApp.class)
public class TipoAssociadoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EH_ADMINISTRADOR = false;
    private static final Boolean UPDATED_EH_ADMINISTRADOR = true;

    private static final Boolean DEFAULT_EH_FINANCEIRO = false;
    private static final Boolean UPDATED_EH_FINANCEIRO = true;

    private static final Boolean DEFAULT_EH_OPERACIONAL = false;
    private static final Boolean UPDATED_EH_OPERACIONAL = true;

    private static final Boolean DEFAULT_EH_VETERINARIO = false;
    private static final Boolean UPDATED_EH_VETERINARIO = true;

    @Autowired
    private TipoAssociadoRepository tipoAssociadoRepository;

    @Autowired
    private TipoAssociadoService tipoAssociadoService;

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

    private MockMvc restTipoAssociadoMockMvc;

    private TipoAssociado tipoAssociado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoAssociadoResource tipoAssociadoResource = new TipoAssociadoResource(tipoAssociadoService);
        this.restTipoAssociadoMockMvc = MockMvcBuilders.standaloneSetup(tipoAssociadoResource)
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
    public static TipoAssociado createEntity(EntityManager em) {
        TipoAssociado tipoAssociado = new TipoAssociado()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .ehAdministrador(DEFAULT_EH_ADMINISTRADOR)
            .ehFinanceiro(DEFAULT_EH_FINANCEIRO)
            .ehOperacional(DEFAULT_EH_OPERACIONAL)
            .ehVeterinario(DEFAULT_EH_VETERINARIO);
        return tipoAssociado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoAssociado createUpdatedEntity(EntityManager em) {
        TipoAssociado tipoAssociado = new TipoAssociado()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .ehAdministrador(UPDATED_EH_ADMINISTRADOR)
            .ehFinanceiro(UPDATED_EH_FINANCEIRO)
            .ehOperacional(UPDATED_EH_OPERACIONAL)
            .ehVeterinario(UPDATED_EH_VETERINARIO);
        return tipoAssociado;
    }

    @BeforeEach
    public void initTest() {
        tipoAssociado = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoAssociado() throws Exception {
        int databaseSizeBeforeCreate = tipoAssociadoRepository.findAll().size();

        // Create the TipoAssociado
        restTipoAssociadoMockMvc.perform(post("/api/tipo-associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAssociado)))
            .andExpect(status().isCreated());

        // Validate the TipoAssociado in the database
        List<TipoAssociado> tipoAssociadoList = tipoAssociadoRepository.findAll();
        assertThat(tipoAssociadoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoAssociado testTipoAssociado = tipoAssociadoList.get(tipoAssociadoList.size() - 1);
        assertThat(testTipoAssociado.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipoAssociado.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testTipoAssociado.isEhAdministrador()).isEqualTo(DEFAULT_EH_ADMINISTRADOR);
        assertThat(testTipoAssociado.isEhFinanceiro()).isEqualTo(DEFAULT_EH_FINANCEIRO);
        assertThat(testTipoAssociado.isEhOperacional()).isEqualTo(DEFAULT_EH_OPERACIONAL);
        assertThat(testTipoAssociado.isEhVeterinario()).isEqualTo(DEFAULT_EH_VETERINARIO);
    }

    @Test
    @Transactional
    public void createTipoAssociadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoAssociadoRepository.findAll().size();

        // Create the TipoAssociado with an existing ID
        tipoAssociado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoAssociadoMockMvc.perform(post("/api/tipo-associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAssociado)))
            .andExpect(status().isBadRequest());

        // Validate the TipoAssociado in the database
        List<TipoAssociado> tipoAssociadoList = tipoAssociadoRepository.findAll();
        assertThat(tipoAssociadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAssociadoRepository.findAll().size();
        // set the field null
        tipoAssociado.setNome(null);

        // Create the TipoAssociado, which fails.

        restTipoAssociadoMockMvc.perform(post("/api/tipo-associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAssociado)))
            .andExpect(status().isBadRequest());

        List<TipoAssociado> tipoAssociadoList = tipoAssociadoRepository.findAll();
        assertThat(tipoAssociadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEhAdministradorIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAssociadoRepository.findAll().size();
        // set the field null
        tipoAssociado.setEhAdministrador(null);

        // Create the TipoAssociado, which fails.

        restTipoAssociadoMockMvc.perform(post("/api/tipo-associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAssociado)))
            .andExpect(status().isBadRequest());

        List<TipoAssociado> tipoAssociadoList = tipoAssociadoRepository.findAll();
        assertThat(tipoAssociadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEhFinanceiroIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAssociadoRepository.findAll().size();
        // set the field null
        tipoAssociado.setEhFinanceiro(null);

        // Create the TipoAssociado, which fails.

        restTipoAssociadoMockMvc.perform(post("/api/tipo-associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAssociado)))
            .andExpect(status().isBadRequest());

        List<TipoAssociado> tipoAssociadoList = tipoAssociadoRepository.findAll();
        assertThat(tipoAssociadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEhOperacionalIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAssociadoRepository.findAll().size();
        // set the field null
        tipoAssociado.setEhOperacional(null);

        // Create the TipoAssociado, which fails.

        restTipoAssociadoMockMvc.perform(post("/api/tipo-associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAssociado)))
            .andExpect(status().isBadRequest());

        List<TipoAssociado> tipoAssociadoList = tipoAssociadoRepository.findAll();
        assertThat(tipoAssociadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEhVeterinarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoAssociadoRepository.findAll().size();
        // set the field null
        tipoAssociado.setEhVeterinario(null);

        // Create the TipoAssociado, which fails.

        restTipoAssociadoMockMvc.perform(post("/api/tipo-associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAssociado)))
            .andExpect(status().isBadRequest());

        List<TipoAssociado> tipoAssociadoList = tipoAssociadoRepository.findAll();
        assertThat(tipoAssociadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoAssociados() throws Exception {
        // Initialize the database
        tipoAssociadoRepository.saveAndFlush(tipoAssociado);

        // Get all the tipoAssociadoList
        restTipoAssociadoMockMvc.perform(get("/api/tipo-associados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAssociado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ehAdministrador").value(hasItem(DEFAULT_EH_ADMINISTRADOR.booleanValue())))
            .andExpect(jsonPath("$.[*].ehFinanceiro").value(hasItem(DEFAULT_EH_FINANCEIRO.booleanValue())))
            .andExpect(jsonPath("$.[*].ehOperacional").value(hasItem(DEFAULT_EH_OPERACIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].ehVeterinario").value(hasItem(DEFAULT_EH_VETERINARIO.booleanValue())));
    }

    @Test
    @Transactional
    public void getTipoAssociado() throws Exception {
        // Initialize the database
        tipoAssociadoRepository.saveAndFlush(tipoAssociado);

        // Get the tipoAssociado
        restTipoAssociadoMockMvc.perform(get("/api/tipo-associados/{id}", tipoAssociado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id").value(tipoAssociado.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ehAdministrador").value(DEFAULT_EH_ADMINISTRADOR.booleanValue()))
            .andExpect(jsonPath("$.ehFinanceiro").value(DEFAULT_EH_FINANCEIRO.booleanValue()))
            .andExpect(jsonPath("$.ehOperacional").value(DEFAULT_EH_OPERACIONAL.booleanValue()))
            .andExpect(jsonPath("$.ehVeterinario").value(DEFAULT_EH_VETERINARIO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoAssociado() throws Exception {
        // Get the tipoAssociado
        restTipoAssociadoMockMvc.perform(get("/api/tipo-associados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoAssociado() throws Exception {
        // Initialize the database
        tipoAssociadoService.save(tipoAssociado);

        int databaseSizeBeforeUpdate = tipoAssociadoRepository.findAll().size();

        // Update the tipoAssociado
        TipoAssociado updatedTipoAssociado = tipoAssociadoRepository.findById(tipoAssociado.getId()).get();
        // Disconnect from session so that the updates on updatedTipoAssociado are not directly saved in db
        em.detach(updatedTipoAssociado);
        updatedTipoAssociado
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .ehAdministrador(UPDATED_EH_ADMINISTRADOR)
            .ehFinanceiro(UPDATED_EH_FINANCEIRO)
            .ehOperacional(UPDATED_EH_OPERACIONAL)
            .ehVeterinario(UPDATED_EH_VETERINARIO);

        restTipoAssociadoMockMvc.perform(put("/api/tipo-associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoAssociado)))
            .andExpect(status().isOk());

        // Validate the TipoAssociado in the database
        List<TipoAssociado> tipoAssociadoList = tipoAssociadoRepository.findAll();
        assertThat(tipoAssociadoList).hasSize(databaseSizeBeforeUpdate);
        TipoAssociado testTipoAssociado = tipoAssociadoList.get(tipoAssociadoList.size() - 1);
        assertThat(testTipoAssociado.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoAssociado.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testTipoAssociado.isEhAdministrador()).isEqualTo(UPDATED_EH_ADMINISTRADOR);
        assertThat(testTipoAssociado.isEhFinanceiro()).isEqualTo(UPDATED_EH_FINANCEIRO);
        assertThat(testTipoAssociado.isEhOperacional()).isEqualTo(UPDATED_EH_OPERACIONAL);
        assertThat(testTipoAssociado.isEhVeterinario()).isEqualTo(UPDATED_EH_VETERINARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoAssociado() throws Exception {
        int databaseSizeBeforeUpdate = tipoAssociadoRepository.findAll().size();

        // Create the TipoAssociado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoAssociadoMockMvc.perform(put("/api/tipo-associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAssociado)))
            .andExpect(status().isBadRequest());

        // Validate the TipoAssociado in the database
        List<TipoAssociado> tipoAssociadoList = tipoAssociadoRepository.findAll();
        assertThat(tipoAssociadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoAssociado() throws Exception {
        // Initialize the database
        tipoAssociadoService.save(tipoAssociado);

        int databaseSizeBeforeDelete = tipoAssociadoRepository.findAll().size();

        // Delete the tipoAssociado
        restTipoAssociadoMockMvc.perform(delete("/api/tipo-associados/{id}", tipoAssociado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoAssociado> tipoAssociadoList = tipoAssociadoRepository.findAll();
        assertThat(tipoAssociadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
