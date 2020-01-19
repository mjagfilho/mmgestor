package br.com.mmgestor.repository.search;

import br.com.mmgestor.domain.Animal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Animal} entity.
 */
public interface AnimalSearchRepository extends ElasticsearchRepository<Animal, Long> {
}
