package br.com.mmgestor.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ClienteFornecedor.
 */
@Entity
@Table(name = "cliente_fornecedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "clientefornecedor")
public class ClienteFornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "dt_nascimento")
    private Instant dtNascimento;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private Long cpf;

    @NotNull
    @Column(name = "nome_haras", nullable = false)
    private String nomeHaras;

    @NotNull
    @Column(name = "localidade_haras", nullable = false)
    private String localidadeHaras;

    @NotNull
    @Column(name = "uf_haras", nullable = false)
    private String ufHaras;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Endereco endereco;

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

    public ClienteFornecedor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Instant getDtNascimento() {
        return dtNascimento;
    }

    public ClienteFornecedor dtNascimento(Instant dtNascimento) {
        this.dtNascimento = dtNascimento;
        return this;
    }

    public void setDtNascimento(Instant dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Long getCpf() {
        return cpf;
    }

    public ClienteFornecedor cpf(Long cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Long cpf) {
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

    public Endereco getEndereco() {
        return endereco;
    }

    public ClienteFornecedor endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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

    @Override
    public String toString() {
        return "ClienteFornecedor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dtNascimento='" + getDtNascimento() + "'" +
            ", cpf=" + getCpf() +
            ", nomeHaras='" + getNomeHaras() + "'" +
            ", localidadeHaras='" + getLocalidadeHaras() + "'" +
            ", ufHaras='" + getUfHaras() + "'" +
            "}";
    }
}
