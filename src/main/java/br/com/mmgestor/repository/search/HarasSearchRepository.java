package br.com.mmgestor.repository.search;

import br.com.mmgestor.domain.Haras;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Haras} entity.
 */
public interface HarasSearchRepository extends ElasticsearchRepository<Haras, Long> {
}
