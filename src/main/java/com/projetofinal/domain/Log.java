package com.projetofinal.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.projetofinal.domain.enumeration.Level;

/**
 * A Log.
 */
@Entity
@Table(name = "log")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "origem")
    private String origem;

    @Column(name = "data_log")
    private Instant dataLog;

    @Column(name = "numero_ocorrencias")
    private Integer numeroOcorrencias;

    @ManyToOne
    @JsonIgnoreProperties("logs")
    private LogDetalhado logDetalhado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public Log level(Level level) {
        this.level = level;
        return this;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getDescricao() {
        return descricao;
    }

    public Log descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getOrigem() {
        return origem;
    }

    public Log origem(String origem) {
        this.origem = origem;
        return this;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public Instant getDataLog() {
        return dataLog;
    }

    public Log dataLog(Instant dataLog) {
        this.dataLog = dataLog;
        return this;
    }

    public void setDataLog(Instant dataLog) {
        this.dataLog = dataLog;
    }

    public Integer getNumeroOcorrencias() {
        return numeroOcorrencias;
    }

    public Log numeroOcorrencias(Integer numeroOcorrencias) {
        this.numeroOcorrencias = numeroOcorrencias;
        return this;
    }

    public void setNumeroOcorrencias(Integer numeroOcorrencias) {
        this.numeroOcorrencias = numeroOcorrencias;
    }

    public LogDetalhado getLogDetalhado() {
        return logDetalhado;
    }

    public Log logDetalhado(LogDetalhado logDetalhado) {
        this.logDetalhado = logDetalhado;
        return this;
    }

    public void setLogDetalhado(LogDetalhado logDetalhado) {
        this.logDetalhado = logDetalhado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Log)) {
            return false;
        }
        return id != null && id.equals(((Log) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Log{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", dataLog='" + getDataLog() + "'" +
            ", numeroOcorrencias=" + getNumeroOcorrencias() +
            "}";
    }
}
