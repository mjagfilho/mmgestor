package br.com.mmgestor.web.rest;

import br.com.mmgestor.MmgestorApp;
import br.com.mmgestor.domain.Responsavel;
import br.com.mmgestor.domain.Endereco;
import br.com.mmgestor.domain.User;
import br.com.mmgestor.repository.ResponsavelRepository;
import br.com.mmgestor.service.ResponsavelService;
import br.com.mmgestor.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static br.com.mmgestor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ResponsavelResource} REST controller.
 */
@SpringBootTest(classes = MmgestorApp.class)
public class ResponsavelResourceIT {

    private static final String DEFAULT_CPF = "492\\523x077-43";
    private static final String UPDATED_CPF = "847u6976284-48";

    private static final String DEFAULT_NOME_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_COMPLETO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT_NASCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_NASCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private ResponsavelService responsavelService;

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

    private MockMvc restResponsavelMockMvc;

    private Responsavel responsavel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResponsavelResource responsavelResource = new ResponsavelResource(responsavelService);
        this.restResponsavelMockMvc = MockMvcBuilders.standaloneSetup(responsavelResource)
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
    public static Responsavel createEntity(EntityManager em) {
        Responsavel responsavel = new Responsavel()
            .cpf(DEFAULT_CPF)
            .nomeCompleto(DEFAULT_NOME_COMPLETO)
            .dtNascimento(DEFAULT_DT_NASCIMENTO);
        // Add required entity
        Endereco endereco;
        if (TestUtil.findAll(em, Endereco.class).isEmpty()) {
            endereco = EnderecoResourceIT.createEntity(em);
            em.persist(endereco);
            em.flush();
        } else {
            endereco = TestUtil.findAll(em, Endereco.class).get(0);
        }
        responsavel.setEndereco(endereco);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        responsavel.setUsuario(user);
        return responsavel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsavel createUpdatedEntity(EntityManager em) {
        Responsavel responsavel = new Responsavel()
            .cpf(UPDATED_CPF)
            .nomeCompleto(UPDATED_NOME_COMPLETO)
            .dtNascimento(UPDATED_DT_NASCIMENTO);
        // Add required entity
        Endereco endereco;
        if (TestUtil.findAll(em, Endereco.class).isEmpty()) {
            endereco = EnderecoResourceIT.createUpdatedEntity(em);
            em.persist(endereco);
            em.flush();
        } else {
            endereco = TestUtil.findAll(em, Endereco.class).get(0);
        }
        responsavel.setEndereco(endereco);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        responsavel.setUsuario(user);
        return responsavel;
    }

    @BeforeEach
    public void initTest() {
        responsavel = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsavel() throws Exception {
        int databaseSizeBeforeCreate = responsavelRepository.findAll().size();

        // Create the Responsavel
        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isCreated());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeCreate + 1);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testResponsavel.getNomeCompleto()).isEqualTo(DEFAULT_NOME_COMPLETO);
        assertThat(testResponsavel.getDtNascimento()).isEqualTo(DEFAULT_DT_NASCIMENTO);
    }

    @Test
    @Transactional
    public void createResponsavelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsavelRepository.findAll().size();

        // Create the Responsavel with an existing ID
        responsavel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelRepository.findAll().size();
        // set the field null
        responsavel.setCpf(null);

        // Create the Responsavel, which fails.

        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeCompletoIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelRepository.findAll().size();
        // set the field null
        responsavel.setNomeCompleto(null);

        // Create the Responsavel, which fails.

        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsavels() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList
        restResponsavelMockMvc.perform(get("/api/responsavels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavel.getId().intValue())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].nomeCompleto").value(hasItem(DEFAULT_NOME_COMPLETO)))
            .andExpect(jsonPath("$.[*].dtNascimento").value(hasItem(DEFAULT_DT_NASCIMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getResponsavel() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get the responsavel
        restResponsavelMockMvc.perform(get("/api/responsavels/{id}", responsavel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responsavel.getId().intValue()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.nomeCompleto").value(DEFAULT_NOME_COMPLETO))
            .andExpect(jsonPath("$.dtNascimento").value(DEFAULT_DT_NASCIMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResponsavel() throws Exception {
        // Get the responsavel
        restResponsavelMockMvc.perform(get("/api/responsavels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsavel() throws Exception {
        // Initialize the database
        responsavelService.save(responsavel);

        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Update the responsavel
        Responsavel updatedResponsavel = responsavelRepository.findById(responsavel.getId()).get();
        // Disconnect from session so that the updates on updatedResponsavel are not directly saved in db
        em.detach(updatedResponsavel);
        updatedResponsavel
            .cpf(UPDATED_CPF)
            .nomeCompleto(UPDATED_NOME_COMPLETO)
            .dtNascimento(UPDATED_DT_NASCIMENTO);

        restResponsavelMockMvc.perform(put("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResponsavel)))
            .andExpect(status().isOk());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testResponsavel.getNomeCompleto()).isEqualTo(UPDATED_NOME_COMPLETO);
        assertThat(testResponsavel.getDtNascimento()).isEqualTo(UPDATED_DT_NASCIMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsavel() throws Exception {
        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Create the Responsavel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelMockMvc.perform(put("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResponsavel() throws Exception {
        // Initialize the database
        responsavelService.save(responsavel);

        int databaseSizeBeforeDelete = responsavelRepository.findAll().size();

        // Delete the responsavel
        restResponsavelMockMvc.perform(delete("/api/responsavels/{id}", responsavel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
