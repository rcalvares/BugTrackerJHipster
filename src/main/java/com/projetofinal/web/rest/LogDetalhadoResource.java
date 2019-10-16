package com.projetofinal.web.rest;

import com.projetofinal.domain.LogDetalhado;
import com.projetofinal.repository.LogDetalhadoRepository;
import com.projetofinal.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.projetofinal.domain.LogDetalhado}.
 */
@RestController
@RequestMapping("/api")
public class LogDetalhadoResource {

    private final Logger log = LoggerFactory.getLogger(LogDetalhadoResource.class);

    private static final String ENTITY_NAME = "bugTrackerLogDetalhado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogDetalhadoRepository logDetalhadoRepository;

    public LogDetalhadoResource(LogDetalhadoRepository logDetalhadoRepository) {
        this.logDetalhadoRepository = logDetalhadoRepository;
    }

    /**
     * {@code POST  /log-detalhados} : Create a new logDetalhado.
     *
     * @param logDetalhado the logDetalhado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logDetalhado, or with status {@code 400 (Bad Request)} if the logDetalhado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/log-detalhados")
    public ResponseEntity<LogDetalhado> createLogDetalhado(@RequestBody LogDetalhado logDetalhado) throws URISyntaxException {
        log.debug("REST request to save LogDetalhado : {}", logDetalhado);
        if (logDetalhado.getId() != null) {
            throw new BadRequestAlertException("A new logDetalhado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogDetalhado result = logDetalhadoRepository.save(logDetalhado);
        return ResponseEntity.created(new URI("/api/log-detalhados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /log-detalhados} : Updates an existing logDetalhado.
     *
     * @param logDetalhado the logDetalhado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logDetalhado,
     * or with status {@code 400 (Bad Request)} if the logDetalhado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logDetalhado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/log-detalhados")
    public ResponseEntity<LogDetalhado> updateLogDetalhado(@RequestBody LogDetalhado logDetalhado) throws URISyntaxException {
        log.debug("REST request to update LogDetalhado : {}", logDetalhado);
        if (logDetalhado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogDetalhado result = logDetalhadoRepository.save(logDetalhado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, logDetalhado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /log-detalhados} : get all the logDetalhados.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logDetalhados in body.
     */
    @GetMapping("/log-detalhados")
    public List<LogDetalhado> getAllLogDetalhados() {
        log.debug("REST request to get all LogDetalhados");
        return logDetalhadoRepository.findAll();
    }

    /**
     * {@code GET  /log-detalhados/:id} : get the "id" logDetalhado.
     *
     * @param id the id of the logDetalhado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logDetalhado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/log-detalhados/{id}")
    public ResponseEntity<LogDetalhado> getLogDetalhado(@PathVariable Long id) {
        log.debug("REST request to get LogDetalhado : {}", id);
        Optional<LogDetalhado> logDetalhado = logDetalhadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(logDetalhado);
    }

    /**
     * {@code DELETE  /log-detalhados/:id} : delete the "id" logDetalhado.
     *
     * @param id the id of the logDetalhado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/log-detalhados/{id}")
    public ResponseEntity<Void> deleteLogDetalhado(@PathVariable Long id) {
        log.debug("REST request to delete LogDetalhado : {}", id);
        logDetalhadoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
