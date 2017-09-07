package org.demo.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.demo.solr.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackageClasses = ArticleRepository.class, namedQueriesLocation = "classpath*:application.properties")
public class SolrRepositoryConfig {

    @Bean
    public SolrClient solrClient(@Value("${solr.server-url}") String serverUrl) {
        return new HttpSolrClient.Builder()
                .withBaseSolrUrl(serverUrl)
                .build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) {
        return new SolrTemplate(client);
    }
}
