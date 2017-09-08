package org.demo.solr;


import org.apache.solr.client.solrj.SolrClient;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EmbeddedSolrServerIntegrationTestConfig.class)
abstract public class AbstractSolrIntegrationTest {

    @Value("${solr.core.name:wikipedia}")
    protected String defaultCoreName;

    @Autowired
    protected SolrClient solrClient;

    @Before
    public void setUp() {
        try {
            solrClient.deleteByQuery(defaultCoreName, "*:*");
            solrClient.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
