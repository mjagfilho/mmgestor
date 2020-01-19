package br.com.mmgestor.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link LocalSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class LocalSearchRepositoryMockConfiguration {

    @MockBean
    private LocalSearchRepository mockLocalSearchRepository;

}
