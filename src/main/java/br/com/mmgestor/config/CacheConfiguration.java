package br.com.mmgestor.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, br.com.mmgestor.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, br.com.mmgestor.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, br.com.mmgestor.domain.User.class.getName());
            createCache(cm, br.com.mmgestor.domain.Authority.class.getName());
            createCache(cm, br.com.mmgestor.domain.User.class.getName() + ".authorities");
            createCache(cm, br.com.mmgestor.domain.Usuario.class.getName());
            createCache(cm, br.com.mmgestor.domain.Endereco.class.getName());
            createCache(cm, br.com.mmgestor.domain.Responsavel.class.getName());
            createCache(cm, br.com.mmgestor.domain.Haras.class.getName());
            createCache(cm, br.com.mmgestor.domain.TipoLocal.class.getName());
            createCache(cm, br.com.mmgestor.domain.Local.class.getName());
            createCache(cm, br.com.mmgestor.domain.ClienteFornecedor.class.getName());
            createCache(cm, br.com.mmgestor.domain.Animal.class.getName());
            createCache(cm, br.com.mmgestor.domain.DadosAssociacao.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
