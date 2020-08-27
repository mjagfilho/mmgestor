package br.com.mmgestor.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TipoAssociado.
 */
@Entity
@Table(name = "tipo_associado")
public class TipoAssociado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "eh_administrador", nullable = false)
    private Boolean ehAdministrador;

    @NotNull
    @Column(name = "eh_financeiro", nullable = false)
    private Boolean ehFinanceiro;

    @NotNull
    @Column(name = "eh_operacional", nullable = false)
    private Boolean ehOperacional;

    @NotNull
    @Column(name = "eh_veterinario", nullable = false)
    private Boolean ehVeterinario;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public TipoAssociado nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoAssociado descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isEhAdministrador() {
        return ehAdministrador;
    }

    public TipoAssociado ehAdministrador(Boolean ehAdministrador) {
        this.ehAdministrador = ehAdministrador;
        return this;
    }

    public void setEhAdministrador(Boolean ehAdministrador) {
        this.ehAdministrador = ehAdministrador;
    }

    public Boolean isEhFinanceiro() {
        return ehFinanceiro;
    }

    public TipoAssociado ehFinanceiro(Boolean ehFinanceiro) {
        this.ehFinanceiro = ehFinanceiro;
        return this;
    }

    public void setEhFinanceiro(Boolean ehFinanceiro) {
        this.ehFinanceiro = ehFinanceiro;
    }

    public Boolean isEhOperacional() {
        return ehOperacional;
    }

    public TipoAssociado ehOperacional(Boolean ehOperacional) {
        this.ehOperacional = ehOperacional;
        return this;
    }

    public void setEhOperacional(Boolean ehOperacional) {
        this.ehOperacional = ehOperacional;
    }

    public Boolean isEhVeterinario() {
        return ehVeterinario;
    }

    public TipoAssociado ehVeterinario(Boolean ehVeterinario) {
        this.ehVeterinario = ehVeterinario;
        return this;
    }

    public void setEhVeterinario(Boolean ehVeterinario) {
        this.ehVeterinario = ehVeterinario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoAssociado)) {
            return false;
        }
        return id != null && id.equals(((TipoAssociado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoAssociado{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ehAdministrador='" + isEhAdministrador() + "'" +
            ", ehFinanceiro='" + isEhFinanceiro() + "'" +
            ", ehOperacional='" + isEhOperacional() + "'" +
            ", ehVeterinario='" + isEhVeterinario() + "'" +
            "}";
    }
}
