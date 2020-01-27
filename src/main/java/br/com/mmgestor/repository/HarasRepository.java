package br.com.mmgestor.repository;

import br.com.mmgestor.domain.Haras;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Haras entity.
 */
@Repository
public interface HarasRepository extends JpaRepository<Haras, Long> {

}
