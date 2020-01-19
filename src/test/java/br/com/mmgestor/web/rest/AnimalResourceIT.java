package br.com.mmgestor.web.rest;

import br.com.mmgestor.MmGestorApp;
import br.com.mmgestor.domain.Animal;
import br.com.mmgestor.repository.AnimalRepository;
import br.com.mmgestor.repository.search.AnimalSearchRepository;
import br.com.mmgestor.service.AnimalService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static br.com.mmgestor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.mmgestor.domain.enumeration.Sexo;
import br.com.mmgestor.domain.enumeration.Pelagem;
/**
 * Integration tests for the {@link AnimalResource} REST controller.
 */
@SpringBootTest(classes = MmGestorApp.class)
public class AnimalResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT_NASCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_NASCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Sexo DEFAULT_SEXO = Sexo.MACHO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMEA;

    private static final Pelagem DEFAULT_PELAGEM = Pelagem.BRANCA;
    private static final Pelagem UPDATED_PELAGEM = Pelagem.ALAZA;

    private static final Boolean DEFAULT_EH_VIVO = false;
    private static final Boolean UPDATED_EH_VIVO = true;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AnimalService animalService;

    /**
     * This repository is mocked in the br.com.mmgestor.repository.search test package.
     *
     * @see br.com.mmgestor.repository.search.AnimalSearchRepositoryMockConfiguration
     */
    @Autowired
    private AnimalSearchRepository mockAnimalSearchRepository;

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

    private MockMvc restAnimalMockMvc;

    private Animal animal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnimalResource animalResource = new AnimalResource(animalService);
        this.restAnimalMockMvc = MockMvcBuilders.standaloneSetup(animalResource)
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
    public static Animal createEntity(EntityManager em) {
        Animal animal = new Animal()
            .nome(DEFAULT_NOME)
            .dtNascimento(DEFAULT_DT_NASCIMENTO)
            .sexo(DEFAULT_SEXO)
            .pelagem(DEFAULT_PELAGEM)
            .ehVivo(DEFAULT_EH_VIVO);
        return animal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Animal createUpdatedEntity(EntityManager em) {
        Animal animal = new Animal()
            .nome(UPDATED_NOME)
            .dtNascimento(UPDATED_DT_NASCIMENTO)
            .sexo(UPDATED_SEXO)
            .pelagem(UPDATED_PELAGEM)
            .ehVivo(UPDATED_EH_VIVO);
        return animal;
    }

    @BeforeEach
    public void initTest() {
        animal = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnimal() throws Exception {
        int databaseSizeBeforeCreate = animalRepository.findAll().size();

        // Create the Animal
        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isCreated());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeCreate + 1);
        Animal testAnimal = animalList.get(animalList.size() - 1);
        assertThat(testAnimal.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAnimal.getDtNascimento()).isEqualTo(DEFAULT_DT_NASCIMENTO);
        assertThat(testAnimal.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testAnimal.getPelagem()).isEqualTo(DEFAULT_PELAGEM);
        assertThat(testAnimal.isEhVivo()).isEqualTo(DEFAULT_EH_VIVO);

        // Validate the Animal in Elasticsearch
        verify(mockAnimalSearchRepository, times(1)).save(testAnimal);
    }

    @Test
    @Transactional
    public void createAnimalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = animalRepository.findAll().size();

        // Create the Animal with an existing ID
        animal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isBadRequest());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeCreate);

        // Validate the Animal in Elasticsearch
        verify(mockAnimalSearchRepository, times(0)).save(animal);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalRepository.findAll().size();
        // set the field null
        animal.setNome(null);

        // Create the Animal, which fails.

        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isBadRequest());

        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDtNascimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalRepository.findAll().size();
        // set the field null
        animal.setDtNascimento(null);

        // Create the Animal, which fails.

        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isBadRequest());

        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalRepository.findAll().size();
        // set the field null
        animal.setSexo(null);

        // Create the Animal, which fails.

        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isBadRequest());

        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPelagemIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalRepository.findAll().size();
        // set the field null
        animal.setPelagem(null);

        // Create the Animal, which fails.

        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isBadRequest());

        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEhVivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = animalRepository.findAll().size();
        // set the field null
        animal.setEhVivo(null);

        // Create the Animal, which fails.

        restAnimalMockMvc.perform(post("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isBadRequest());

        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnimals() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get all the animalList
        restAnimalMockMvc.perform(get("/api/animals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dtNascimento").value(hasItem(DEFAULT_DT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].pelagem").value(hasItem(DEFAULT_PELAGEM.toString())))
            .andExpect(jsonPath("$.[*].ehVivo").value(hasItem(DEFAULT_EH_VIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAnimal() throws Exception {
        // Initialize the database
        animalRepository.saveAndFlush(animal);

        // Get the animal
        restAnimalMockMvc.perform(get("/api/animals/{id}", animal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(animal.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dtNascimento").value(DEFAULT_DT_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.pelagem").value(DEFAULT_PELAGEM.toString()))
            .andExpect(jsonPath("$.ehVivo").value(DEFAULT_EH_VIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAnimal() throws Exception {
        // Get the animal
        restAnimalMockMvc.perform(get("/api/animals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnimal() throws Exception {
        // Initialize the database
        animalService.save(animal);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockAnimalSearchRepository);

        int databaseSizeBeforeUpdate = animalRepository.findAll().size();

        // Update the animal
        Animal updatedAnimal = animalRepository.findById(animal.getId()).get();
        // Disconnect from session so that the updates on updatedAnimal are not directly saved in db
        em.detach(updatedAnimal);
        updatedAnimal
            .nome(UPDATED_NOME)
            .dtNascimento(UPDATED_DT_NASCIMENTO)
            .sexo(UPDATED_SEXO)
            .pelagem(UPDATED_PELAGEM)
            .ehVivo(UPDATED_EH_VIVO);

        restAnimalMockMvc.perform(put("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnimal)))
            .andExpect(status().isOk());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeUpdate);
        Animal testAnimal = animalList.get(animalList.size() - 1);
        assertThat(testAnimal.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAnimal.getDtNascimento()).isEqualTo(UPDATED_DT_NASCIMENTO);
        assertThat(testAnimal.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testAnimal.getPelagem()).isEqualTo(UPDATED_PELAGEM);
        assertThat(testAnimal.isEhVivo()).isEqualTo(UPDATED_EH_VIVO);

        // Validate the Animal in Elasticsearch
        verify(mockAnimalSearchRepository, times(1)).save(testAnimal);
    }

    @Test
    @Transactional
    public void updateNonExistingAnimal() throws Exception {
        int databaseSizeBeforeUpdate = animalRepository.findAll().size();

        // Create the Animal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnimalMockMvc.perform(put("/api/animals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(animal)))
            .andExpect(status().isBadRequest());

        // Validate the Animal in the database
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Animal in Elasticsearch
        verify(mockAnimalSearchRepository, times(0)).save(animal);
    }

    @Test
    @Transactional
    public void deleteAnimal() throws Exception {
        // Initialize the database
        animalService.save(animal);

        int databaseSizeBeforeDelete = animalRepository.findAll().size();

        // Delete the animal
        restAnimalMockMvc.perform(delete("/api/animals/{id}", animal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Animal> animalList = animalRepository.findAll();
        assertThat(animalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Animal in Elasticsearch
        verify(mockAnimalSearchRepository, times(1)).deleteById(animal.getId());
    }

    @Test
    @Transactional
    public void searchAnimal() throws Exception {
        // Initialize the database
        animalService.save(animal);
        when(mockAnimalSearchRepository.search(queryStringQuery("id:" + animal.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(animal), PageRequest.of(0, 1), 1));
        // Search the animal
        restAnimalMockMvc.perform(get("/api/_search/animals?query=id:" + animal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(animal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dtNascimento").value(hasItem(DEFAULT_DT_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].pelagem").value(hasItem(DEFAULT_PELAGEM.toString())))
            .andExpect(jsonPath("$.[*].ehVivo").value(hasItem(DEFAULT_EH_VIVO.booleanValue())));
    }
}
