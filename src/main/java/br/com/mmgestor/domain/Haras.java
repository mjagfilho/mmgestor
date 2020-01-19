package br.com.mmgestor.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Haras.
 */
@Entity
@Table(name = "haras")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "haras")
public class Haras implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "localidade", nullable = false)
    private String localidade;

    @NotNull
    @Column(name = "uf", nullable = false)
    private String uf;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("haras")
    private Responsavel responsavel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Haras nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalidade() {
        return localidade;
    }

    public Haras localidade(String localidade) {
        this.localidade = localidade;
        return this;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public Haras uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public Haras responsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Haras)) {
            return false;
        }
        return id != null && id.equals(((Haras) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Haras{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", localidade='" + getLocalidade() + "'" +
            ", uf='" + getUf() + "'" +
            "}";
    }
}
