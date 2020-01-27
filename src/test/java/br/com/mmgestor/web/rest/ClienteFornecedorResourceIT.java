package br.com.mmgestor.web.rest;

import br.com.mmgestor.MmgestorApp;
import br.com.mmgestor.domain.ClienteFornecedor;
import br.com.mmgestor.domain.Endereco;
import br.com.mmgestor.repository.ClienteFornecedorRepository;
import br.com.mmgestor.service.ClienteFornecedorService;
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
 * Integration tests for the {@link ClienteFornecedorResource} REST controller.
 */
@SpringBootTest(classes = MmgestorApp.class)
public class ClienteFornecedorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT_NASCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_NASCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CPF = "513!386z028-98";
    private static final String UPDATED_CPF = "989c503e404-05";

    private static final String DEFAULT_NOME_HARAS = "AAAAAAAAAA";
    private static final String UPDATED_NOME_HARAS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIDADE_HARAS = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIDADE_HARAS = "BBBBBBBBBB";

    private static final String DEFAULT_UF_HARAS = "AA";
    private static final String UPDATED_UF_HARAS = "BB";

    @Autowired
    private ClienteFornecedorRepository clienteFornecedorRepository;

    @Autowired
    private ClienteFornecedorService clienteFornecedorService;

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

    private MockMvc restClienteFornecedorMockMvc;

    private ClienteFornecedor clienteFornecedor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteFornecedorResource clienteFornecedorResource = new ClienteFornecedorResource(clienteFornecedorService);
        this.restClienteFornecedorMockMvc = MockMvcBuilders.standaloneSetup(clienteFornecedorResource)
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
    public static ClienteFornecedor createEntity(EntityManager em) {
        ClienteFornecedor clienteFornecedor = new ClienteFornecedor()
            .nome(DEFAULT_NOME)
            .dtNascimento(DEFAULT_DT_NASCIMENTO)
            .cpf(DEFAULT_CPF)
            .nomeHaras(DEFAULT_NOME_HARAS)
            .localidadeHaras(DEFAULT_LOCALIDADE_HARAS)
            .ufHaras(DEFAULT_UF_HARAS);
        // Add required entity
        Endereco endereco;
        if (TestUtil.findAll(em, Endereco.class).isEmpty()) {
            endereco = EnderecoResourceIT.createEntity(em);
            em.persist(endereco);
            em.flush();
        } else {
            endereco = TestUtil.findAll(em, Endereco.class).get(0);
        }
        clienteFornecedor.setEndereco(endereco);
        return clienteFornecedor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClienteFornecedor createUpdatedEntity(EntityManager em) {
        ClienteFornecedor clienteFornecedor = new ClienteFornecedor()
            .nome(UPDATED_NOME)
            .dtNascimento(UPDATED_DT_NASCIMENTO)
            .cpf(UPDATED_CPF)
            .nomeHaras(UPDATED_NOME_HARAS)
            .localidadeHaras(UPDATED_LOCALIDADE_HARAS)
            .ufHaras(UPDATED_UF_HARAS);
        // Add required entity
        Endereco endereco;
        if (TestUtil.findAll(em, Endereco.class).isEmpty()) {
            endereco = EnderecoResourceIT.createUpdatedEntity(em);
            em.persist(endereco);
            em.flush();
        } else {
            endereco = TestUtil.findAll(em, Endereco.class).get(0);
        }
        clienteFornecedor.setEndereco(endereco);
        return clienteFornecedor;
    }

    @BeforeEach
    public void initTest() {
        clienteFornecedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createClienteFornecedor() throws Exception {
        int databaseSizeBeforeCreate = clienteFornecedorRepository.findAll().size();

        // Create the ClienteFornecedor
        restClienteFornecedorMockMvc.perform(post("/api/cliente-fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteFornecedor)))
            .andExpect(status().isCreated());

        // Validate the ClienteFornecedor in the database
        List<ClienteFornecedor> clienteFornecedorList = clienteFornecedorRepository.findAll();
        assertThat(clienteFornecedorList).hasSize(databaseSizeBeforeCreate + 1);
        ClienteFornecedor testClienteFornecedor = clienteFornecedorList.get(clienteFornecedorList.size() - 1);
        assertThat(testClienteFornecedor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testClienteFornecedor.getDtNascimento()).isEqualTo(DEFAULT_DT_NASCIMENTO);
        assertThat(testClienteFornecedor.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testClienteFornecedor.getNomeHaras()).isEqualTo(DEFAULT_NOME_HARAS);
        assertThat(testClienteFornecedor.getLocalidadeHaras()).isEqualTo(DEFAULT_LOCALIDADE_HARAS);
        assertThat(testClienteFornecedor.getUfHaras()).isEqualTo(DEFAULT_UF_HARAS);
    }

    @Test
    @Transactional
    public void createClienteFornecedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteFornecedorRepository.findAll().size();

        // Create the ClienteFornecedor with an existing ID
        clienteFornecedor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteFornecedorMockMvc.perform(post("/api/cliente-fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteFornecedor)))
            .andExpect(status().isBadRequest());

        // Validate the ClienteFornecedor in the database
        List<ClienteFornecedor> clienteFornecedorList = clienteFornecedorRepository.findAll();
        assertThat(clienteFornecedorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteFornecedorRepository.findAll().size();
        // set the field null
        clienteFornecedor.setNome(null);

        // Create the ClienteFornecedor, which fails.

        restClienteFornecedorMockMvc.perform(post("/api/cliente-fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteFornecedor)))
            .andExpect(status().isBadRequest());

        List<ClienteFornecedor> clienteFornecedorList = clienteFornecedorRepository.findAll();
        assertThat(clienteFornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteFornecedorRepository.findAll().size();
        // set the field null
        clienteFornecedor.setCpf(null);

        // Create the ClienteFornecedor, which fails.

        restClienteFornecedorMockMvc.perform(post("/api/cliente-fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteFornecedor)))
            .andExpect(status().isBadRequest());

        List<ClienteFornecedor> clienteFornecedorList = clienteFornecedorRepository.findAll();
        assertThat(clienteFornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeHarasIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteFornecedorRepository.findAll().size();
        // set the field null
        clienteFornecedor.setNomeHaras(null);

        // Create the ClienteFornecedor, which fails.

        restClienteFornecedorMockMvc.perform(post("/api/cliente-fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteFornecedor)))
            .andExpect(status().isBadRequest());

        List<ClienteFornecedor> clienteFornecedorList = clienteFornecedorRepository.findAll();
        assertThat(clienteFornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalidadeHarasIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteFornecedorRepository.findAll().size();
        // set the field null
        clienteFornecedor.setLocalidadeHaras(null);

        // Create the ClienteFornecedor, which fails.

        restClienteFornecedorMockMvc.perform(post("/api/cliente-fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteFornecedor)))
            .andExpect(status().isBadRequest());

        List<ClienteFornecedor> clienteFornecedorList = clienteFornecedorRepository.findAll();
        assertThat(clienteFornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUfHarasIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteFornecedorRepository.findAll().size();
        // set the field null
        clienteFornecedor.setUfHaras(null);

        // Create the ClienteFornecedor, which fails.

        restClienteFornecedorMockMvc.perform(post("/api/cliente-fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteFornecedor)))
            .andExpect(status().isBadRequest());

        List<ClienteFornecedor> clienteFornecedorList = clienteFornecedorRepository.findAll();
        assertThat(clienteFornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClienteFornecedors() throws Exception {
        // Initialize the database
        clienteFornecedorRepository.saveAndFlush(clienteFornecedor);

        // Get all the clienteFornecedorList
        restClienteFornecedorMockMvc.perform(get("/api/cliente-fornecedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteFornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dtNascimento").value(hasItem(DEFAULT_DT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].nomeHaras").value(hasItem(DEFAULT_NOME_HARAS)))
            .andExpect(jsonPath("$.[*].localidadeHaras").value(hasItem(DEFAULT_LOCALIDADE_HARAS)))
            .andExpect(jsonPath("$.[*].ufHaras").value(hasItem(DEFAULT_UF_HARAS)));
    }
    
    @Test
    @Transactional
    public void getClienteFornecedor() throws Exception {
        // Initialize the database
        clienteFornecedorRepository.saveAndFlush(clienteFornecedor);

        // Get the clienteFornecedor
        restClienteFornecedorMockMvc.perform(get("/api/cliente-fornecedors/{id}", clienteFornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clienteFornecedor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dtNascimento").value(DEFAULT_DT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.nomeHaras").value(DEFAULT_NOME_HARAS))
            .andExpect(jsonPath("$.localidadeHaras").value(DEFAULT_LOCALIDADE_HARAS))
            .andExpect(jsonPath("$.ufHaras").value(DEFAULT_UF_HARAS));
    }

    @Test
    @Transactional
    public void getNonExistingClienteFornecedor() throws Exception {
        // Get the clienteFornecedor
        restClienteFornecedorMockMvc.perform(get("/api/cliente-fornecedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClienteFornecedor() throws Exception {
        // Initialize the database
        clienteFornecedorService.save(clienteFornecedor);

        int databaseSizeBeforeUpdate = clienteFornecedorRepository.findAll().size();

        // Update the clienteFornecedor
        ClienteFornecedor updatedClienteFornecedor = clienteFornecedorRepository.findById(clienteFornecedor.getId()).get();
        // Disconnect from session so that the updates on updatedClienteFornecedor are not directly saved in db
        em.detach(updatedClienteFornecedor);
        updatedClienteFornecedor
            .nome(UPDATED_NOME)
            .dtNascimento(UPDATED_DT_NASCIMENTO)
            .cpf(UPDATED_CPF)
            .nomeHaras(UPDATED_NOME_HARAS)
            .localidadeHaras(UPDATED_LOCALIDADE_HARAS)
            .ufHaras(UPDATED_UF_HARAS);

        restClienteFornecedorMockMvc.perform(put("/api/cliente-fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClienteFornecedor)))
            .andExpect(status().isOk());

        // Validate the ClienteFornecedor in the database
        List<ClienteFornecedor> clienteFornecedorList = clienteFornecedorRepository.findAll();
        assertThat(clienteFornecedorList).hasSize(databaseSizeBeforeUpdate);
        ClienteFornecedor testClienteFornecedor = clienteFornecedorList.get(clienteFornecedorList.size() - 1);
        assertThat(testClienteFornecedor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testClienteFornecedor.getDtNascimento()).isEqualTo(UPDATED_DT_NASCIMENTO);
        assertThat(testClienteFornecedor.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testClienteFornecedor.getNomeHaras()).isEqualTo(UPDATED_NOME_HARAS);
        assertThat(testClienteFornecedor.getLocalidadeHaras()).isEqualTo(UPDATED_LOCALIDADE_HARAS);
        assertThat(testClienteFornecedor.getUfHaras()).isEqualTo(UPDATED_UF_HARAS);
    }

    @Test
    @Transactional
    public void updateNonExistingClienteFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = clienteFornecedorRepository.findAll().size();

        // Create the ClienteFornecedor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteFornecedorMockMvc.perform(put("/api/cliente-fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteFornecedor)))
            .andExpect(status().isBadRequest());

        // Validate the ClienteFornecedor in the database
        List<ClienteFornecedor> clienteFornecedorList = clienteFornecedorRepository.findAll();
        assertThat(clienteFornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClienteFornecedor() throws Exception {
        // Initialize the database
        clienteFornecedorService.save(clienteFornecedor);

        int databaseSizeBeforeDelete = clienteFornecedorRepository.findAll().size();

        // Delete the clienteFornecedor
        restClienteFornecedorMockMvc.perform(delete("/api/cliente-fornecedors/{id}", clienteFornecedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClienteFornecedor> clienteFornecedorList = clienteFornecedorRepository.findAll();
        assertThat(clienteFornecedorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
