package br.com.mmgestor.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ClienteFornecedor.
 */
@Entity
@Table(name = "cliente_fornecedor")
public class ClienteFornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "dt_nascimento")
    private LocalDate dtNascimento;

    @NotNull
    @Size(min = 14, max = 14)
    @Pattern(regexp = "[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}")
    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;

    @NotNull
    @Column(name = "nome_haras", nullable = false)
    private String nomeHaras;

    @NotNull
    @Column(name = "localidade_haras", nullable = false)
    private String localidadeHaras;

    @NotNull
    @Size(min = 2, max = 2)
    @Pattern(regexp = "[A-Z]{2}")
    @Column(name = "uf_haras", length = 2, nullable = false)
    private String ufHaras;

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

    public ClienteFornecedor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public ClienteFornecedor dtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
        return this;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public ClienteFornecedor cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeHaras() {
        return nomeHaras;
    }

    public ClienteFornecedor nomeHaras(String nomeHaras) {
        this.nomeHaras = nomeHaras;
        return this;
    }

    public void setNomeHaras(String nomeHaras) {
        this.nomeHaras = nomeHaras;
    }

    public String getLocalidadeHaras() {
        return localidadeHaras;
    }

    public ClienteFornecedor localidadeHaras(String localidadeHaras) {
        this.localidadeHaras = localidadeHaras;
        return this;
    }

    public void setLocalidadeHaras(String localidadeHaras) {
        this.localidadeHaras = localidadeHaras;
    }

    public String getUfHaras() {
        return ufHaras;
    }

    public ClienteFornecedor ufHaras(String ufHaras) {
        this.ufHaras = ufHaras;
        return this;
    }

    public void setUfHaras(String ufHaras) {
        this.ufHaras = ufHaras;
    }

    public String getCep() {
        return cep;
    }

    public ClienteFornecedor cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public ClienteFornecedor logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public ClienteFornecedor numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public ClienteFornecedor complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public ClienteFornecedor bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public ClienteFornecedor localidade(String localidade) {
        this.localidade = localidade;
        return this;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public ClienteFornecedor uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteFornecedor)) {
            return false;
        }
        return id != null && id.equals(((ClienteFornecedor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteFornecedor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dtNascimento='" + getDtNascimento() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", nomeHaras='" + getNomeHaras() + "'" +
            ", localidadeHaras='" + getLocalidadeHaras() + "'" +
            ", ufHaras='" + getUfHaras() + "'" +
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
