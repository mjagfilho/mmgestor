package br.com.mmgestor.repository.search;

import br.com.mmgestor.domain.ClienteFornecedor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ClienteFornecedor} entity.
 */
public interface ClienteFornecedorSearchRepository extends ElasticsearchRepository<ClienteFornecedor, Long> {
}
