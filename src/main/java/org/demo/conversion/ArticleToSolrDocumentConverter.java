package org.demo.conversion;

import org.apache.solr.common.SolrInputDocument;
import org.demo.pojo.Article;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleToSolrDocumentConverter implements Converter<Article, SolrInputDocument> {

    @Override
    public SolrInputDocument convert(Article article) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("fileName", article.getFileName());
        document.addField("title", article.getTitles());
        document.addField("content", article.getContent());

        return document;
    }
}
