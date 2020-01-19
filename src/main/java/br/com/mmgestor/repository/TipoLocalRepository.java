package br.com.mmgestor.repository;

import br.com.mmgestor.domain.TipoLocal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoLocal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoLocalRepository extends JpaRepository<TipoLocal, Long> {

}
