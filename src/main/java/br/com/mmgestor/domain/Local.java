package br.com.mmgestor.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Local.
 */
@Entity
@Table(name = "local")
public class Local implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "area", precision = 21, scale = 2, nullable = false)
    private BigDecimal area;

    @NotNull
    @Column(name = "eh_contigua", nullable = false)
    private Boolean ehContigua;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private TipoLocal tipo;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Endereco endereco;

    @ManyToOne
    @JsonIgnoreProperties("locals")
    private Local pai;

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

    public Local nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getArea() {
        return area;
    }

    public Local area(BigDecimal area) {
        this.area = area;
        return this;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public Boolean isEhContigua() {
        return ehContigua;
    }

    public Local ehContigua(Boolean ehContigua) {
        this.ehContigua = ehContigua;
        return this;
    }

    public void setEhContigua(Boolean ehContigua) {
        this.ehContigua = ehContigua;
    }

    public TipoLocal getTipo() {
        return tipo;
    }

    public Local tipo(TipoLocal tipoLocal) {
        this.tipo = tipoLocal;
        return this;
    }

    public void setTipo(TipoLocal tipoLocal) {
        this.tipo = tipoLocal;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Local endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Local getPai() {
        return pai;
    }

    public Local pai(Local local) {
        this.pai = local;
        return this;
    }

    public void setPai(Local local) {
        this.pai = local;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Local)) {
            return false;
        }
        return id != null && id.equals(((Local) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Local{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", area=" + getArea() +
            ", ehContigua='" + isEhContigua() + "'" +
            "}";
    }
}
