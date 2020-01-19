package br.com.mmgestor.repository.search;

import br.com.mmgestor.domain.Usuario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Usuario} entity.
 */
public interface UsuarioSearchRepository extends ElasticsearchRepository<Usuario, Long> {
}
