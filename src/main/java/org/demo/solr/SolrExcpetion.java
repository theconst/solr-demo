package org.demo.solr;

public class SolrExcpetion extends RuntimeException {

    public SolrExcpetion(Exception ex) {
        super(ex);
    }
}
