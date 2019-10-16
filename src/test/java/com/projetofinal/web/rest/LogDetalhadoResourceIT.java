package com.projetofinal.web.rest;

import com.projetofinal.BugTrackerApp;
import com.projetofinal.domain.LogDetalhado;
import com.projetofinal.repository.LogDetalhadoRepository;
import com.projetofinal.web.rest.errors.ExceptionTranslator;

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
import java.util.List;

import static com.projetofinal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LogDetalhadoResource} REST controller.
 */
@SpringBootTest(classes = BugTrackerApp.class)
public class LogDetalhadoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DETALHES = "AAAAAAAAAA";
    private static final String UPDATED_DETALHES = "BBBBBBBBBB";

    @Autowired
    private LogDetalhadoRepository logDetalhadoRepository;

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

    private MockMvc restLogDetalhadoMockMvc;

    private LogDetalhado logDetalhado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogDetalhadoResource logDetalhadoResource = new LogDetalhadoResource(logDetalhadoRepository);
        this.restLogDetalhadoMockMvc = MockMvcBuilders.standaloneSetup(logDetalhadoResource)
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
    public static LogDetalhado createEntity(EntityManager em) {
        LogDetalhado logDetalhado = new LogDetalhado()
            .titulo(DEFAULT_TITULO)
            .detalhes(DEFAULT_DETALHES);
        return logDetalhado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LogDetalhado createUpdatedEntity(EntityManager em) {
        LogDetalhado logDetalhado = new LogDetalhado()
            .titulo(UPDATED_TITULO)
            .detalhes(UPDATED_DETALHES);
        return logDetalhado;
    }

    @BeforeEach
    public void initTest() {
        logDetalhado = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogDetalhado() throws Exception {
        int databaseSizeBeforeCreate = logDetalhadoRepository.findAll().size();

        // Create the LogDetalhado
        restLogDetalhadoMockMvc.perform(post("/api/log-detalhados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logDetalhado)))
            .andExpect(status().isCreated());

        // Validate the LogDetalhado in the database
        List<LogDetalhado> logDetalhadoList = logDetalhadoRepository.findAll();
        assertThat(logDetalhadoList).hasSize(databaseSizeBeforeCreate + 1);
        LogDetalhado testLogDetalhado = logDetalhadoList.get(logDetalhadoList.size() - 1);
        assertThat(testLogDetalhado.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testLogDetalhado.getDetalhes()).isEqualTo(DEFAULT_DETALHES);
    }

    @Test
    @Transactional
    public void createLogDetalhadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logDetalhadoRepository.findAll().size();

        // Create the LogDetalhado with an existing ID
        logDetalhado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogDetalhadoMockMvc.perform(post("/api/log-detalhados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logDetalhado)))
            .andExpect(status().isBadRequest());

        // Validate the LogDetalhado in the database
        List<LogDetalhado> logDetalhadoList = logDetalhadoRepository.findAll();
        assertThat(logDetalhadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLogDetalhados() throws Exception {
        // Initialize the database
        logDetalhadoRepository.saveAndFlush(logDetalhado);

        // Get all the logDetalhadoList
        restLogDetalhadoMockMvc.perform(get("/api/log-detalhados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logDetalhado.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].detalhes").value(hasItem(DEFAULT_DETALHES)));
    }
    
    @Test
    @Transactional
    public void getLogDetalhado() throws Exception {
        // Initialize the database
        logDetalhadoRepository.saveAndFlush(logDetalhado);

        // Get the logDetalhado
        restLogDetalhadoMockMvc.perform(get("/api/log-detalhados/{id}", logDetalhado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logDetalhado.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.detalhes").value(DEFAULT_DETALHES));
    }

    @Test
    @Transactional
    public void getNonExistingLogDetalhado() throws Exception {
        // Get the logDetalhado
        restLogDetalhadoMockMvc.perform(get("/api/log-detalhados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogDetalhado() throws Exception {
        // Initialize the database
        logDetalhadoRepository.saveAndFlush(logDetalhado);

        int databaseSizeBeforeUpdate = logDetalhadoRepository.findAll().size();

        // Update the logDetalhado
        LogDetalhado updatedLogDetalhado = logDetalhadoRepository.findById(logDetalhado.getId()).get();
        // Disconnect from session so that the updates on updatedLogDetalhado are not directly saved in db
        em.detach(updatedLogDetalhado);
        updatedLogDetalhado
            .titulo(UPDATED_TITULO)
            .detalhes(UPDATED_DETALHES);

        restLogDetalhadoMockMvc.perform(put("/api/log-detalhados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogDetalhado)))
            .andExpect(status().isOk());

        // Validate the LogDetalhado in the database
        List<LogDetalhado> logDetalhadoList = logDetalhadoRepository.findAll();
        assertThat(logDetalhadoList).hasSize(databaseSizeBeforeUpdate);
        LogDetalhado testLogDetalhado = logDetalhadoList.get(logDetalhadoList.size() - 1);
        assertThat(testLogDetalhado.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testLogDetalhado.getDetalhes()).isEqualTo(UPDATED_DETALHES);
    }

    @Test
    @Transactional
    public void updateNonExistingLogDetalhado() throws Exception {
        int databaseSizeBeforeUpdate = logDetalhadoRepository.findAll().size();

        // Create the LogDetalhado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLogDetalhadoMockMvc.perform(put("/api/log-detalhados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logDetalhado)))
            .andExpect(status().isBadRequest());

        // Validate the LogDetalhado in the database
        List<LogDetalhado> logDetalhadoList = logDetalhadoRepository.findAll();
        assertThat(logDetalhadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLogDetalhado() throws Exception {
        // Initialize the database
        logDetalhadoRepository.saveAndFlush(logDetalhado);

        int databaseSizeBeforeDelete = logDetalhadoRepository.findAll().size();

        // Delete the logDetalhado
        restLogDetalhadoMockMvc.perform(delete("/api/log-detalhados/{id}", logDetalhado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LogDetalhado> logDetalhadoList = logDetalhadoRepository.findAll();
        assertThat(logDetalhadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogDetalhado.class);
        LogDetalhado logDetalhado1 = new LogDetalhado();
        logDetalhado1.setId(1L);
        LogDetalhado logDetalhado2 = new LogDetalhado();
        logDetalhado2.setId(logDetalhado1.getId());
        assertThat(logDetalhado1).isEqualTo(logDetalhado2);
        logDetalhado2.setId(2L);
        assertThat(logDetalhado1).isNotEqualTo(logDetalhado2);
        logDetalhado1.setId(null);
        assertThat(logDetalhado1).isNotEqualTo(logDetalhado2);
    }
}
