package org.demo.solr;


import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.lang.String.format;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = EmbeddedSolrServerIntegrationTestConfig.class)
@Slf4j
abstract public class AbstractSolrIntegrationTest {

    @Value("${solr.core.name:wikipedia}")
    protected String defaultCoreName;

    @Value("${dump.enabled:true}")
    protected boolean dumpEnabled;

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

    @After
    public void dumpContent() {
        if (dumpEnabled) {
            SolrDocumentList documentList;
            try {
                QueryResponse response = solrClient.query(new SolrQuery("*:*"));
                documentList = response.getResults();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            log.debug(format("--'%s' dump --", defaultCoreName));
            if (documentList == null || documentList.isEmpty()) {
                log.debug("<EMPTY>");
            } else {
                for (SolrDocument doc : documentList) {
                    log.info(doc.toString());
                }
            }
            log.debug(format("-- end of '%s' dump --", defaultCoreName));
        }
    }
}
