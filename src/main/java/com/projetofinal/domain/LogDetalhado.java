package com.projetofinal.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A LogDetalhado.
 */
@Entity
@Table(name = "log_detalhado")
public class LogDetalhado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "detalhes")
    private String detalhes;

    @OneToMany(mappedBy = "logDetalhado")
    private Set<Log> logs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public LogDetalhado titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public LogDetalhado detalhes(String detalhes) {
        this.detalhes = detalhes;
        return this;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public LogDetalhado logs(Set<Log> logs) {
        this.logs = logs;
        return this;
    }

    public LogDetalhado addLog(Log log) {
        this.logs.add(log);
        log.setLogDetalhado(this);
        return this;
    }

    public LogDetalhado removeLog(Log log) {
        this.logs.remove(log);
        log.setLogDetalhado(null);
        return this;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogDetalhado)) {
            return false;
        }
        return id != null && id.equals(((LogDetalhado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LogDetalhado{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", detalhes='" + getDetalhes() + "'" +
            "}";
    }
}
