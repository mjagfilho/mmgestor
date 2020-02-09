package br.com.mmgestor.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Associado.
 */
@Entity
@Table(name = "associado")
public class Associado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dt_nascimento")
    private LocalDate dtNascimento;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Endereco endereco;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public Associado dtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
        return this;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Associado endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public User getUsuario() {
        return usuario;
    }

    public Associado usuario(User user) {
        this.usuario = user;
        return this;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Associado)) {
            return false;
        }
        return id != null && id.equals(((Associado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Associado{" +
            "id=" + getId() +
            ", dtNascimento='" + getDtNascimento() + "'" +
            "}";
    }
}
