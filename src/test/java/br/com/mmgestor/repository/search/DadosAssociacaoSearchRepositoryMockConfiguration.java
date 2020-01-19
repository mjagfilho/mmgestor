package br.com.mmgestor.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link DadosAssociacaoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DadosAssociacaoSearchRepositoryMockConfiguration {

    @MockBean
    private DadosAssociacaoSearchRepository mockDadosAssociacaoSearchRepository;

}
