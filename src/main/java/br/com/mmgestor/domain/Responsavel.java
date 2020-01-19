package br.com.mmgestor.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Responsavel.
 */
@Entity
@Table(name = "responsavel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "responsavel")
public class Responsavel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "cpf", nullable = false, unique = true)
    private Long cpf;

    @NotNull
    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(name = "dt_nascimento")
    private Instant dtNascimento;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Endereco endereco;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Usuario usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCpf() {
        return cpf;
    }

    public Responsavel cpf(Long cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public Responsavel nomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
        return this;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Instant getDtNascimento() {
        return dtNascimento;
    }

    public Responsavel dtNascimento(Instant dtNascimento) {
        this.dtNascimento = dtNascimento;
        return this;
    }

    public void setDtNascimento(Instant dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Responsavel endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Responsavel usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Responsavel)) {
            return false;
        }
        return id != null && id.equals(((Responsavel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Responsavel{" +
            "id=" + getId() +
            ", cpf=" + getCpf() +
            ", nomeCompleto='" + getNomeCompleto() + "'" +
            ", dtNascimento='" + getDtNascimento() + "'" +
            "}";
    }
}
