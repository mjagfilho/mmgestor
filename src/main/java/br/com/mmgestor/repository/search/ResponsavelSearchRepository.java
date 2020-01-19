package br.com.mmgestor.repository.search;

import br.com.mmgestor.domain.Responsavel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Responsavel} entity.
 */
public interface ResponsavelSearchRepository extends ElasticsearchRepository<Responsavel, Long> {
}
