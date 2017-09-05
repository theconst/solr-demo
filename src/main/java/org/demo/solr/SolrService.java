package org.demo.solr;

import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.io.stream.SolrStream;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.singleton;

@Slf4j
@Service
public class SolrService {

    private final SolrClient client;

    private final ConversionService conversionService;

    @Autowired
    public SolrService(SolrClient client, ConversionService conversionService) {
        this.client = client;
        this.conversionService = conversionService;
    }

    public <T> List<T> search(String collection, String searchTerm, Class<T> clazz) {
        SolrQuery query = new SolrQuery(searchTerm);
        try {
            QueryResponse response = client.query(collection, query);
            return response.getBeans(clazz);
        } catch (SolrServerException | IOException ex) {
            throw new SolrExcpetion(ex);
        }
    }

    public <T> void publish(String collection, Collection<T> beans) {
        try {
            for (T bean : beans) {
                SolrInputDocument solrDocument = conversionService.convert(bean, SolrInputDocument.class);
                client.add(collection, solrDocument);
            }
            client.commit();
        } catch (SolrServerException | IOException ex) {
            throw new SolrExcpetion(ex);
        }
        log.info(format("Successfully added %s to %s", collection, beans));
    }

    public <T> void publish(String collection, T bean) {
        publish(collection, singleton(bean));
    }
}
