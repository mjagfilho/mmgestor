package br.com.mmgestor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DadosAssociacao.
 */
@Entity
@Table(name = "dados_associacao")
public class DadosAssociacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "criador")
    private String criador;

    @Column(name = "proprietario")
    private String proprietario;

    @Column(name = "livro")
    private String livro;

    
    @Column(name = "registro", unique = true)
    private String registro;

    @Column(name = "exame_dna")
    private String exameDNA;

    
    @Column(name = "chip", unique = true)
    private String chip;

    @Column(name = "eh_bloqueado")
    private Boolean ehBloqueado;

    @OneToOne(mappedBy = "dadosAssociacao")
    @JsonIgnore
    private Animal animal;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCriador() {
        return criador;
    }

    public DadosAssociacao criador(String criador) {
        this.criador = criador;
        return this;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    public String getProprietario() {
        return proprietario;
    }

    public DadosAssociacao proprietario(String proprietario) {
        this.proprietario = proprietario;
        return this;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getLivro() {
        return livro;
    }

    public DadosAssociacao livro(String livro) {
        this.livro = livro;
        return this;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getRegistro() {
        return registro;
    }

    public DadosAssociacao registro(String registro) {
        this.registro = registro;
        return this;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getExameDNA() {
        return exameDNA;
    }

    public DadosAssociacao exameDNA(String exameDNA) {
        this.exameDNA = exameDNA;
        return this;
    }

    public void setExameDNA(String exameDNA) {
        this.exameDNA = exameDNA;
    }

    public String getChip() {
        return chip;
    }

    public DadosAssociacao chip(String chip) {
        this.chip = chip;
        return this;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public Boolean isEhBloqueado() {
        return ehBloqueado;
    }

    public DadosAssociacao ehBloqueado(Boolean ehBloqueado) {
        this.ehBloqueado = ehBloqueado;
        return this;
    }

    public void setEhBloqueado(Boolean ehBloqueado) {
        this.ehBloqueado = ehBloqueado;
    }

    public Animal getAnimal() {
        return animal;
    }

    public DadosAssociacao animal(Animal animal) {
        this.animal = animal;
        return this;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DadosAssociacao)) {
            return false;
        }
        return id != null && id.equals(((DadosAssociacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DadosAssociacao{" +
            "id=" + getId() +
            ", criador='" + getCriador() + "'" +
            ", proprietario='" + getProprietario() + "'" +
            ", livro='" + getLivro() + "'" +
            ", registro='" + getRegistro() + "'" +
            ", exameDNA='" + getExameDNA() + "'" +
            ", chip='" + getChip() + "'" +
            ", ehBloqueado='" + isEhBloqueado() + "'" +
            "}";
    }
}
