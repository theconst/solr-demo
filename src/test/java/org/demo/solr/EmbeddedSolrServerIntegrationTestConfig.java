package org.demo.solr;

import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import java.nio.file.Paths;

@Configuration
@EnableSolrRepositories
@Slf4j
public class EmbeddedSolrServerIntegrationTestConfig {

    @Bean
    public SolrClient embeddedSolrServer(@Value("${solr.server.path:src/test/resources/solr}") String path,
                                                 @Value("${solr.core.name:wikipedia}") String coreName)  {
        EmbeddedSolrServer solrServer = new EmbeddedSolrServer(Paths.get(path), coreName);
        log.info(solrServer.getCoreContainer().getAllCoreNames().toString());
        return solrServer;
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) {
        return new SolrTemplate(client);
    }

}
