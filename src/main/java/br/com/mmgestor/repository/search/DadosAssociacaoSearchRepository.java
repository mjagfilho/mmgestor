package br.com.mmgestor.repository.search;

import br.com.mmgestor.domain.DadosAssociacao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DadosAssociacao} entity.
 */
public interface DadosAssociacaoSearchRepository extends ElasticsearchRepository<DadosAssociacao, Long> {
}
