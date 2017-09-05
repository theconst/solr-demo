package org.demo.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {

    @Bean
    SolrClient solrClient(@Value("${solr.server-url}") String serverUrl) {
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder();
        return builder.withBaseSolrUrl(serverUrl).build();
    }
}
