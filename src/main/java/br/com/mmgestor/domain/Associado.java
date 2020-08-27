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

    @NotNull
    @Size(min = 9, max = 9)
    @Pattern(regexp = "[0-9]{5}-[0-9]{3}")
    @Column(name = "cep", length = 9, nullable = false)
    private String cep;

    @NotNull
    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @NotNull
    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotNull
    @Column(name = "localidade", nullable = false)
    private String localidade;

    @NotNull
    @Size(min = 2, max = 2)
    @Pattern(regexp = "[A-Z]{2}")
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private TipoAssociado tipo;

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

    public String getCep() {
        return cep;
    }

    public Associado cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public Associado logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public Associado numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public Associado complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public Associado bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public Associado localidade(String localidade) {
        this.localidade = localidade;
        return this;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public Associado uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public TipoAssociado getTipo() {
        return tipo;
    }

    public Associado tipo(TipoAssociado tipoAssociado) {
        this.tipo = tipoAssociado;
        return this;
    }

    public void setTipo(TipoAssociado tipoAssociado) {
        this.tipo = tipoAssociado;
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
            ", cep='" + getCep() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", localidade='" + getLocalidade() + "'" +
            ", uf='" + getUf() + "'" +
            "}";
    }
}
