package br.com.mmgestor.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import br.com.mmgestor.domain.enumeration.Sexo;

import br.com.mmgestor.domain.enumeration.Pelagem;

/**
 * A Animal.
 */
@Entity
@Table(name = "animal")
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @NotNull
    @Column(name = "dt_nascimento", nullable = false)
    private LocalDate dtNascimento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "pelagem", nullable = false)
    private Pelagem pelagem;

    @NotNull
    @Column(name = "eh_vivo", nullable = false)
    private Boolean ehVivo;

    @OneToOne
    @JoinColumn(unique = true)
    private DadosAssociacao dadosAssociacao;

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

    public Animal nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public Animal dtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
        return this;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Animal sexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Pelagem getPelagem() {
        return pelagem;
    }

    public Animal pelagem(Pelagem pelagem) {
        this.pelagem = pelagem;
        return this;
    }

    public void setPelagem(Pelagem pelagem) {
        this.pelagem = pelagem;
    }

    public Boolean isEhVivo() {
        return ehVivo;
    }

    public Animal ehVivo(Boolean ehVivo) {
        this.ehVivo = ehVivo;
        return this;
    }

    public void setEhVivo(Boolean ehVivo) {
        this.ehVivo = ehVivo;
    }

    public DadosAssociacao getDadosAssociacao() {
        return dadosAssociacao;
    }

    public Animal dadosAssociacao(DadosAssociacao dadosAssociacao) {
        this.dadosAssociacao = dadosAssociacao;
        return this;
    }

    public void setDadosAssociacao(DadosAssociacao dadosAssociacao) {
        this.dadosAssociacao = dadosAssociacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Animal)) {
            return false;
        }
        return id != null && id.equals(((Animal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Animal{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dtNascimento='" + getDtNascimento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", pelagem='" + getPelagem() + "'" +
            ", ehVivo='" + isEhVivo() + "'" +
            "}";
    }
}
