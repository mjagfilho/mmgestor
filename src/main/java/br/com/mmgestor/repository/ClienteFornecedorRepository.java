package br.com.mmgestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mmgestor.domain.ClienteFornecedor;

/**
 * Spring Data  repository for the ClienteFornecedor entity.
 */
@Repository
public interface ClienteFornecedorRepository extends JpaRepository<ClienteFornecedor, Long> {
}
