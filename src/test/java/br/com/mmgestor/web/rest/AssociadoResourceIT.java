package br.com.mmgestor.web.rest;

import br.com.mmgestor.MmgestorApp;
import br.com.mmgestor.domain.Associado;
import br.com.mmgestor.domain.TipoAssociado;
import br.com.mmgestor.domain.User;
import br.com.mmgestor.repository.AssociadoRepository;
import br.com.mmgestor.service.AssociadoService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static br.com.mmgestor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AssociadoResource} REST controller.
 */
@SpringBootTest(classes = MmgestorApp.class)
public class AssociadoResourceIT {

    private static final LocalDate DEFAULT_DT_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CEP = "53787-600";
    private static final String UPDATED_CEP = "64567-280";

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_UF = "AA";
    private static final String UPDATED_UF = "BB";

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private AssociadoService associadoService;

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

    private MockMvc restAssociadoMockMvc;

    private Associado associado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssociadoResource associadoResource = new AssociadoResource(associadoService);
        this.restAssociadoMockMvc = MockMvcBuilders.standaloneSetup(associadoResource)
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
    public static Associado createEntity(EntityManager em) {
        Associado associado = new Associado()
            .dtNascimento(DEFAULT_DT_NASCIMENTO)
            .cep(DEFAULT_CEP)
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .bairro(DEFAULT_BAIRRO)
            .localidade(DEFAULT_LOCALIDADE)
            .uf(DEFAULT_UF);
        // Add required entity
        TipoAssociado tipoAssociado;
        if (TestUtil.findAll(em, TipoAssociado.class).isEmpty()) {
            tipoAssociado = TipoAssociadoResourceIT.createEntity(em);
            em.persist(tipoAssociado);
            em.flush();
        } else {
            tipoAssociado = TestUtil.findAll(em, TipoAssociado.class).get(0);
        }
        associado.setTipo(tipoAssociado);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        associado.setUsuario(user);
        return associado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Associado createUpdatedEntity(EntityManager em) {
        Associado associado = new Associado()
            .dtNascimento(UPDATED_DT_NASCIMENTO)
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .localidade(UPDATED_LOCALIDADE)
            .uf(UPDATED_UF);
        // Add required entity
        TipoAssociado tipoAssociado;
        if (TestUtil.findAll(em, TipoAssociado.class).isEmpty()) {
            tipoAssociado = TipoAssociadoResourceIT.createUpdatedEntity(em);
            em.persist(tipoAssociado);
            em.flush();
        } else {
            tipoAssociado = TestUtil.findAll(em, TipoAssociado.class).get(0);
        }
        associado.setTipo(tipoAssociado);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        associado.setUsuario(user);
        return associado;
    }

    @BeforeEach
    public void initTest() {
        associado = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssociado() throws Exception {
        int databaseSizeBeforeCreate = associadoRepository.findAll().size();

        // Create the Associado
        restAssociadoMockMvc.perform(post("/api/associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associado)))
            .andExpect(status().isCreated());

        // Validate the Associado in the database
        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeCreate + 1);
        Associado testAssociado = associadoList.get(associadoList.size() - 1);
        assertThat(testAssociado.getDtNascimento()).isEqualTo(DEFAULT_DT_NASCIMENTO);
        assertThat(testAssociado.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testAssociado.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testAssociado.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAssociado.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testAssociado.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testAssociado.getLocalidade()).isEqualTo(DEFAULT_LOCALIDADE);
        assertThat(testAssociado.getUf()).isEqualTo(DEFAULT_UF);
    }

    @Test
    @Transactional
    public void createAssociadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = associadoRepository.findAll().size();

        // Create the Associado with an existing ID
        associado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssociadoMockMvc.perform(post("/api/associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associado)))
            .andExpect(status().isBadRequest());

        // Validate the Associado in the database
        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = associadoRepository.findAll().size();
        // set the field null
        associado.setCep(null);

        // Create the Associado, which fails.

        restAssociadoMockMvc.perform(post("/api/associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associado)))
            .andExpect(status().isBadRequest());

        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = associadoRepository.findAll().size();
        // set the field null
        associado.setLogradouro(null);

        // Create the Associado, which fails.

        restAssociadoMockMvc.perform(post("/api/associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associado)))
            .andExpect(status().isBadRequest());

        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = associadoRepository.findAll().size();
        // set the field null
        associado.setNumero(null);

        // Create the Associado, which fails.

        restAssociadoMockMvc.perform(post("/api/associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associado)))
            .andExpect(status().isBadRequest());

        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = associadoRepository.findAll().size();
        // set the field null
        associado.setBairro(null);

        // Create the Associado, which fails.

        restAssociadoMockMvc.perform(post("/api/associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associado)))
            .andExpect(status().isBadRequest());

        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = associadoRepository.findAll().size();
        // set the field null
        associado.setLocalidade(null);

        // Create the Associado, which fails.

        restAssociadoMockMvc.perform(post("/api/associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associado)))
            .andExpect(status().isBadRequest());

        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUfIsRequired() throws Exception {
        int databaseSizeBeforeTest = associadoRepository.findAll().size();
        // set the field null
        associado.setUf(null);

        // Create the Associado, which fails.

        restAssociadoMockMvc.perform(post("/api/associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associado)))
            .andExpect(status().isBadRequest());

        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssociados() throws Exception {
        // Initialize the database
        associadoRepository.saveAndFlush(associado);

        // Get all the associadoList
        restAssociadoMockMvc.perform(get("/api/associados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(associado.getId().intValue())))
            .andExpect(jsonPath("$.[*].dtNascimento").value(hasItem(DEFAULT_DT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].localidade").value(hasItem(DEFAULT_LOCALIDADE)))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF)));
    }

    @Test
    @Transactional
    public void getAssociado() throws Exception {
        // Initialize the database
        associadoRepository.saveAndFlush(associado);

        // Get the associado
        restAssociadoMockMvc.perform(get("/api/associados/{id}", associado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(associado.getId().intValue()))
            .andExpect(jsonPath("$.dtNascimento").value(DEFAULT_DT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.localidade").value(DEFAULT_LOCALIDADE))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF));
    }

    @Test
    @Transactional
    public void getNonExistingAssociado() throws Exception {
        // Get the associado
        restAssociadoMockMvc.perform(get("/api/associados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssociado() throws Exception {
        // Initialize the database
        associadoService.save(associado);

        int databaseSizeBeforeUpdate = associadoRepository.findAll().size();

        // Update the associado
        Associado updatedAssociado = associadoRepository.findById(associado.getId()).get();
        // Disconnect from session so that the updates on updatedAssociado are not directly saved in db
        em.detach(updatedAssociado);
        updatedAssociado
            .dtNascimento(UPDATED_DT_NASCIMENTO)
            .cep(UPDATED_CEP)
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .localidade(UPDATED_LOCALIDADE)
            .uf(UPDATED_UF);

        restAssociadoMockMvc.perform(put("/api/associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssociado)))
            .andExpect(status().isOk());

        // Validate the Associado in the database
        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeUpdate);
        Associado testAssociado = associadoList.get(associadoList.size() - 1);
        assertThat(testAssociado.getDtNascimento()).isEqualTo(UPDATED_DT_NASCIMENTO);
        assertThat(testAssociado.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testAssociado.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testAssociado.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAssociado.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testAssociado.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testAssociado.getLocalidade()).isEqualTo(UPDATED_LOCALIDADE);
        assertThat(testAssociado.getUf()).isEqualTo(UPDATED_UF);
    }

    @Test
    @Transactional
    public void updateNonExistingAssociado() throws Exception {
        int databaseSizeBeforeUpdate = associadoRepository.findAll().size();

        // Create the Associado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssociadoMockMvc.perform(put("/api/associados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associado)))
            .andExpect(status().isBadRequest());

        // Validate the Associado in the database
        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssociado() throws Exception {
        // Initialize the database
        associadoService.save(associado);

        int databaseSizeBeforeDelete = associadoRepository.findAll().size();

        // Delete the associado
        restAssociadoMockMvc.perform(delete("/api/associados/{id}", associado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Associado> associadoList = associadoRepository.findAll();
        assertThat(associadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
