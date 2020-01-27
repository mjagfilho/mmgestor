package br.com.mmgestor.repository;

import br.com.mmgestor.domain.Animal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Animal entity.
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
