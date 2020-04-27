package br.com.mmgestor.repository;

import br.com.mmgestor.domain.ClienteFornecedor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClienteFornecedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteFornecedorRepository extends JpaRepository<ClienteFornecedor, Long> {

}
