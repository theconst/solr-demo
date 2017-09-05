package org.demo.app.facade;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.demo.pojo.Article;
import org.demo.solr.SolrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;
import static org.apache.commons.io.FileUtils.readFileToString;

@Component
@Slf4j
public class ArticleCollectionFacade {

    private static final Charset CHARSET = Charset.defaultCharset();

    private final SolrService solrService;

    @Autowired
    public ArticleCollectionFacade(SolrService solrService) {
        this.solrService = solrService;
    }

    public void addFilesToCollection(String collectionName, File... files) {
        try {
            for (File file : files) {
                Article article = new Article();
                String content = readFileToString(file, CHARSET);
                article.setContent(content);
                String fileName = file.getName();
                article.setTitles(singletonList(fileName));
                article.setFileName(fileName);
                solrService.publish(collectionName, article);
            }
            log.info(format("Files %s were added to collection named %s", Arrays.toString(stream(files)
                    .map(File::getName)
                    .toArray()), collectionName));
        } catch (IOException ex) {
            log.info("Error reading file", ex);
        }
    }

    //todo: consider formatting results
    public <T> List<T> searchForBeansInCollection(String collectionName, String queryString, Class<T> beanClass) {
        return solrService.search(collectionName, queryString, beanClass);
    }
}
