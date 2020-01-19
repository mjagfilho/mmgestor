package br.com.mmgestor.repository.search;

import br.com.mmgestor.domain.TipoLocal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TipoLocal} entity.
 */
public interface TipoLocalSearchRepository extends ElasticsearchRepository<TipoLocal, Long> {
}
