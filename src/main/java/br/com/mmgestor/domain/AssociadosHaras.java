package br.com.mmgestor.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AssociadosHaras.
 */
@Entity
@Table(name = "associados_haras")
public class AssociadosHaras implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_associacao", nullable = false)
    private LocalDate dataAssociacao;

    @NotNull
    @Column(name = "eh_ativo", nullable = false)
    private Boolean ehAtivo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("associadosHaras")
    private Associado associado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("associadosHaras")
    private Haras haras;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAssociacao() {
        return dataAssociacao;
    }

    public AssociadosHaras dataAssociacao(LocalDate dataAssociacao) {
        this.dataAssociacao = dataAssociacao;
        return this;
    }

    public void setDataAssociacao(LocalDate dataAssociacao) {
        this.dataAssociacao = dataAssociacao;
    }

    public Boolean isEhAtivo() {
        return ehAtivo;
    }

    public AssociadosHaras ehAtivo(Boolean ehAtivo) {
        this.ehAtivo = ehAtivo;
        return this;
    }

    public void setEhAtivo(Boolean ehAtivo) {
        this.ehAtivo = ehAtivo;
    }

    public Associado getAssociado() {
        return associado;
    }

    public AssociadosHaras associado(Associado associado) {
        this.associado = associado;
        return this;
    }

    public void setAssociado(Associado associado) {
        this.associado = associado;
    }

    public Haras getHaras() {
        return haras;
    }

    public AssociadosHaras haras(Haras haras) {
        this.haras = haras;
        return this;
    }

    public void setHaras(Haras haras) {
        this.haras = haras;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssociadosHaras)) {
            return false;
        }
        return id != null && id.equals(((AssociadosHaras) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AssociadosHaras{" +
            "id=" + getId() +
            ", dataAssociacao='" + getDataAssociacao() + "'" +
            ", ehAtivo='" + isEhAtivo() + "'" +
            "}";
    }
}
