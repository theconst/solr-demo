package org.demo.solr;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.junit.BeforeClass;

public class SolrServiceTest {

    private static CoreContainer coreContainer;
    private static EmbeddedSolrServer solrServer;

    @BeforeClass
    public static void initSolrTest() {
        coreContainer = new CoreContainer("src/test/resources/testdata/solr");
        coreContainer.load();
        solrServer = new EmbeddedSolrServer(coreContainer, "wikipedia");
    }

}