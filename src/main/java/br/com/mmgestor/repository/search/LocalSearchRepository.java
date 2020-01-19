package br.com.mmgestor.repository.search;

import br.com.mmgestor.domain.Local;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Local} entity.
 */
public interface LocalSearchRepository extends ElasticsearchRepository<Local, Long> {
}
